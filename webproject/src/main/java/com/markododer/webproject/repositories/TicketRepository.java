package com.markododer.webproject.repositories;


import com.markododer.webproject.model.Ticket;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
