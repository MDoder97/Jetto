/* tslint:disable:no-unused-variable */

import {TestBed, async, inject} from '@angular/core/testing';
import {CompanyResolverService} from './company-resolver.service';

describe('Service: CompanyResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CompanyResolverService]
    });
  });

  it('should ...', inject([CompanyResolverService], (service: CompanyResolverService) => {
    expect(service).toBeTruthy();
  }));
});
