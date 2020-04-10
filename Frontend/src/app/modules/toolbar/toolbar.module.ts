import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ToolbarComponent} from './toolbar.component';
import {FlexModule} from '@angular/flex-layout';
import {RouterModule} from '@angular/router';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [ToolbarComponent],
  imports: [
    CommonModule,
    MatToolbarModule,
    FlexModule,
    RouterModule,
    MatButtonModule
  ],
  exports: [ToolbarComponent]
})
export class ToolbarModule {
}
