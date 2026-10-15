package com.kshitij.It_Support.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kshitij.It_Support.services.TicketService;
import com.kshitij.It_Support.dto.*;


import jakarta.validation.Valid;
import java.util.List;

@RestController 
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService){
    this.ticketService = ticketService;
    
}
@PostMapping("/")
public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody CreateTicketDto request){
    TicketResponseDto response = ticketService.createTicket(request);
    return ResponseEntity
    .status(HttpStatus.CREATED)
    .body(response);
}
@GetMapping("/{ticketNumber}")
public ResponseEntity<TicketResponseDto> getTicketByTicketNUmber(@PathVariable String ticketNumber){
    TicketResponseDto response = ticketService.getByTicketNumber(ticketNumber);
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(response);
}
@GetMapping("/getmytickets")
public ResponseEntity<List<TicketResponseDto>> getAllTickets(){
    List<TicketResponseDto> response = ticketService.getMyTickets();
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(response);
}
@PutMapping ("/acceptticket/{ticketNumber}")
public ResponseEntity<UpdateTicketResponseDto> acceptTicket(
        @PathVariable String ticketNumber) {
    
    UpdateTicketResponseDto response = ticketService.acceptTicket(ticketNumber);
    return ResponseEntity.ok(response);
}
@PutMapping ("/updateticket/{ticketNumber}")
public ResponseEntity<TicketResponseDto> updated( @PathVariable String ticketNumber,@Valid @RequestBody UpdateTicketDto request) {
    TicketResponseDto response = ticketService.updateTicket(
            ticketNumber, request.getStatus(), request.getPriority());
    return ResponseEntity
    .status(HttpStatus.OK)
    .body(response);
}
}