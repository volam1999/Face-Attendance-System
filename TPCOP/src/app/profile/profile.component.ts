import { Component, Input, OnInit } from '@angular/core';
import { APIService } from '../api.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  data: any;
  @Input() USERNAME: string | undefined;
  constructor(private apiService: APIService) {
  }

  async ngOnInit() {
    console.log(this.USERNAME);
    this.data = await this.apiService.sendGET("http://localhost:8080/user/get/" + this.USERNAME);
    if (this.data) {
      this.data.birthDay = new Date(this.data.birthDay).toLocaleDateString();
      console.log(this.data)
    }
  }

}

// <td *ngIf="data.sex == 'male'"><i nz-icon style="color: orangered; font-size: 16px;" nzType="man" nzTheme="outline"></i></td>
// <td *ngIf="data.sex != 'male'"><i nz-icon style="color: rgb(185, 77, 122); font-size: 16px;" nzType="woman" nzTheme="outline"></i></td>
// <td style="color: red;">{{data.local}}</td>