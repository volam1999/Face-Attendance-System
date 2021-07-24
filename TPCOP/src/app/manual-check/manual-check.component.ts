
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { getISOWeek } from 'date-fns';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { APIService } from '../api.service';

@Component({
  selector: 'app-manual-check',
  templateUrl: './manual-check.component.html',
  styleUrls: ['./manual-check.component.css']
})
export class ManualCheckComponent implements OnInit {

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

    let uid = this.formHoliday.get("uid")?.value;

    if (!uid) {
      this.notificationService.info("Notification", "UID is REQUIRED!. Please fill one!");
      return
    }

    try {
      let response = await this.apiService.sendGET("http://localhost:8080/check/" + uid, { responseType: "text" })
      this.notificationService.info("Notification", response);
    } catch (error) {
      this.notificationService.error("Notification", error);
    }
  }

}
