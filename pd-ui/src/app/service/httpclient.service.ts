import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

export class User {
  constructor(
    public userName: string,
    public userPassword: string,
    public userFirstName: string,
    public userLastName: string,
    public role: Role
  ) {}
}

export class Role {
  constructor(
    public id: Number,
    public roleName: string
  ) {}
}

@Injectable({
  providedIn: "root"
})
export class HttpClientService {
  constructor(private httpClient: HttpClient) {}

  getUsers() {
    return this.httpClient.get<User[]>("http://localhost:8080/api/all-users");
  }

  public deleteUser(user) {
    return this.httpClient.delete<User>(
      "http://localhost:8080/api/users" + "?userName=" + user.userName
    );
  }

  public createUser(user) {
    return this.httpClient.post<User>(
      "http://localhost:8080/api/users",
      user
    );
  }
}
