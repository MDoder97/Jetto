package com.markododer.webproject.dto;


import com.markododer.webproject.model.Reservation;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private boolean admin;
    private List<Reservation> reservations;
    private String jwt;
}
