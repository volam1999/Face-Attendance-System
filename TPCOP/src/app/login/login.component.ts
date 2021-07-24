import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { APIService } from '../api.service';
import { Router } from "@angular/router";
import { NzMessageService } from 'ng-zorro-antd/message';
import { Md5 } from 'ts-md5';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  validateForm!: FormGroup;
  isLoading = false;


  async submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    let username = this.validateForm.get("userName")?.value;
    let password = this.validateForm.get("password")?.value;
    let md5 = new Md5();
    password = md5.appendStr(password).end();
    let data = {
      "username": username,
      "password": password
    }
    console.log(username + " " + password)
    if (username && password) {
      this.isLoading = true;
      let validated;

      try {
        validated = await this.apiService.sendPOST("http://localhost:8080/user/validate/", data);
      } catch (error) {
        console.log(error);
        this.message.create("error", "Server is temporarily down! Please, try again later...");
        return
      }
      finally {
        this.isLoading = false;
      }
      if (validated == 1) {
        localStorage.setItem("SESSION", username);
        this.router.navigate(["TPCOP/index"]);
      } else if (validated == 0) {
        this.message.create("error", "Username or Password is incorrect!")
      } else {
        this.message.create("error", "Your account has been blocked or have activated yet! No further information!")
      }
    }

  }

  async loadDataFromMockApi() {
    // load users from mock api
    let url = "https://606e83ef0c054f0017657193.mockapi.io/employee";
    let result: any[] = await this.apiService.sendGET(url);
    result.forEach(async (user: any) => {
      user.sex = user.sex ? "male" : "female";
      await this.apiService.sendPOST("http://localhost:8080/user/add", user, {responseType:"text"});
    });

    // load departments from mock api
    let urlDepartment = "https://606e83ef0c054f0017657193.mockapi.io/departments";
    let departments: any[] = await this.apiService.sendGET(urlDepartment);
    departments.forEach(async department => {
      await this.apiService.sendPOST("http://localhost:8080/department/add", department);
    });
  }

  constructor(private fb: FormBuilder, private apiService: APIService, private router: Router, private message: NzMessageService) { 
    if(localStorage.getItem("SESSION")){
      this.router.navigate(["TPCOP/index"])
    }
  }

  ngOnInit(): void {
    this.loadDataFromMockApi();
    this.validateForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }

}
