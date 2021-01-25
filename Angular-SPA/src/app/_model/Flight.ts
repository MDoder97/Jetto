import {City} from './City';
import {Ticket} from './Ticket';

export interface Flight {
  id: number;
  tickets: Ticket[];
  origin: City;
  destination: City;
}
