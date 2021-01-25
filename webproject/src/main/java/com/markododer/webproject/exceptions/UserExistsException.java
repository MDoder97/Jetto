package com.markododer.webproject.exceptions;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String errorMessage) {
        super(errorMessage);
    }
}
