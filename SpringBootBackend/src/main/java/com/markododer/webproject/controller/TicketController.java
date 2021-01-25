package com.markododer.webproject.controller;


import com.markododer.webproject.exceptions.InvalidAddRequest;
import com.markododer.webproject.exceptions.InvalidDeleteRequest;
import com.markododer.webproject.exceptions.InvalidUpdateRequest;
import com.markododer.webproject.model.Company;
import com.markododer.webproject.model.Flight;
import com.markododer.webproject.model.Ticket;
import com.markododer.webproject.services.CompanyService;
import com.markododer.webproject.services.FlightService;
import com.markododer.webproject.services.TicketService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final CompanyService companyService;

    public TicketController(TicketService ticketService, FlightService flightService, CompanyService companyService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.companyService = companyService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket) {
        Ticket addedTicket = ticketService.addTicket(ticket);
        if(addedTicket != null) {
            return ResponseEntity.ok().body(addedTicket);
        }
        throw new InvalidAddRequest("Please check ticket parameters");
    }

    @GetMapping
    public List<Ticket> getTickets() {
        return ticketService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable("ticketId") Long ticketId) {
        boolean deleted = ticketService.deleteTicket(ticketId);
        if(deleted) {
            return ResponseEntity.ok().build();
        }
        throw new InvalidDeleteRequest("Failed to delete ticket");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateTicket(@RequestBody Ticket ticketToUpdate) {
        Ticket updatedTicket = ticketService.updateTicket(ticketToUpdate);
        if(updatedTicket != null) {
            return ResponseEntity.ok(updatedTicket);
        }
        throw new InvalidUpdateRequest("Please check ticket parameters");
    }

}
