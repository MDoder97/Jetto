import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {Company} from '../_model/Company';
import {map} from 'rxjs/operators';
import {TicketService} from './ticket.service';
import {Ticket} from '../_model/Ticket';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  baseUrl = environment.apiUrl + 'companies/';
  public companies: Company[] = [];

  constructor(private ticketService: TicketService, private http: HttpClient) {
  }

  updateCompany(company: Company) {
    return this.http.put(this.baseUrl, company);
  }

  addCompany(company: Company) {
    return this.http.post(this.baseUrl, company).pipe(
      map((addedTicket: any) => {
        this.companies.push(addedTicket);
      })
    );
  }

  deleteCompany(companyId: number) {
    return this.http.delete(this.baseUrl + companyId).pipe(
      map((companyList: Company[]) => {
        Object.assign(this.companies, companyList);
        this.ticketService.fetchTickets();
      })
    );
  }

  fetchCompanies() {
    return this.http.get(this.baseUrl);
  }

  fetchCompanyTickets(companyId: number): Observable<any> {
    return this.http.get(this.baseUrl + companyId);
  }
}
