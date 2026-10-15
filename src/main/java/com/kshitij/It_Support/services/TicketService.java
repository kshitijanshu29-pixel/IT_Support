package com.kshitij.It_Support.services;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kshitij.It_Support.entities.*;
import com.kshitij.It_Support.repository.*;

import org.springframework.transaction.annotation.Transactional;

import com.kshitij.It_Support.dto.*;
import com.kshitij.It_Support.exception.*;

import java.util.List;


@Service 
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    public TicketService(TicketRepository ticketRepository , UserRepository userRepository){
        this.ticketRepository=ticketRepository;
        this.userRepository=userRepository;
    }
    @Transactional 
    public TicketResponseDto createTicket(CreateTicketDto request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("user not found"));


        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(Status.OPEN);
        ticket.setPriority(Priority.LOW);
        ticket.setCreatedBy(user);
        ticket.setAssignedTo(null);
       
        
        Ticket saved = ticketRepository.save(ticket);

        String ticketNumber = "TKT - XQWERT - " + saved.getId();
        saved.setTicketNumber(ticketNumber);
        saved = ticketRepository.save(saved);
        
        return mapToDto(saved);


    }
    public TicketResponseDto mapToDto(Ticket saved) {

    User createdByUser = saved.getCreatedBy();

    UserSummaryDto createdBy = new UserSummaryDto(
            createdByUser.getId(),
            createdByUser.getName()
    );

    User assignedToUser = saved.getAssignedTo();

    UserSummaryDto assignedTo = assignedToUser == null
            ? null
            : new UserSummaryDto(
                    assignedToUser.getId(),
                    assignedToUser.getName()
            );

    return new TicketResponseDto(saved.getTicketNumber(),saved.getTitle(),createdBy,saved.getDescription(),             saved. getPriority(),
            saved.getStatus(),
            saved.getCreatedAt(),
            saved.getUpdatedAt(),
            assignedTo
    );
}

    
@Transactional(readOnly = true)  
public  TicketResponseDto getByTicketNumber(String ticketNumber){
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
        .orElseThrow(() -> new TicketNotFoundException("no ticket found"));
        return mapToDto(ticket);
    }
@Transactional 
public TicketResponseDto updateTicket(String ticketNumber,Status status,Priority priority) {

    Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
            .orElseThrow(() ->
                    new TicketNotFoundException("Ticket not found: " + ticketNumber));

    ticket.setStatus(status);
    ticket.setPriority(priority);   

    Ticket saved = ticketRepository.save(ticket);
    return mapToDto(saved);
}
@Transactional 
    public UpdateTicketResponseDto acceptTicket(String ticketNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
        .orElseThrow(()-> new UserNotFoundException("User not found"));
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
        .orElseThrow(() -> new TicketNotFoundException("ticket doesnot exist"));
        if (ticket.getAssignedTo() != null) {
            throw new TicketAlreadyAssignedException("Ticket already under consideration");
        }
        ticket.setAssignedTo(user);
        

        Ticket saved = ticketRepository.save(ticket);
        User assignedTo = saved.getAssignedTo();
        UserSummaryDto assignedToSummary = new UserSummaryDto(assignedTo.getId(), assignedTo.getName());
        return new UpdateTicketResponseDto(saved.getTicketNumber(), assignedToSummary, saved.getId(), saved.getStatus(), saved.getUpdatedAt(),saved.getPriority());
    }
     @Transactional(readOnly = true)
    public List<TicketResponseDto> getMyTickets(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
        .orElseThrow(()-> new UserNotFoundException("User not found"));

        List<Ticket> ticket = ticketRepository.findByCreatedBy(user);
        return ticket.stream()
            .map(this::mapToDto)
            .toList();
        

    }
   
}
