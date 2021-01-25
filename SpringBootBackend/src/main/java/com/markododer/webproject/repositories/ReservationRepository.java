package com.markododer.webproject.repositories;

import com.markododer.webproject.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByTicketId(Long ticketId);
}
