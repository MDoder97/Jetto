/* tslint:disable:no-unused-variable */

import {TestBed, async, inject} from '@angular/core/testing';
import {TicketResolverService} from './ticket-resolver.service';

describe('Service: TicketResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TicketResolverService]
    });
  });

  it('should ...', inject([TicketResolverService], (service: TicketResolverService) => {
    expect(service).toBeTruthy();
  }));
});
