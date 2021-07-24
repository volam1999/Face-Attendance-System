import { Component, OnInit } from '@angular/core';
import { APIService } from '../api.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  totalActiveUser: number | undefined;
  totalEmployee: number | undefined;
  totalDepartment: number | undefined;
  current = 0;
  index = 'First-content';

  pre(): void {
    this.current -= 1;
    this.changeContent();
  }

  next(): void {
    this.current += 1;
    this.changeContent();
  }

  done(): void {
    console.log('done');
  }

  changeContent(): void {
    switch (this.current) {
      case 0: {
        this.index = 'First-content';
        break;
      }
      case 1: {
        this.index = 'Second-content';
        break;
      }
      case 2: {
        this.index = 'third-content';
        break;
      }
      default: {
        this.index = 'error';
      }
    }
  }

  constructor(private apiService: APIService) { }

  async ngOnInit() {
    this.totalActiveUser = await this.apiService.sendGET("http://localhost:8080/user/count/1");
    this.totalEmployee = await this.apiService.sendGET("http://localhost:8080/user/count/");
    this.totalDepartment = await this.apiService.sendGET("http://localhost:8080//department/count");
  }

}
// https://hookagency.com/website-color-schemes/