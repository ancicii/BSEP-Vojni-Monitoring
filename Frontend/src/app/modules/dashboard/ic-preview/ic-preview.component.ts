import { Component, OnInit } from '@angular/core';
import {DatePipe} from "@angular/common";
import {CertificateApiService} from "../../../core/certificate-api.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";

@Component({
  selector: 'app-ic-preview',
  templateUrl: './ic-preview.component.html',
  styleUrls: ['./ic-preview.component.scss']
})
export class IcPreviewComponent implements OnInit {
  dataSource = new IcDataSource(this._certificateApiService)
  displayedColumns = ['serialNumber', 'alias', 'issuerAlias', 'issuerName', 'startDate', 'endDate', 'isActive'];

  constructor(private _certificateApiService: CertificateApiService) {
  }
  ngOnInit() {
  }
}

export class IcDataSource extends DataSource<any> {
  constructor(private _certificateApiService: CertificateApiService) {
    super();
  }
  connect(): Observable<any> {
    return this._certificateApiService.getIntermediateCertificates();
  }
  disconnect() {}
}
