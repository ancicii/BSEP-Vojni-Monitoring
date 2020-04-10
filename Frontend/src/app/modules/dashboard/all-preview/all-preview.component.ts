import { Component, OnInit } from '@angular/core';
import {CertificateApiService} from "../../../core/certificate-api.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";

@Component({
  selector: 'app-all-preview',
  templateUrl: './all-preview.component.html',
  styleUrls: ['./all-preview.component.scss']
})
export class AllPreviewComponent implements OnInit {
  dataSource = new EucDataSource(this._certificateApiService)
  displayedColumns = ['serialNumber', 'alias', 'issuerAlias', 'issuerName', 'startDate', 'endDate', 'isActive', 'type'];

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
    return this._certificateApiService.getAllCertificates();
  }
  disconnect() {}
}
