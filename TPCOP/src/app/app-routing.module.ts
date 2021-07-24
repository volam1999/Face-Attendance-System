import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AttComponent } from './att/att.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HolidayComponent } from './holiday/holiday.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { ManualCheckComponent } from './manual-check/manual-check.component';
import { P404Component } from './p404/p404.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },

  {
    path: 'TPCOP', component: DashboardComponent, children: [
      { path: 'index', component: IndexComponent },
      { path: 'users', component: UserComponent },
      { path: 'att', component: AttComponent },
      { path: 'holiday', component: HolidayComponent },
      { path: 'manual-check', component: ManualCheckComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '404', component: P404Component },
  { path: '**', redirectTo: "404" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
