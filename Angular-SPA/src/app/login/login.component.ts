import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';
import {AlertifyService} from '../_services/alertify.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginModel: any = {};

  constructor(
    private alertify: AlertifyService,
    private router: Router,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    if (this.authService.loggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }

  login() {
    this.authService.login(this.loginModel).subscribe(
      (next) => {
        this.alertify.success('Logged in!');
      },
      (error) => {
        this.alertify.error(error.error);
      },
      () => {
        this.router.navigate(['/dashboard']);
      }
    );
  }
}
