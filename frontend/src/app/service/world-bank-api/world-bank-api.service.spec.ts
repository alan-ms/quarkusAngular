import { TestBed } from '@angular/core/testing';

import { WorldBankApiService } from './world-bank-api.service';

describe('WorldBankApiService', () => {
  let service: WorldBankApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorldBankApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
