package com.markododer.webproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean available;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private MyUser user;

}

