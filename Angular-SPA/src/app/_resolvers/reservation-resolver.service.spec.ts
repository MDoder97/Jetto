/* tslint:disable:no-unused-variable */

import {TestBed, async, inject} from '@angular/core/testing';
import {ReservationResolverService} from './reservation-resolver.service';

describe('Service: ReservationResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReservationResolverService]
    });
  });

  it('should ...', inject([ReservationResolverService], (service: ReservationResolverService) => {
    expect(service).toBeTruthy();
  }));
});
