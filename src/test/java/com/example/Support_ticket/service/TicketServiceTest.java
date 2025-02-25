package com.example.Support_ticket.service;
import com.example.Support_ticket.model.Ticket;
import com.example.Support_ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class TicketServiceTest {


    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTicket() {
        Ticket ticket = new Ticket();
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Testing the ticket creation.");

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticket);
        assertNotNull(createdTicket);
        assertEquals("Test Ticket", createdTicket.getTitle());
    }

}
