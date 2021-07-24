import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { getISOWeek } from 'date-fns';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { APIService } from '../api.service';

@Component({
  selector: 'app-holiday',
  templateUrl: './holiday.component.html',
  styleUrls: ['./holiday.component.css']
})
export class HolidayComponent implements OnInit {
  date: any;
  constructor(private fb: FormBuilder, private apiService: APIService, private notificationService: NzNotificationService) { }
  formHoliday!: FormGroup;

  ngOnInit(): void {
    this.formHoliday = this.fb.group({
      uid: [null, [Validators.required]],
    });
  }

  async submitForm() {

    for (const i in this.formHoliday.controls) {
      this.formHoliday.controls[i].markAsDirty();
      this.formHoliday.controls[i].updateValueAndValidity();
    }


    let startDay = new Date(this.date[0]);
    let endDay = new Date(this.date[1]);
    let uid = this.formHoliday.get("uid")?.value;

    if (startDay.getDate() > endDay.getDate()) {
      let temp = startDay;
      startDay = endDay;
      endDay = temp;
    }
    let dayFormat = startDay.getDate() + ":" + endDay.getDate();
    try {
      let response = await this.apiService.sendGET("http://localhost:8080/holiday/leave/" + uid + "/" + dayFormat, { responseType: "text" })
      this.notificationService.info("Notification", response);
    } catch (error) {
      this.notificationService.error("Notification", error);
    }
  }

}
