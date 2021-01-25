import {Component, OnInit} from '@angular/core';
import {AlertifyService} from '../_services/alertify.service';
import {ActivatedRoute} from '@angular/router';
import {Reservation} from '../_model/Reservation';
import {UserService} from '../_services/user.service';
import {AuthService} from '../_services/auth.service';

@Component({
  selector: 'app-reservations-page',
  templateUrl: './reservations-page.component.html',
  styleUrls: ['./reservations-page.component.scss'],
})
export class ReservationsPageComponent implements OnInit {
  reservations: Reservation[];
  currentDate: Date;

  constructor(
    private authService: AuthService,
    private alertify: AlertifyService,
    public userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      (data: { reservationList: Reservation[] }) => {
        this.reservations = data.reservationList;
      }
    );
    console.log(this.reservations);
    this.currentDate = new Date();
  }

  deleteReservation(reservationId: number) {
    this.userService.deleteReservation(reservationId).subscribe(
      () => {
        this.reservations.splice(this.reservations.findIndex((reservation) => {
          return reservation.id === reservationId;
        }), 1);
        if (this.reservations.length > 0) {
          this.authService.currentUser.bookings = this.reservations;
        } else {
          this.authService.currentUser.bookings = [];
        }
        this.authService.setUserData(this.authService.currentUser);
        this.alertify.success('Reservation deleted!');
      },
      (error) => {
        this.alertify.error(error);
      }
    );
  }

  dayDifference(dateString: Date): number {
    const date = new Date(dateString);
    const oneDay = 1000 * 60 * 60 * 24;
    const start = Date.UTC(date.getFullYear(), date.getMonth(), date.getDate());
    const end = Date.UTC(
      this.currentDate.getFullYear(),
      this.currentDate.getMonth(),
      this.currentDate.getDate()
    );
    return (start - end) / oneDay;
  }
}
