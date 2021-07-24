import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { APIService } from '../api.service';

@Component({
  selector: 'app-att',
  templateUrl: './att.component.html',
  styleUrls: ['./att.component.css']
})
export class AttComponent implements OnInit {

  data: any[] = [];
  profile: any;
  updateUserInfoForm!: FormGroup;
  isDrawerVisible = false;

  constructor(private apiService: APIService) { }

  async ngOnInit() {
    this.data = await this.apiService.sendGET("http://localhost:8080/getLogModel");
    this.data?.forEach(element => {
      element.detectedTime = new Date(element.detectedTime).toLocaleString()
    });
  }

  async showUserInfo(username: string) {
    this.profile = await this.apiService.sendGET("http://localhost:8080/user/get/" + username);
    this.profile.birthDay = new Date(this.profile.birthDay).toLocaleDateString();

    if (this.profile.phoneNumber) {
      let dirtyPhone = this.profile.phoneNumber
      try {
        let match = dirtyPhone.match(/^(\d{4})(\d{3})(\d{3})$/);
        this.profile.phoneNumber = match[1] + " " + match[2] + " " + match[3]
      } catch (error) {
        console.log(error)
      }
    } else {
      this.profile.phoneNumber = "-"
    }

    this.isDrawerVisible = true;
  }

  close() {
    this.isDrawerVisible = false;
  }
}
