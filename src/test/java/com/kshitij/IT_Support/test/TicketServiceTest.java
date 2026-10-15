package com.kshitij.IT_Support.test;

import com.kshitij.It_Support.dto.CreateTicketDto;
import com.kshitij.It_Support.dto.TicketResponseDto;
import com.kshitij.It_Support.dto.UpdateTicketDto;
import com.kshitij.It_Support.dto.UpdateTicketResponseDto;

import com.kshitij.It_Support.entities.*;
import com.kshitij.It_Support.exception.TicketNotFoundException;
import com.kshitij.It_Support.exception.UserNotFoundException;
import com.kshitij.It_Support.repository.TicketRepository;
import com.kshitij.It_Support.repository.UserRepository;
import com.kshitij.It_Support.services.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 import java.util.Optional;
 import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock 
    private UserRepository userRepository;
    @InjectMocks 
    private TicketService ticketService;

    
@Test
void createTicket_ShouldReturnTicket() {

    User user = new User();
    user.setName("kshitij");
    user.setEmail("abc@gmail.com");
    user.setId(1L);
    user.setRole(Role.CUSTOMER);
    user.setCreatedAt(null);

    CreateTicketDto request = new CreateTicketDto();
    
    request.setTitle("backend issue");
    request.setDescription("qwertyuiop");

    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);

    when(authentication.getName())
            .thenReturn("abc@gmail.com");

    when(securityContext.getAuthentication())
            .thenReturn(authentication);

    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail("abc@gmail.com"))
            .thenReturn(Optional.of(user));

    // Simulate database generating ID = 1
    when(ticketRepository.save(any(Ticket.class)))
            .thenAnswer(invocation -> {
                Ticket ticket = invocation.getArgument(0);

                if (ticket.getId() == null) {
                    ticket.setId(1L);
                }

                return ticket;
            });

    // Call service
    TicketResponseDto response =
            ticketService.createTicket(request);

    // Response assertions
    assertEquals("backend issue", response.getTitle());
    assertEquals("qwertyuiop", response.getDescription());
    assertEquals(Status.OPEN, response.getStatus());
    assertEquals(Priority.LOW, response.getPriority());

    assertNull(response.getCreatedAt());

    // Created by
    assertEquals(1L, response.getCreatedBy().getId());
    assertEquals("kshitij", response.getCreatedBy().getName());

    // New ticket should be unassigned
    assertNull(response.getAssignedTo());

    // Ticket number
    assertEquals("TKT - XQWERT - 1",
            response.getTicketNumber());

    // Verify save was called twice
    ArgumentCaptor<Ticket> captor =
            ArgumentCaptor.forClass(Ticket.class);

    verify(ticketRepository, times(2))
            .save(captor.capture());

    List<Ticket> savedTickets =
            captor.getAllValues();

    // First save
    Ticket firstSave = savedTickets.get(0);

    assertEquals(1L, firstSave.getId());
    assertEquals("abc@gmail.com",
            firstSave.getCreatedBy().getEmail());

    // Second/final save
    Ticket finalSave = savedTickets.get(1);

    assertEquals(1L, finalSave.getId());

    assertEquals("TKT - XQWERT - 1",
            finalSave.getTicketNumber());

    assertEquals("abc@gmail.com",
            finalSave.getCreatedBy().getEmail());

    assertNull(finalSave.getAssignedTo());
}

    @Test
    void createTicket_ShouldThrowException_WhenUserNotFound(){  
        CreateTicketDto request = new CreateTicketDto();
        
        request.setTitle("backend issue");
        request.setDescription("qwertyuiop");

        User user = new User();
        user.setEmail("abc@gmail.com");
        when(userRepository.findByEmail("abc@gmail.com"))
        .thenReturn(Optional.empty());

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(authentication.getName()).thenReturn("abc@gmail.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(UserNotFoundException.class,
          () -> ticketService.createTicket(request));
    }
    @Test
    void getTicketByTicketNumber_ShouldReturnTicket(){
        User user = new User();
        user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        
        Ticket ticket = new Ticket();
        ticket.setId(1l);
        
        ticket.setTicketNumber("1E001");
        ticket.setAssignedTo(null);
        ticket.setCreatedAt(null);
        ticket.setDescription("qwerty");
        ticket.setPriority(Priority.HIGH);
        ticket.setStatus(Status.CLOSED);
        ticket.setCreatedBy(user);
        
        when(ticketRepository.findByTicketNumber("1E001"))
        .thenReturn(Optional.of(ticket));
        TicketResponseDto response = ticketService.getByTicketNumber("1E001");
        assertEquals("1E001", response.getTicketNumber());

    }
    @Test
    void getTicketByTicketNumber_ShouldThrowException_WhenTicketIsNotFound(){
        Ticket ticket = new Ticket();
        ticket.setTicketNumber("1E001");
        when(ticketRepository.findByTicketNumber("1E001"))
        .thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.getByTicketNumber("1E001"));
    }
    @Test
    void getAllTicketsByEmail(){
        User user = new User();
        user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        Ticket ticket = new Ticket();
        ticket.setId(1l);
        
        ticket.setTicketNumber("1E001");
        ticket.setAssignedTo(null);
        ticket.setCreatedAt(null);
        ticket.setDescription("qwerty");
        ticket.setPriority(Priority.HIGH);
        ticket.setStatus(Status.CLOSED);
        ticket.setCreatedBy(user);
        ticket.setTitle("backend issue");
        ticket.setUpdatedAt(null);

        Ticket ticket2 = new Ticket();
        ticket2.setId(2l);
        
        ticket2.setTicketNumber("2E002");
        ticket2.setAssignedTo(null);
        ticket2.setCreatedAt(null);
        ticket2.setDescription("poiuy");
        ticket2.setPriority(Priority.HIGH);
        ticket2.setStatus(Status.CLOSED);
        ticket2.setCreatedBy(user);
        ticket2.setUpdatedAt(null);
        ticket2.setTitle("frontend issue");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
            when(authentication.getName())
            .thenReturn("abc@gmail.com");
            when(securityContext.getAuthentication())
            .thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            when(userRepository.findByEmail("abc@gmail.com"))
            .thenReturn(Optional.of(user));
            when(ticketRepository.findByCreatedBy(user))
            .thenReturn(List.of(ticket,ticket2));
            List<TicketResponseDto> reponse = ticketService.getMyTickets();
            assertEquals(2, reponse.size());
                assertEquals("1E001", reponse.get(0).getTicketNumber());
                assertEquals("2E002", reponse.get(1).getTicketNumber());



    }
    @Test 
    void updateTicket_ShouldReturnUpdatedTicket(){


        User user = new User();
user.setId(1L);
user.setName("kshitij");
user.setEmail("abc@gmail.com");



        Ticket ticket = new Ticket();
        ticket.setId(1l);
        
        ticket.setTicketNumber("1E001");
        ticket.setTitle("backend issue");
        ticket.setDescription("qwertyuyu");
        ticket.setPriority(Priority.LOW);
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedAt(null);
        ticket.setCreatedBy(user);
        ticket.setAssignedTo(null);

        Ticket updatedtTicket = new Ticket();
        updatedtTicket.setId(1l);
        
        updatedtTicket.setTicketNumber("1E001");
        updatedtTicket.setTitle("backend issue");
        updatedtTicket.setDescription("qwertyuyu");
        updatedtTicket.setPriority(Priority.HIGH);
        updatedtTicket.setStatus(Status.IN_PROGRESS);
        updatedtTicket.setCreatedAt(null);
        updatedtTicket.setCreatedBy(user);
        updatedtTicket.setAssignedTo(null);


        UpdateTicketDto request = new UpdateTicketDto();
        request.setPriority(Priority.HIGH);
        request.setStatus(Status.IN_PROGRESS);

        when(ticketRepository.findByTicketNumber("1E001"))
        .thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class)))
        .thenReturn(updatedtTicket);
        TicketResponseDto result = ticketService.updateTicket("1E001", Status.IN_PROGRESS, Priority.HIGH);
        assertEquals("1E001", result.getTicketNumber());
        assertEquals(Priority.HIGH,result.getPriority());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
    }

    @Test
     void updateTicket_AcceptTicket_ShouldReturnUpdatedTicket(){
        
         User user = new User();
         user.setId(1L);
         user.setName("kshitij");
         user.setEmail("abc@gmail.com");


        Ticket ticket = new Ticket();
        ticket.setId(1l);
        
        ticket.setTicketNumber("1E001");
        ticket.setTitle("backend issue");
        ticket.setDescription("qwertyuyu");
        ticket.setPriority(Priority.LOW);
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedAt(null);
        ticket.setCreatedBy(user);
        ticket.setAssignedTo(null);
        ticket.setUpdatedAt(null);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getName())
        .thenReturn("abc@gmail.com");
        when(securityContext.getAuthentication())
        .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(ticketRepository.findByTicketNumber("1E001"))
        .thenReturn(Optional.of(ticket));
         when(userRepository.findByEmail("abc@gmail.com"))
        .thenReturn(Optional.of(user));
        when(ticketRepository.save(any(Ticket.class)))
        .thenReturn(ticket);

        UpdateTicketResponseDto result = ticketService.acceptTicket("1E001");
        assertEquals("1E001", result.getTicketNumber());
        
        assertEquals(1l, result.getId());
        assertEquals(user, result.getAssignedto());
        assertEquals(Status.OPEN, result.getStatus());
        assertEquals(null, result.getUpdatedAt());




    }


    
}
