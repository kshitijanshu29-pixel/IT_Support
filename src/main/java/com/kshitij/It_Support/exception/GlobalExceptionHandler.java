package com.kshitij.It_Support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity <String>handleUserAlreadyExistException(UserAlreadyExistException ex){
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
    }
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<String> handleTicketNotFoundException(TicketNotFoundException ex){
    return ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(ex.getMessage());
}


}
