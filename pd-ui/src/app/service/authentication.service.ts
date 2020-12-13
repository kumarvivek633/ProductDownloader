import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map } from "rxjs/operators";

export class User {
  constructor(public status: string) {}
}

@Injectable({
  providedIn: "root",
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}
  authenticate(username, password) {
    return this.httpClient
      .post<any>("http://localhost:8080/api/authenticate", {
        username,
        password,
      })
      .pipe(
        map((userData) => {
          sessionStorage.setItem("username", username);
          let tokenStr = "Bearer " + userData.id_token;
          sessionStorage.setItem("token", tokenStr);
          sessionStorage.setItem("role", userData.role);
          return userData;
        })
      );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    console.log(!(user === null));
    return !(user === null);
  }

  isUserAdmin() {
    let role = sessionStorage.getItem("role");
    return (role === '[ADMIN]');
  }

  logOut() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("token");
  }
}
