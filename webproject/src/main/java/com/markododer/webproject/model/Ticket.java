package com.markododer.webproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;
    private boolean oneWay;
    private LocalDate departDate;
    private LocalDate returnDate;

    @ManyToOne
    private Flight flight;
    private long count;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();

    public void removeReservation(Reservation reservationToRemove) {
        reservations.remove(reservationToRemove);
    }
}
