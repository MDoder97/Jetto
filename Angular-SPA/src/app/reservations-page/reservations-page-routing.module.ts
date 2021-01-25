import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ReservationsPageComponent} from './reservations-page.component';

const routes: Routes = [{path: '', component: ReservationsPageComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReservationsPageRoutingModule {
}
