package com.markododer.webproject.controller;


import com.markododer.webproject.exceptions.InvalidAddRequest;
import com.markododer.webproject.exceptions.InvalidDeleteRequest;
import com.markododer.webproject.model.MyUser;
import com.markododer.webproject.model.Reservation;
import com.markododer.webproject.model.Ticket;
import com.markododer.webproject.services.ReservationService;
import com.markododer.webproject.services.TicketService;
import com.markododer.webproject.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;
    private final ReservationService reservationService;

    public UserController(UserService userService, TicketService ticketService, ReservationService reservationService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.reservationService = reservationService;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MyUser> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public List<Reservation> getReservations(@PathVariable("userId") Long userId) throws Exception {
        List<Reservation> reservations = reservationService.getReservations(userId);
        if(reservations != null)
            return reservations;
        throw new Exception("Failed to get reservations, Please reload the page");
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addReservation(@PathVariable("userId") Long userId, @RequestBody Long ticketId) {
        MyUser user = reservationService.addReservation(userId, ticketId);
        if(user != null) {
            return ResponseEntity.ok().build();
        }
        throw new InvalidAddRequest("Reservation failed. Please check number of tickets left");
}

    @PostMapping("/delete/{userId}")
    public ResponseEntity<?> deleteReservation(@PathVariable("userId") Long userId, @RequestBody Long reservationId) {
        MyUser user = reservationService.deleteReservation(userId, reservationId);
        if(user != null) {
            return ResponseEntity.ok().build();
        }
        throw new InvalidDeleteRequest("Reservation not found");
    }

}
