package com.markododer.webproject.services;

import com.markododer.webproject.model.MyUser;
import com.markododer.webproject.model.Ticket;
import com.markododer.webproject.repositories.ReservationRepository;
import com.markododer.webproject.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IService<Reservation, Long> {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, UserService userService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public MyUser addReservation(Long userId, Long ticketId) {
        MyUser user = getUser(userId);
        Optional<Ticket> ticketOptional = ticketService.findById(ticketId);
        Ticket ticket;
        if(user != null && ticketOptional.isPresent() && (ticket = ticketOptional.get()).getCount() > 0) {
            ticket.setCount(ticket.getCount() - 1);
            Reservation reservation = createReservation(ticket);
            reservation.setUser(user);
            user.addReservation(reservation);

            userService.save(user);
            ticketService.save(ticket);
            return user;
        }
        return null;
    }

    private MyUser getUser(Long userId) {
        Optional<MyUser> userOptional = userService.findById(userId);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    private Reservation createReservation(Ticket ticket) {
        Reservation reservation = new Reservation();
        reservation.setAvailable(true);
        reservation.setTicket(ticket);
        return reservation;
    }

    public List<Reservation> getReservations(Long userId) {
        MyUser user = getUser(userId);
        if(user != null) {
            return user.getReservations();
        }
        return null;
    }

    public MyUser deleteReservation(Long userId, Long reservationId) {
        MyUser user = getUser(userId);
        if(user != null) {
            Reservation reservationToRemove = null;
            for(Reservation reservation : user.getReservations()) {
                if(reservation.getId() == reservationId) {
                    reservationToRemove = reservation;
                    Ticket ticket = reservationToRemove.getTicket();
                    ticket.setCount(ticket.getCount() + 1);
                    ticket.removeReservation(reservationToRemove);
                    user.removeReservation(reservationToRemove);
                    reservationRepository.deleteById(reservationToRemove.getId());
                    userService.save(user);
                    return user;
                }
            }
        }
        return null;
    }
}
