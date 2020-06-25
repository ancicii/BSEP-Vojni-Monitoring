import { Component, OnInit } from '@angular/core';
import {SiemCenterService} from '../../core/siem-center.service';

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.scss']
})
export class AlarmsComponent implements OnInit {

  constructor(private siemCenterService: SiemCenterService) { }

  ngOnInit(): void {
  }

  refreshAlarms() {
    this.siemCenterService.getAlarms().subscribe((resData => {
        console.log(resData);
    }
    ));
  }
}
