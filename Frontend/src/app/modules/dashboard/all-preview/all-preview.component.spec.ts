import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllPreviewComponent } from './all-preview.component';

describe('AllPreviewComponent', () => {
  let component: AllPreviewComponent;
  let fixture: ComponentFixture<AllPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
