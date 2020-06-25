import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ILogTemplate, SiemCenterService} from "../../../core/siem-center.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.scss']
})
export class TemplateComponent implements OnInit {

  newRule: FormGroup;
  osList: string[] = ['windows', 'linux'];
  types: string[] = ['warning', 'critical error', 'message'];

  constructor(private siemCenterService: SiemCenterService, private router: Router) { }

  ngOnInit(){
  this.newRule = new FormGroup({
    name: new FormControl('',[Validators.required]),
    number: new FormControl('',[Validators.required]),
    time: new FormControl('',[Validators.required]),
    message: new FormControl('',[Validators.required]),
    sendMessage: new FormControl('',[Validators.required]),
  });
  }

  onSubmit() {

    const templateParams: ILogTemplate = {
      name: this.newRule.get('name').value,
      number: this.newRule.get('number').value,
      time: this.newRule.get('time').value,
      message: this.newRule.get('message').value,
      sendMessage: this.newRule.get('sendMessage').value
    };

    this.siemCenterService.createTemplate(templateParams).subscribe((resData =>
        this.router.navigate(['/dashboard/all/preview'])
    ));

  }
}
