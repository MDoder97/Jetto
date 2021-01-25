package com.markododer.webproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDto {

    private String username;
    private String password;

}
