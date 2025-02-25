package com.example.Support_ticket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "IT Support Ticket API", version = "1.0", description = "API documentation for the IT Support Ticket System")
)
public class SwaggerConfig {
}
