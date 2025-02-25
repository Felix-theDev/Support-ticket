package com.example.Support_ticket.repository;

import com.example.Support_ticket.model.Status;
import com.example.Support_ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(Status status);
}
