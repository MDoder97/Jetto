import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TicketService} from './ticket.service';
import {environment} from 'src/environments/environment';
import {CompanyService} from './company.service';

@Injectable({
  providedIn: 'root',
})
export class AppConfigService {
  private apiEndpointFlights = environment.apiUrl + 'flights';
  private apiEndpointCompanies = environment.apiUrl + 'companies';
  private apiEndpointTickets = environment.apiUrl + 'tickets';

  constructor(
    private http: HttpClient,
    private companyService: CompanyService,
    private ticketService: TicketService
  ) {
  }

  loadFlights(): Promise<any> {
    const promise = this.http
      .get(this.apiEndpointFlights)
      .toPromise()
      .then((data) => {
        Object.assign(this.ticketService.flights, data);
        return data;
      });

    return promise;
  }
}
