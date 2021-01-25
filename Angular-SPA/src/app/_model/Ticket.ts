import {Company} from './Company';
import {Flight} from './Flight';

export interface Ticket {
  id: number;
  company: Company;
  oneWay: boolean;
  departDate: string;
  returnDate: string;
  flight: Flight;
  count: number;
}
