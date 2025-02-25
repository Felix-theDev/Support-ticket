package com.example.Support_ticket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // 🔹 Allow H2 console
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()

                        .requestMatchers("/api/auth/**").permitAll() // Allow login & registration
                        .requestMatchers(HttpMethod.POST, "/api/tickets").hasAnyAuthority("EMPLOYEE", "IT_SUPPORT")
                        .requestMatchers(HttpMethod.GET, "/api/tickets").hasAuthority("IT_SUPPORT")
                        .requestMatchers(HttpMethod.GET, "/api/tickets/{id}").hasAnyAuthority("EMPLOYEE", "IT_SUPPORT")
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/{id}/status").hasAuthority("IT_SUPPORT")
                        .requestMatchers(HttpMethod.POST, "/api/tickets/{id}/comments").hasAuthority("IT_SUPPORT")
                        .requestMatchers(HttpMethod.GET, "/api/tickets/{id}/audit-log").hasAuthority("IT_SUPPORT")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
