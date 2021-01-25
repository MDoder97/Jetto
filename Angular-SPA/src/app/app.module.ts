import {BrowserModule} from '@angular/platform-browser';
import {NgModule, APP_INITIALIZER} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SharedModule} from './shared/shared.module';
import {MainPageComponent} from './main-page/main-page.component';
import {NavComponent} from './nav/nav.component';
import {AuthService} from './_services/auth.service';
import {TicketService} from './_services/ticket.service';
import {HttpClientModule} from '@angular/common/http';
import {AppConfigService} from './_services/app-config.service';
import {JwtModule} from '@auth0/angular-jwt';
import {AlertifyService} from './_services/alertify.service';
import {CompanyService} from './_services/company.service';
import {ErrorInterceptorProvider} from './_services/error.interceptor';

export function appInit(appConfigService: AppConfigService) {
  return () => {
    // appConfigService.loadFlights();
  };
}

export function tokenGetter() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [AppComponent, LoginComponent, MainPageComponent, NavComponent],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: ['localhost:8080/api/users'],
      },
    }),
  ],
  providers: [
    ErrorInterceptorProvider,
    AuthService,
    TicketService,
    AppConfigService,
    AlertifyService,
    CompanyService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      multi: true,
      deps: [AppConfigService],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
