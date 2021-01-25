package com.markododer.webproject.exceptions;

public class FetchFailedException extends RuntimeException {
    public FetchFailedException(String errorMessage) {
        super(errorMessage);
    }
}
