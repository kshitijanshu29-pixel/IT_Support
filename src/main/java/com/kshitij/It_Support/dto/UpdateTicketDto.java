package com.kshitij.It_Support.dto;

import com.kshitij.It_Support.entities.*;

public class UpdateTicketDto {
    private Status status;
    private Priority priority;
    
    
    public UpdateTicketDto(){}
    public UpdateTicketDto(Status status , Priority priority ){
        this.status= status;
        this.priority = priority;

    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    

    
}
