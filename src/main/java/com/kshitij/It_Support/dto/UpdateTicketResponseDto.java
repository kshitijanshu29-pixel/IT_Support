package com.kshitij.It_Support.dto;
import java.time.LocalDateTime;
import com.kshitij.It_Support.entities.*;

public class UpdateTicketResponseDto {
    private String ticketNumber;
    
    
    private UserSummaryDto assignedto;
    private Long id;
    private Status status;
    private LocalDateTime updatedAt;
    private Priority priority;
    
    
    
    public UpdateTicketResponseDto(String ticketNumber , UserSummaryDto assignedto , Long id , Status status ,LocalDateTime updatedAt , Priority priority){
        this.ticketNumber = ticketNumber;
        this.assignedto = assignedto;
        this.id = id;
        
        this.status = status;
        this.updatedAt = updatedAt;
        this.priority =  priority;

}
    public String getTicketNumber() {
        return ticketNumber;
    }
    public UserSummaryDto getAssignedto() {
        return assignedto;
    }
    public Long getId() {
        return id;
    
    }
    public Status getStatus() {
        return status;
    }
   
   
    public void setStatus(Status status) {
        this.status = status;

    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public Priority gePriority(){
        return priority;
    }
}
