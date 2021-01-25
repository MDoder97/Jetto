import {Component, OnInit, OnDestroy} from '@angular/core';
import {TicketService} from '../_services/ticket.service';
import {Ticket} from '../_model/Ticket';
import {ActivatedRoute, Router} from '@angular/router';
import {Company} from '../_model/Company';
import {Flight} from '../_model/Flight';
import {AlertifyService} from '../_services/alertify.service';
import {CompanyService} from '../_services/company.service';

@Component({
  selector: 'app-ticket-page',
  templateUrl: './ticket-page.component.html',
  styleUrls: ['./ticket-page.component.scss']
})
export class TicketPageComponent implements OnInit {
  ticket: Ticket;

  constructor(
    public companyService: CompanyService,
    private router: Router,
    private alertify: AlertifyService,
    public ticketService: TicketService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data: { ticketToUpdate: Ticket }) => {
      this.ticket = data.ticketToUpdate;
    });
  }

  validateDates(): boolean {
    const depart = new Date(this.ticket.departDate);
    const ret = new Date(this.ticket.returnDate);
    return depart.getTime() < ret.getTime();
  }

  updateTicket() {
    this.ticketService.updateTicket(this.ticket).subscribe(
      (next) => {
        this.alertify.success('Ticket updated succesfully!');
        this.router.navigate(['/dashboard']);
        localStorage.removeItem('ticketToUpdate');
      },
      (error) => {
        this.alertify.error(error);
      }
    );
  }

  compareCompanies(company1: Company, company2: Company) {
    return company1 && company2
      ? company1.id === company2.id
      : company1 === company2;
  }

  compareFlights(flight1: Flight, flight2: Flight) {
    return flight1 && flight2 ? flight1.id === flight2.id : flight1 === flight2;
  }

}
