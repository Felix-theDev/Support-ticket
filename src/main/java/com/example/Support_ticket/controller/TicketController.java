package com.example.Support_ticket.controller;

import com.example.Support_ticket.model.AuditLog;
import com.example.Support_ticket.model.Status;
import com.example.Support_ticket.model.Ticket;
import com.example.Support_ticket.repository.AuditLogRepository;
import com.example.Support_ticket.repository.TicketRepository;
import com.example.Support_ticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Endpoints for managing support tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final AuditLogRepository auditLogRepository;

    public TicketController(TicketService ticketService, TicketRepository ticketRepository, AuditLogRepository auditLogRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.auditLogRepository = auditLogRepository;
    }

    // ✅ Create a new ticket (Employees & IT Support can create tickets)
    @PostMapping
    @Operation(summary = "Create a new support ticket", description = "Allows employees to create a ticket.")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // ✅ Get all tickets (Only IT Support can see all)
    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieves all tickets. Only IT Support can access this.")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // ✅ Get a single ticket by ID (Employees can see their own, IT Support can see all)
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        // Ensure Employees can only view their own tickets
        if (!userDetails.getAuthorities().toString().contains("IT_SUPPORT") &&
                !ticket.getCreatedBy().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(ticket);
    }

    // ✅ Update ticket status (Only IT Support can update)
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (!userDetails.getAuthorities().toString().contains("IT_SUPPORT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only IT Support can change status.");
        }

        try {
            Status newStatus = Status.valueOf(request.get("status").toUpperCase()); // Convert to enum safely
            ticket.setStatus(newStatus);
            ticketRepository.save(ticket);

            // Log status change
            AuditLog log = new AuditLog();
            log.setTicket(ticket);
            log.setAction("STATUS_CHANGED");
            log.setPerformedBy(userDetails.getUsername());
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);

            return ResponseEntity.ok("Ticket status updated to " + newStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value.");
        }
    }

    // ✅ Add a comment to a ticket (Only IT Support can comment)
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (!userDetails.getAuthorities().toString().contains("IT_SUPPORT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only IT Support can add comments.");
        }

        String comment = request.get("comment");
//        ticket.getComments().add(comment);
        ticketRepository.save(ticket);

        // Log comment addition
        AuditLog log = new AuditLog();
        log.setTicket(ticket);
        log.setAction("COMMENT_ADDED");
        log.setPerformedBy(userDetails.getUsername());
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);

        return ResponseEntity.ok("Comment added successfully.");
    }

    // ✅ Get audit logs for a ticket
    @GetMapping("/{id}/audit-log")
    public ResponseEntity<List<AuditLog>> getAuditLog(@PathVariable Long id) {
        List<AuditLog> logs = auditLogRepository.findByTicketId(id);
        return ResponseEntity.ok(logs);
    }
}
