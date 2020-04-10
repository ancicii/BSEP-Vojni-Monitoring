import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {PaginatorComponent} from "../paginator/paginator.component";
import {EucPreviewComponent} from "./euc-preview/euc-preview.component";
import {AllPreviewComponent} from "./all-preview/all-preview.component";
import {IcPreviewComponent} from "./ic-preview/ic-preview.component";
import {AuthenticationApiService} from "../../core/authentication-api.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit, OnInit {
  @ViewChild(PaginatorComponent, {static: false}) paginator: PaginatorComponent;
  @ViewChild(EucPreviewComponent, {static: false}) eucPreviewComponent: EucPreviewComponent;
  @ViewChild(AllPreviewComponent, {static: false}) allPreviewComponent: AllPreviewComponent;
  @ViewChild(IcPreviewComponent, {static: false}) icPreviewComponent: IcPreviewComponent;
  _content: string;
  role: string;

  constructor(private route: ActivatedRoute, private authService: AuthenticationApiService) {
    this.role = this.authService.getRole();
  }

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  ngAfterViewInit() {
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._content = params.get('content');
    });
  }

  private pageChanged($event) {
    switch (this._content) {
      case 'ic':
        // this.icPreviewComponent.pageChanged($event);
        break;
      case 'all':
        // this.allPreviewComponent.pageChanged($event);
        break;
      case 'euc':
        // this.eucPreviewComponent.pageChanged($event);
        break;
    }
  }

  private contentPageChanged($event) {
    this.paginator.page = $event;
  }
}
