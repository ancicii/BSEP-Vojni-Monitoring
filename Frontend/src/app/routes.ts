import {Routes} from '@angular/router';
import {LoginComponent} from './modules/login/login.component';
import {DashboardComponent} from "./modules/dashboard/dashboard.component";
import {EndUserCertificateComponent} from "./modules/end-user-certificate/end-user-certificate.component";
import {IntermediateCertificateComponent} from "./modules/intermediate-certificate/intermediate-certificate.component";
import {TemplateComponent} from "./template/template.component";
import {AlarmsComponent} from "./modules/alarms/alarms.component";


export const routes: Routes = [
  {
    path: 'dashboard/:content/preview',
    component: DashboardComponent
  },
  {
    path: 'dashboard',
    redirectTo: 'dashboard/all/preview',
    pathMatch: 'full'
  },
  {
    path: '',
    redirectTo: 'dashboard/all/preview',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'dashboard/create/euc',
    component: EndUserCertificateComponent
  },
  {
    path: 'dashboard/create/ic',
    component: IntermediateCertificateComponent

  },
  {
    path: 'dashboard/template',
    component: TemplateComponent

  },
  {
    path: 'dashboard/alarms',
    component: AlarmsComponent

  }
];
