import {Component, OnInit} from '@angular/core';
import {CertificateApiService} from "../../../core/certificate-api.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-euc-preview',
  templateUrl: './euc-preview.component.html',
  styleUrls: ['./euc-preview.component.scss']
})
export class EucPreviewComponent implements OnInit {
  dataSource = new EucDataSource(this._certificateApiService)
  displayedColumns = ['serialNumber', 'alias', 'issuerAlias', 'issuerName', 'startDate', 'endDate', 'isActive'];
  pipe = new DatePipe('en-US');

  constructor(private _certificateApiService: CertificateApiService) {
  }
  ngOnInit() {
  }
}

export class EucDataSource extends DataSource<any> {
  constructor(private _certificateApiService: CertificateApiService) {
    super();
  }
  connect(): Observable<any> {
    return this._certificateApiService.getEndUserCertificates();
  }
  disconnect() {}
}
