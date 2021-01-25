package com.markododer.webproject.dto;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String username;
    private String password;
    private boolean admin;
}
