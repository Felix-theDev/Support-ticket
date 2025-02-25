package com.example.Support_ticket.repository;

import com.example.Support_ticket.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTicketId(Long ticketId);
}