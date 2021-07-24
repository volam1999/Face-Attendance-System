import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NzModalService } from 'ng-zorro-antd/modal';
import { APIService } from '../api.service';
import { UserModel } from '../model/UserModel';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @ViewChild('tempplateNotification') template?: TemplateRef<{}>;

  dataSet: any[] = [];
  filterDataSet: any[] = [];
  sourceData: any[] = [];
  listUser: UserModel[] = [];
  isLoading = false;
  isVisible = false;
  currentUserName = "";
  searchValue = "";
  visible = false;
  isDrawerVisible = false;
  profile: any;
  session: String | null = "";
  isDisableUpdateButton = true;
  updateUserInfoForm!: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private apiService: APIService, private modal: NzModalService, private notificationService: NzNotificationService) { }

  sortOrder: null;
  sortDirections = ['ascend', 'descend', null];
  listOfColumn = [
    {
      title: 'Modified Date',
      compare: (a: any, b: any) => {
        return (a.modifiedDate as string).localeCompare((b.modifiedDate))
      },
    },
    {
      title: 'Created Date',
      compare: (a: any, b: any) => {
        return (a.createdDate as string).localeCompare((b.createdDate))
      },
    },
    {
      title: 'Status',
      compare: (a: any, b: any) => {
        return a.status - b.status;
      },
    }
  ]

  async ngOnInit() {
    this.session = localStorage.getItem("SESSION");
    this.dataSet = await this.apiService.sendGET("http://localhost:8080/user/" + this.session);
    this.dataSet?.forEach(element => {
      element.birthDay = new Date(element.birthDay).toLocaleDateString()
      element.modifiedDate = new Date(element.modifiedDate).toLocaleString()
      element.createdDate = new Date(element.createdDate).toLocaleString()
    });
    this.sourceData = this.dataSet;
  }

  showUserInfoModal(username: string) {
    this.currentUserName = username;
    this.isVisible = true;
  }

  showDeleteConfirm(id: string): void {
    this.modal.confirm({
      nzTitle: 'Are you sure delete this user?',
      nzOkText: 'Yes',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () => this.onDelete(id),
      nzCancelText: 'No',
      nzOnCancel: () => console.log('Cancel')
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

    let fullname = "-"
    if (this.profile.firstname) {
      fullname = this.profile.firstname;
    }
    if (this.profile.lastname) {
      fullname += this.profile.lastname;
    }

    this.updateUserInfoForm = this.fb.group({
      fullname: [fullname],
      username: [this.profile.username],
      local: [this.profile.local],
      sex: [this.profile.sex],
      birthDay: [this.profile.birthDay],
      email: [this.profile.email],
      phone: [this.profile.phoneNumber],
    });

    this.isDrawerVisible = true;
    this.isDisableUpdateButton = true;
  }

  handleCancel() { this.isVisible = false }

  handleOk() { this.isVisible = false }

  reset() {
    this.searchValue = ""
    this.dataSet = this.sourceData;
  }

  search() {
    console.log(this.searchValue);
    this.filterDataSet = this.sourceData.filter(item => (item.username as string).includes(this.searchValue));
    this.dataSet = this.filterDataSet;
  }

  close() {
    this.isDrawerVisible = false;
  }

  onDelete(id: string) {
    this.dataSet = this.dataSet.filter(item => item.id != id);
  }

  async activeOrDeactiveAccount(id: string) {
    try {
      let actByUser = localStorage.getItem("SESSION");
      console.log(actByUser)
      let data = {
        "username": actByUser,
        "id": id
      }

      await this.apiService.sendPOST("http://localhost:8080/user/control/", data, { responseType: 'text' });

      this.dataSet.forEach(element => {
        if (element.id == id) {
          var status, color;
          let now = new Date();
          element.modifiedDate = now.toLocaleString();
          element.modifiedBy = actByUser;
          if (element.status == 1) {
            //this.notificationService.warning("Notification", "Account " + element.username + " has been deactivated !")
            status = "deactivated";
            color = "red";
          } else {
            //this.notificationService.success("Notification", "Account " + element.username + " have been activated successfully !");
            status = "activated";
            color = "green";
          }
          const data = {
            'username': element.username,
            'color': color,
            'status': status,
          }
          this.notificationService.template(this.template!, { nzData: data })
          element.status = !element.status;
        }
      })
    } catch (error) {
      console.log(error)
      this.notificationService.error("Notification", error.statusText);
    }
  }

  async updateUserInfo(id: string) {
    console.log(id)
    let username = this.updateUserInfoForm.get("username")?.value;
    let fullname = this.updateUserInfoForm.get("fullname")?.value;
    let firstname = "";
    let lastname = ""
    if (fullname != "-") {
      firstname = (fullname as string).substr(0, (fullname as string).lastIndexOf(" "));
      lastname = (fullname as string).substring((fullname as string).lastIndexOf(" "));
    }
    let loc = this.updateUserInfoForm.get("local")?.value;
    let birthDay = this.updateUserInfoForm.get("birthDay")?.value;
    let email = this.updateUserInfoForm.get("email")?.value;
    let phone = this.updateUserInfoForm.get("phone")?.value;
    phone = (phone as string).replace(/ /g, '');

    let sex = this.updateUserInfoForm.get("sex")?.value;

    let data = {
      "id": id,
      "username": username,
      "firstname": firstname,
      "lastname": lastname,
      "local": loc,
      "birthDay": Date.parse(birthDay),
      "email": email,
      "phoneNumber": phone,
      "sex": sex
    }
    try {
      await this.apiService.sendPUT("http://localhost:8080/user/update", data, { responseType: 'text' });
      this.notificationService.success("Notification", "Update profile <b>" + id + "</b> successfully !");
    } catch (error) {
      this.notificationService.error("Notification", "Update user profile <b>" + id + "</b> failled !");
    }
    this.isDisableUpdateButton = true;
  }

  enableUpdate() {
    this.isDisableUpdateButton = false;
  }

  addClickEvent(e: any) {
    let x = e.currentTarget.getBoundingClientRect();
    console.log(e)
    if (e.type === 'click') {
      this.visible = true;
    }
    else if (e.type === 'mouseenter') {
      this.visible = true;
    }
    else if (e.type === 'mouseleave' && e.clientX > x.right) {
      this.visible = false
    }
  }


}
