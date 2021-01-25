import {Reservation} from './Reservation';

export interface User {
  id: number;
  username: string;
  admin: boolean;
  bookings: Reservation[];
}
