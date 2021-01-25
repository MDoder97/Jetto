package com.markododer.webproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
