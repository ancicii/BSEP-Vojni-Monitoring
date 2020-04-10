import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {CoreModule} from "../../core/core.module";
import {FlexModule} from '@angular/flex-layout';
import {PaginatorComponent} from "../paginator/paginator.component";
import {RouterModule} from '@angular/router';
import {ToolbarModule} from '../toolbar/toolbar.module';
import {AllPreviewComponent} from "./all-preview/all-preview.component";
import {EucPreviewComponent} from "./euc-preview/euc-preview.component";
import {IcPreviewComponent} from "./ic-preview/ic-preview.component";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [DashboardComponent, PaginatorComponent, AllPreviewComponent, EucPreviewComponent, IcPreviewComponent],
  imports: [
    CommonModule,
    CoreModule,
    FlexModule,
    RouterModule,
    ToolbarModule,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule
  ],
  providers:[]
})
export class DashboardModule {
}
