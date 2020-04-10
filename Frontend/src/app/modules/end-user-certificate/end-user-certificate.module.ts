import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EndUserCertificateComponent } from './end-user-certificate.component';
import {ToolbarModule} from "../toolbar/toolbar.module";
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatButtonModule} from "@angular/material/button";
import {FlexModule} from "@angular/flex-layout";

@NgModule({
  declarations: [EndUserCertificateComponent],
  imports: [
    CommonModule,
    ToolbarModule,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    FlexModule
  ],
  exports: [
    EndUserCertificateComponent
  ]
})
export class EndUserCertificateModule { }
