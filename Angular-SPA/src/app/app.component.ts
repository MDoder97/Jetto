import { Component, OnInit } from '@angular/core';
import { AuthService } from './_services/auth.service';
import { User } from './_model/User';
import { CompanyService } from './_services/company.service';
import { TicketService } from './_services/ticket.service';
import { AppConfigService } from './_services/app-config.service';
import { Company } from './_model/Company';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'webProject';

  constructor(
    private authService: AuthService,
    public companyService: CompanyService,
    public ticketService: TicketService,
    public AppConfigService: AppConfigService
  ) {}

  ngOnInit() {
    const user: User = JSON.parse(localStorage.getItem('user'));
    if (user) {
      this.authService.currentUser = user;
      this.authService.setUserData(this.authService.currentUser);
      this.companyService
        .fetchCompanies()
        .subscribe((companyList: Company[]) => {
          this.companyService.companies = companyList;
        });
      this.AppConfigService.loadFlights();
    }
  }
}
