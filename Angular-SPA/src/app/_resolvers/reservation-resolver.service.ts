import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Reservation} from '../_model/Reservation';
import {UserService} from '../_services/user.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationResolverService implements Resolve<Reservation[]> {
  constructor(private userService: UserService) {
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Reservation[] | import('rxjs').Observable<Reservation[]> | Promise<Reservation[]> {
    return this.userService.fetchReservations();
  }
}
