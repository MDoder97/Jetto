import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TicketPageRoutingModule} from './ticket-page-routing.module';
import {TicketPageComponent} from './ticket-page.component';
import {SharedModule} from '../shared/shared.module';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [TicketPageComponent],
  imports: [
    SharedModule,
    CommonModule,
    FormsModule,
    TicketPageRoutingModule
  ]
})
export class TicketPageModule {
}
