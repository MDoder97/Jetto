import {Injectable} from '@angular/core';
import {
  Resolve,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import {Ticket} from '../_model/Ticket';
import {CompanyService} from '../_services/company.service';

@Injectable({
  providedIn: 'root',
})
export class CompanyResolverService implements Resolve<Ticket[]> {
  constructor(private companyService: CompanyService) {
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Ticket[] | import('rxjs').Observable<Ticket[]> | Promise<Ticket[]> {
    return this.companyService.fetchCompanyTickets(route.params.id);
  }
}
