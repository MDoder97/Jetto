import {Component, OnInit, OnDestroy} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Company} from '../_model/Company';
import {TicketService} from '../_services/ticket.service';
import {CompanyService} from '../_services/company.service';
import {AlertifyService} from '../_services/alertify.service';
import {AppConfigService} from '../_services/app-config.service';
import {Ticket} from '../_model/Ticket';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-company-page',
  templateUrl: './company-page.component.html',
  styleUrls: ['./company-page.component.scss'],
})
export class CompanyPageComponent implements OnInit, OnDestroy {
  currentCompany: Company;
  ticketType = 'All';
  newCompany: Company = {
    id: 0,
    name: '',
  };
  updatedCompanyName: string;

  companyTickets: Ticket[];

  constructor(
    private router: Router,
    private alertify: AlertifyService,
    public companyService: CompanyService,
    public authService: AuthService,
    public ticketService: TicketService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data: { ticketList: Ticket[] }) => {
      this.companyTickets = data.ticketList;
    });
    this.currentCompany = JSON.parse(localStorage.getItem('company'));
  }

  ngOnDestroy(): void {
    localStorage.removeItem('company');
  }

  updateCompany() {
    const companyToUpdate = this.currentCompany;
    companyToUpdate.name = this.updatedCompanyName;
    this.companyService.updateCompany(companyToUpdate).subscribe(
      (updatedCompany: Company) => {
        localStorage.setItem('company', JSON.stringify(updatedCompany));
        Object.assign(this.currentCompany, updatedCompany);
        this.companyService.fetchCompanies();
        this.companyService.fetchCompanyTickets(updatedCompany.id).subscribe(
          (ticketList: Ticket[]) => this.companyTickets = ticketList
        );
        this.alertify.success('Company updated!');
      },
      (error) => {
        this.alertify.error(error.error);
      }
    );
  }

  deleteCompany() {
    this.companyService.deleteCompany(this.currentCompany.id).subscribe(
      (next) => {
        this.companyService.fetchCompanies();
        this.router.navigate(['/dashboard']);
        this.alertify.success('Company deleted!');
      },
      (error) => {
        this.alertify.error(error.error);
      }
    );
  }

  addCompany() {
    this.companyService.addCompany(this.newCompany).subscribe(
      (next) => {
        this.alertify.success('Company added!');
      },
      (error) => {
        this.alertify.error(error.error);
      }
    );
  }
}
