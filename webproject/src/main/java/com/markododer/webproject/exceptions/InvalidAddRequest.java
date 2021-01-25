package com.markododer.webproject.exceptions;

public class InvalidAddRequest extends RuntimeException {

    public InvalidAddRequest(String errorMessage) {
        super(errorMessage);
    }
}
