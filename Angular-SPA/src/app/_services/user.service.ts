import {Injectable} from '@angular/core';
import {Reservation} from '../_model/Reservation';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {AuthService} from './auth.service';
import {map} from 'rxjs/operators';
import {Ticket} from '../_model/Ticket';
import {User} from '../_model/User';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  baseUrl = environment.apiUrl + 'users/';

  reservations: Reservation[] = [];

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getReservations() {
    return this.http.get<Reservation[]>(
      this.baseUrl + this.authService.currentUser.id
    );
  }

  deleteReservation(reservationId: number) {
    return this.http.post(this.baseUrl + 'delete/' + this.authService.currentUser.id, reservationId);
  }

  addReservation(ticket: Ticket) {
    return this.http.post(this.baseUrl + 'add/' + this.authService.currentUser.id, ticket.id);
  }

  fetchReservations(): Promise<any> {
    const promise = this.http
      .get(this.baseUrl + this.authService.currentUser.id)
      .toPromise()
      .then((data) => {
        Object.assign(this.reservations, data);
        return data;
      });
    return promise;
  }
}
