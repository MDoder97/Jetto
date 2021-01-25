package com.markododer.webproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = UserExistsException.class )
    public ResponseEntity<Object> handleException(UserExistsException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FetchFailedException.class )
    public ResponseEntity<Object> handleException(FetchFailedException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidAddRequest.class )
    public ResponseEntity<Object> handleException(InvalidAddRequest ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidUpdateRequest.class )
    public ResponseEntity<Object> handleException(InvalidUpdateRequest ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDeleteRequest.class )
    public ResponseEntity<Object> handleException(InvalidDeleteRequest ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
