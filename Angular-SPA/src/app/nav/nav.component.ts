import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {AlertifyService} from '../_services/alertify.service';
import {Router, ActivatedRoute} from '@angular/router';
import {Reservation} from '../_model/Reservation';
import {UserService} from '../_services/user.service';
import {User} from '../_model/User';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit {
  userType: string;
  reservationNumber = 0;
  userData: User;

  constructor(
    private router: Router,
    private alertify: AlertifyService,
    public authService: AuthService
  ) {
  }

  ngOnInit() {
    this.authService.userData.subscribe((user) => {
      if (user) {
        console.log(user);
        this.userData = user;
        this.userType = this.userData.admin ? 'Admin' : 'User';
        if (this.userData.bookings) {
          this.reservationNumber = this.userData.bookings.length;
        } else {
          this.reservationNumber = 0;
        }
      }
    });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.authService.currentUser = null;
    this.alertify.message('logged out');
    this.router.navigate(['/']);
  }

  reservationPage() {
    this.router.navigate(['/reservations']);
  }

  mainPage() {
    this.router.navigate(['/dashboard']);
  }
}
