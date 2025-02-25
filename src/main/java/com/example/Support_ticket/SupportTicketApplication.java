package com.example.Support_ticket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(title = "IT Support Ticket API", version = "1.0", description = "API documentation for the IT Support Ticket System")
)

@SpringBootApplication
public class SupportTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupportTicketApplication.class, args);
	}

}
