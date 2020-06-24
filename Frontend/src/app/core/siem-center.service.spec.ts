import { TestBed } from '@angular/core/testing';

import { SiemCenterService } from './siem-center.service';

describe('SiemCenterService', () => {
  let service: SiemCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SiemCenterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
