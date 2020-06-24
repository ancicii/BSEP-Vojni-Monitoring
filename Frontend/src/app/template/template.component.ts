import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ILogTemplate, SiemCenterService} from "../core/siem-center.service";

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.scss']
})
export class TemplateComponent implements OnInit {

  newRule: FormGroup;
  osList: string[] = ['windows', 'linux'];
  types: string[] = ['warning', 'critical error', 'message'];

  constructor(private siemCenterService: SiemCenterService) { }

  ngOnInit(){
  this.newRule = new FormGroup({
    name: new FormControl(''),
    number: new FormControl(''),
    time: new FormControl(''),
    os: new FormControl(''),
    type: new FormControl(''),
    message: new FormControl(''),
    sendMessage: new FormControl(''),
  });
  }

  onSubmit() {

    const templateParams: ILogTemplate = {
      name: this.newRule.get('name').value,
      number: this.newRule.get('number').value,
      time: this.newRule.get('time').value,
      os: this.newRule.get('os').value,
      type: this.newRule.get('type').value,
      message: this.newRule.get('message').value,
      sendMessage: this.newRule.get('sendMessage').value
    };

    this.siemCenterService.createTemplate(templateParams).subscribe((resData =>
    console.log(resData)
    ));

  }
}
