import { Component, OnInit } from '@angular/core';
import {SiemCenterService} from '../../../core/siem-center.service';
import {CertificateApiService} from "../../../core/certificate-api.service";
import {Router} from "@angular/router";
import {EucDataSource} from "../all-preview/all-preview.component";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.scss']
})
export class AlarmsComponent implements OnInit {

  dataSource = new AlarmsDataSource(this.siemCenterService)
  displayedColumns = ['log'];

  constructor(private siemCenterService: SiemCenterService,
              private router: Router) {
  }
  ngOnInit() {
  }

  refreshAlarms() {
    this.dataSource = new AlarmsDataSource(this.siemCenterService)
  }
}

export class AlarmsDataSource extends DataSource<any> {
  constructor(private siemCenterService: SiemCenterService) {
    super();
  }
  connect(): Observable<any> {
    let d = this.siemCenterService.getAlarms();
    console.log(d);
    return d;
  }
  disconnect() {}
}
