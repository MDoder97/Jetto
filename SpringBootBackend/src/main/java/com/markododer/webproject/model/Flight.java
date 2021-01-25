package com.markododer.webproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne
    private City origin;
    @ManyToOne
    private City destination;

    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
