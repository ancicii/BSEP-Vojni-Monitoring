import {Component, OnInit, Output, EventEmitter, ViewChild} from '@angular/core';
import {Page} from '../../shared/model/page.model';
import {MatPaginator, PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent implements OnInit {
  _page: Page;
  @Output() pageChangedEvent = new EventEmitter<PageEvent>();
  @ViewChild(MatPaginator) matPaginator: MatPaginator;

  constructor() {
  }
  ngOnInit() {
    this._page = new Page();
  }

  get page(): Page {
    return this._page;
  }

  set page(value: Page) {
    this._page = value;
  }

  pageChanged(event: PageEvent) {
    this.pageChangedEvent.emit(event);
  }

}
