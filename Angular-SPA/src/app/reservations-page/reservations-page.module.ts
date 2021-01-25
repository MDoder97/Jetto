import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ReservationsPageRoutingModule} from './reservations-page-routing.module';
import {ReservationsPageComponent} from './reservations-page.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [ReservationsPageComponent],
  imports: [
    SharedModule,
    CommonModule,
    ReservationsPageRoutingModule
  ]
})
export class ReservationsPageModule {
}
