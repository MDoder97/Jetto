import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CompanyPageRoutingModule} from './company-page-routing.module';
import {CompanyPageComponent} from './company-page.component';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {TableComponent} from '../table/table.component';


@NgModule({
  declarations: [CompanyPageComponent],
  imports: [
    FormsModule,
    SharedModule,
    CommonModule,
    CompanyPageRoutingModule
  ]
})
export class CompanyPageModule {
}
