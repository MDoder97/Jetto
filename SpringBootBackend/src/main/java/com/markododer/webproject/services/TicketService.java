package com.markododer.webproject.services;

import com.markododer.webproject.model.Company;
import com.markododer.webproject.model.Flight;
import com.markododer.webproject.repositories.TicketRepository;
import com.markododer.webproject.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TicketService implements IService<Ticket, Long> {

    private final TicketRepository ticketRepository;
    private final FlightService flightService;
    private final CompanyService companyService;

    public TicketService(TicketRepository ticketRepository, FlightService flightService, CompanyService companyService) {
        this.ticketRepository = ticketRepository;
        this.flightService = flightService;
        this.companyService = companyService;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket addTicket(Ticket ticket) {
        if(isValid(ticket)) {
            ticketRepository.save(ticket);
            ticket.getFlight().getTickets().add(ticket);
            ticket.getCompany().getTickets().add(ticket);
            updateEntities(ticket.getFlight(), ticket.getCompany());
            return ticket;
        }
        return null;
    }

    public boolean deleteTicket(Long ticketId) {
        Ticket ticket = getTicket(ticketId);
        if(ticket != null) {
            ticket.getCompany().getTickets().remove(ticket);
            ticket.getFlight().getTickets().remove(ticket);

            updateEntities(ticket.getFlight(), ticket.getCompany());
            ticketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }

    private Ticket getTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if(ticketOptional.isPresent()) {
            return ticketOptional.get();
        }
        return null;
    }

    public Ticket updateTicket(Ticket ticketToUpdate) {
        Ticket ticket = getTicket(ticketToUpdate.getId());
        if(ticket != null && isValid(ticketToUpdate)) {
            if (ticket.getCompany() != ticketToUpdate.getCompany()) {
                ticket.getCompany().removeTicket(ticket);
                ticketToUpdate.getCompany().addTicket(ticketToUpdate);
            }
            if (ticket.getFlight() != ticketToUpdate.getFlight()) {
                ticket.getFlight().removeTicket(ticket);
                ticketToUpdate.getFlight().addTicket(ticketToUpdate);
            }
            ticketRepository.save(ticketToUpdate);
            updateEntities(ticket.getFlight(), ticket.getCompany());
            updateEntities(ticketToUpdate.getFlight(), ticketToUpdate.getCompany());
            return ticketToUpdate;
        }
        return null;
    }

    private void updateEntities(Flight flight, Company company) {
        companyService.save(company);
        flightService.save(flight);
    }

    private boolean isValid(Ticket ticket) {
        Optional<Company> companyOptional = companyService.findById(ticket.getCompany().getId());
        Optional<Flight> flightOptional = flightService.findById(ticket.getFlight().getId());
        if(companyOptional.isPresent() && flightOptional.isPresent()
                && ticket.getDepartDate().isBefore(ticket.getReturnDate())
                && ticket.getCount() > 0) {
            return true;
        }
        return false;
    }

}
