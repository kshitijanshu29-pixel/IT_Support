package com.kshitij.It_Support.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.It_Support.entities.Ticket;
import com.kshitij.It_Support.entities.User;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    boolean existsByticketNumber(String ticketNumber);
    List<Ticket> findByCreatedBy(User user);
}
