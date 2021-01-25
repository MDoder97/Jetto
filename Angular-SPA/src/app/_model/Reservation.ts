import {Ticket} from './Ticket';

export interface Reservation {
  id: number;
  available: boolean;
  ticket: Ticket;
}
