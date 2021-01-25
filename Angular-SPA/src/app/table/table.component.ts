import {Component, OnInit, Input} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Ticket} from '../_model/Ticket';
import {TicketService} from '../_services/ticket.service';
import {AlertifyService} from '../_services/alertify.service';
import {Router} from '@angular/router';
import {AppConfigService} from '../_services/app-config.service';
import {Company} from '../_model/Company';
import {UserService} from '../_services/user.service';
import {Reservation} from '../_model/Reservation';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit {
  ticketType = 'All';

  originalTickets: Ticket[];
  tickets: Ticket[];

  constructor(
    private router: Router,
    private alertify: AlertifyService,
    public authService: AuthService,
    public ticketService: TicketService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
  }

  @Input() set ticketList(ticketList: Ticket[]) {
    this.tickets = ticketList;
    this.originalTickets = ticketList;
    console.log(this.originalTickets);
  }

  companyPage(company: Company) {
    localStorage.setItem('company', JSON.stringify(company));
    this.router.navigate(['/company/' + company.id]);
  }

  addReservation(ticket: Ticket) {
    if (ticket.count > 0) {
      this.userService.addReservation(ticket).subscribe(
        (addedReservation: Reservation) => {
          ticket.count = ticket.count - 1;
          if (this.authService.currentUser.bookings) {
            this.authService.currentUser.bookings.push(addedReservation);
          } else {
            this.authService.currentUser.bookings = [];
            this.authService.currentUser.bookings.push(addedReservation);
          }
          this.authService.setUserData(this.authService.currentUser);
          this.alertify.success('Ticket reserved!');
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      this.alertify.error('No tickets left!');
    }
  }

  deleteTicket(ticketToDelete: Ticket) {
    this.ticketService.deleteTicket(ticketToDelete.id).subscribe(
      () => {
        this.tickets.splice(this.tickets.findIndex((ticket) => {
          return ticket.id === ticketToDelete.id;
        }), 1);
        this.originalTickets = this.tickets;
        this.alertify.success('Ticket deleted!');
      },
      (error) => {
        console.log(error);
      }
    );
  }

  updateTicket(ticket: Ticket) {
    localStorage.setItem('ticketToUpdate', JSON.stringify(ticket));
    this.router.navigate(['/ticket']);
  }

  filterType(value: string) {
    if (value === 'All') {
      this.tickets = this.originalTickets;
    } else {
      this.tickets = this.originalTickets.filter((ticket) =>
        value === 'OneWay' ? ticket.oneWay : !ticket.oneWay
      );
    }
  }
}
