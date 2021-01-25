import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {MainPageComponent} from './main-page/main-page.component';
import {TicketResolverService} from './_resolvers/ticket-resolver.service';
import {ReservationResolverService} from './_resolvers/reservation-resolver.service';
import {CompanyResolverService} from './_resolvers/company-resolver.service';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: LoginComponent},
  {path: 'dashboard', pathMatch: 'full', component: MainPageComponent},
  {
    path: 'company/:id',
    loadChildren: () =>
      import('./company-page/company-page.module').then(
        (m) => m.CompanyPageModule
      ),
    resolve: {ticketList: CompanyResolverService},
    data: {path: 'company/:id'}
  },
  {
    path: 'reservations',
    loadChildren: () =>
      import('./reservations-page/reservations-page.module').then(
        (m) => m.ReservationsPageModule
      ),
    resolve: {reservationList: ReservationResolverService}
  },
  {
    path: 'ticket',
    pathMatch: 'full',
    loadChildren: () =>
      import('./ticket-page/ticket-page.module').then(
        (m) => m.TicketPageModule
      ),
    resolve: {ticketToUpdate: TicketResolverService},
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
