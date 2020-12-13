import { Component, OnInit } from '@angular/core';
import { UploadFileService } from 'src/app/service/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: "app-upload-files",
  templateUrl: "./upload-files.component.html",
  styleUrls: ["./upload-files.component.css"]
})
export class UploadFilesComponent implements OnInit {

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileList: any;
  token: any;
  role: string;
  fileInfos: Observable<any>;

  constructor(private uploadService: UploadFileService) { }
  ngOnInit() {
    this.token = sessionStorage.getItem('token');
    this.role = sessionStorage.getItem('role');
    this.token = this.token.substr(7);
    this.uploadService.getFiles().subscribe(data => {
      this.fileInfos = data;
      this.fileList = this.fileInfos;
    });
  }
  selectFile(event) {
    this.selectedFiles = event.target.files;
    this.fileList = event.target.files;
  }


  upload() {
    this.progress = 0;
  
    this.currentFile = this.selectedFiles.item(0);
    this.uploadService.upload(this.currentFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = 'File Uploaded!';
          this.uploadService.getFiles().subscribe(data => {
            this.fileInfos = data;
            this.fileList = this.fileInfos;
          });
          this.fileList = this.fileInfos;
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });
  
    this.selectedFiles = undefined;
  }
}