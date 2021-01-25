import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../_model/User';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { BehaviorSubject, Subject, UnsubscriptionError } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseUrl = environment.apiUrl + 'auth/';

  user = new BehaviorSubject<User>(undefined);
  userData = this.user.asObservable();

  currentUser: User;

  constructor(private http: HttpClient) {}

  register(userModel: any) {
    return this.http.post(this.baseUrl + 'register', userModel);
  }

  setUserData(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    this.user.next(user);
  }

  login(userModel: any) {
    return this.http
      .post(this.baseUrl + 'login', userModel)
      .pipe(
        map((response: any) => {
          let user = response;
          if (user) {
            localStorage.setItem('token', user.jwt);
            delete user.JWTtoken;

            localStorage.setItem('user', JSON.stringify(user));
            this.currentUser = user;
            this.setUserData(this.currentUser);
          }
        })
      );
  }

  loggedIn(): boolean {
    const user = localStorage.getItem('user');
    return user !== null;
  }
}
