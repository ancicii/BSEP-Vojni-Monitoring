import { Component, OnInit } from '@angular/core';
import {DatePipe} from "@angular/common";
import {CertificateApiService} from "../../../core/certificate-api.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-ic-preview',
  templateUrl: './ic-preview.component.html',
  styleUrls: ['./ic-preview.component.scss']
})
export class IcPreviewComponent implements OnInit {
  dataSource = new IcDataSource(this._certificateApiService)
  displayedColumns = ['serialNumber', 'alias', 'issuerAlias', 'issuerName',
    'startDate', 'endDate', 'isActive', 'revoke'];

  constructor(private _certificateApiService: CertificateApiService,
              private router: Router) {
  }
  ngOnInit() {
  }

  revoke(alias: any) {
    this._certificateApiService.revokeCertificate(alias).subscribe({
      next: () => {
        this.dataSource = new IcDataSource(this._certificateApiService)
      }
    })
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
