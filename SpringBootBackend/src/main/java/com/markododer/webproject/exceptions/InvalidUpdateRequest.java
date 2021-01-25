package com.markododer.webproject.exceptions;

public class InvalidUpdateRequest extends RuntimeException {

    public InvalidUpdateRequest(String errorMessage) {
        super(errorMessage);
    }
}
