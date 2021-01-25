import {Injectable} from '@angular/core';
import {
  Resolve,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import {Ticket} from '../_model/Ticket';
import {TicketService} from '../_services/ticket.service';
import {Reservation} from '../_model/Reservation';

@Injectable({
  providedIn: 'root',
})
export class TicketResolverService implements Resolve<Ticket> {
  constructor() {
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Ticket | import('rxjs').Observable<Ticket> | Promise<Ticket> {
    return JSON.parse(localStorage.getItem('ticketToUpdate'));
  }
}
