package com.markododer.webproject.exceptions;

public class InvalidDeleteRequest extends RuntimeException{

    public InvalidDeleteRequest(String errorMessage) {
        super(errorMessage);
    }
}
