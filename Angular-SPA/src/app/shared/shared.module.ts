import {NgModule} from '@angular/core';
import {MaterialModule} from './material/material.module';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {TableComponent} from '../table/table.component';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [TableComponent],
  imports: [
    MaterialModule,
    FormsModule,
    CommonModule
  ],
  exports: [
    MaterialModule,
    ReactiveFormsModule,
    TableComponent,
    FormsModule,
    CommonModule
  ]
})
export class SharedModule {
}
