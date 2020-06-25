import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CoreModule} from './core/core.module';
import {ToolbarModule} from './modules/toolbar/toolbar.module';
import {LoginModule} from './modules/login/login.module';
import {SharedModule} from './shared/shared.module';
import {UrlInterceptor} from './core/interceptors/url.interceptor';
import {TokenInterceptor} from './core/interceptors/token.interceptor';
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
// import {EucPreviewModule} from "./modules/dashboard/euc-preview/euc-preview.module";
import {DashboardModule} from "./modules/dashboard/dashboard.module";
import {EndUserCertificateModule} from "./modules/end-user-certificate/end-user-certificate.module";
import {IntermediateCertificateModule} from "./modules/intermediate-certificate/intermediate-certificate.module";
import { TemplateComponent } from './template/template.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import { AlarmsComponent } from './modules/alarms/alarms.component';

@NgModule({
  declarations: [
    AppComponent,
    TemplateComponent,
    AlarmsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CoreModule,
    ToolbarModule,
    DashboardModule,
    LoginModule,
    SharedModule,
    FlexLayoutModule,
    MatInputModule,
    MatTableModule,
    EndUserCertificateModule,
    IntermediateCertificateModule,
    ReactiveFormsModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: UrlInterceptor, multi: true}],
  bootstrap: [AppComponent]

})
export class AppModule { }
