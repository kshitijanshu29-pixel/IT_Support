package com.kshitij.It_Support.dto;
import java.time.LocalDateTime;



import com.kshitij.It_Support.entities.*;
public class TicketResponseDto {

    private String ticketNumber;
    private String title;
   
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserSummaryDto createdBy;
    private UserSummaryDto assignedTo;

    
 
    public TicketResponseDto(){}
    public TicketResponseDto( String ticketNumber, String title, UserSummaryDto createdBy,
        
     String description,
     Priority priority,
     Status status,
     LocalDateTime createdAt,
     LocalDateTime updatedAt,
     UserSummaryDto assignedTo){
        this.ticketNumber=ticketNumber;
        this.title=title;
        this.createdBy = createdBy;
        this.description=description;
        this.priority = priority;
        this.status=status;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.assignedTo=assignedTo;
     }
    public String getTicketNumber() {
        return ticketNumber;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Priority getPriority() {
        return priority;
    }
    public Status getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public UserSummaryDto getCreatedBy() {
        return createdBy;
    }
    
    public UserSummaryDto getAssignedTo() {
        return assignedTo;
    }
    
     
    
}
