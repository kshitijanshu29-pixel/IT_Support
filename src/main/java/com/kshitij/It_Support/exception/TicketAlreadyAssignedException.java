package com.kshitij.It_Support.exception;

public class TicketAlreadyAssignedException extends RuntimeException {
    public TicketAlreadyAssignedException(String message){
        super(message);
    }

    
}
