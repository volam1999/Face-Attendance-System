import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  link: string;
  links: string[];
  SESSION: string | null;

  constructor(private router: Router) {
    this.link = this.router.url;
    this.links = this.handlerBreadcum(this.link);
    this.links = this.links.filter(item => item != "");
    console.log(this.links)
    this.SESSION = localStorage.getItem("SESSION");
  }

  isCollapsed = true
  ngOnInit(): void {
  }

  handlerBreadcum(path: String): string[] {
    return path.split("/");
  }

  updateBreadcum() {
    setTimeout(() => {
      this.link = this.router.url;
      this.links = this.handlerBreadcum(this.link);
      this.links = this.links.filter(item => item != "");
    }, 10);
  }

  logout() {
    localStorage.removeItem("SESSION");
    this.router.navigate(["../login"]);
  }

}
