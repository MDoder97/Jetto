import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Ticket} from '../_model/Ticket';
import {Flight} from '../_model/Flight';
import {Company} from '../_model/Company';
import {map, share} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {AuthService} from './auth.service';
import {Reservation} from '../_model/Reservation';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  baseUrl = environment.apiUrl + 'tickets/';

  public flights: Flight[] = [];

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  addTicket(ticket: Ticket) {
    return this.http.post(this.baseUrl, ticket);
  }

  deleteTicket(ticketId: number) {
    return this.http.delete(this.baseUrl + ticketId);
  }

  updateTicket(ticket: Ticket) {
    return this.http.put(this.baseUrl, ticket);
  }

  fetchTickets() {
    return this.http.get(this.baseUrl);
  }
  
}
