import {Component, OnInit} from '@angular/core';
import {Ticket} from '../_model/Ticket';
import {User} from '../_model/User';
import {AuthService} from '../_services/auth.service';
import {TicketService} from '../_services/ticket.service';
import {Flight} from '../_model/Flight';
import {AppConfigService} from '../_services/app-config.service';
import {AlertifyService} from '../_services/alertify.service';
import {Router} from '@angular/router';
import {CompanyService} from '../_services/company.service';
import {Company} from '../_model/Company';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent implements OnInit {
  registerModel: any = {};
  ticketModel: any = {};
  filteredTickets: Ticket[];
  originalTickets: Ticket[];
  companies: Company[];
  datesValid = false;

  filter = {
    origin: '',
    destination: '',
    departDate: '',
    returnDate: '',
  };

  constructor(
    public companyService: CompanyService,
    public authService: AuthService,
    public ticketService: TicketService,
    private alertify: AlertifyService,
    public AppConfigService: AppConfigService
  ) {
  }

  ngOnInit() {
    this.ticketService.fetchTickets().subscribe((ticketList: Ticket[]) => {
      this.filteredTickets = ticketList;
      this.originalTickets = ticketList;
    });
  }

  register() {
    this.authService.register(this.registerModel).subscribe(
      () => {
        this.registerModel = {};
        this.alertify.success('Registration successful!');
      },
      (error) => {
        this.alertify.error(error);
      }
    );
  }

  addTicket() {
    const ticket: Ticket = Object.assign(this.ticketModel);
    this.ticketService.addTicket(ticket).subscribe(
      (addedTicket: Ticket) => {
        this.filteredTickets.push(addedTicket);
        this.alertify.success('Ticket added succesfully!');
      },
      (error) => {
        this.alertify.error(error);
      }
    );
  }

  filterTable() {
    this.filteredTickets = this.originalTickets.filter(
      (ticket) =>
        ticket.flight.origin.name
          .toLowerCase()
          .startsWith(this.filter.origin.toLowerCase()) &&
        ticket.flight.destination.name
          .toLowerCase()
          .startsWith(this.filter.destination.toLowerCase()) &&
        (this.filter.departDate.length > 0
          ? new Date(ticket.departDate) >= new Date(this.filter.departDate)
          : true) &&
        (this.filter.returnDate.length > 0
          ? new Date(ticket.returnDate) <= new Date(this.filter.returnDate)
          : true)
    );
  }

  validateDates() {
    if (this.ticketModel.departDate && this.ticketModel.returnDate) {
      const depart = new Date(this.ticketModel.departDate);
      const ret = new Date(this.ticketModel.returnDate);
      if (depart.getTime() < ret.getTime()) {
        this.datesValid = true;
      } else {
        this.datesValid = false;
        this.alertify.warning('Depart date must be before return date!');
      }
    }
  }
}
