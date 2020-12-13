import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddUserComponent } from './add-employee/add-user.component';
import { UploadFilesComponent } from './file/upload-files.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGaurdService } from './service/auth-gaurd.service';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: 'viewUser', component: UserComponent,canActivate:[AuthGaurdService] },
  { path: 'addUser', component: AddUserComponent,canActivate:[AuthGaurdService]},
  { path: '', component: UploadFilesComponent,canActivate:[AuthGaurdService]},
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent,canActivate:[AuthGaurdService] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
