import { Component, OnInit } from "@angular/core";
import { HttpClientService, Role, User } from "../service/httpclient.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-add-user",
  templateUrl: "./add-user.component.html",
  styleUrls: ["./add-user.component.css"],
})
export class AddUserComponent implements OnInit {
  user: User = new User("", "", "", "", new Role(null, ""));

  constructor(
    private httpClientService: HttpClientService,
    private router: Router
  ) {}

  ngOnInit() {}
  private options: number[] = [1,2];
  createUser(): void {
    this.httpClientService.createUser(this.user).subscribe((data) => {
      this.router.navigate([""]);
    });
  }
}
