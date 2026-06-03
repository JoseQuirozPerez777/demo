package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF ya que las APIs REST no lo necesitan (evita bloqueos en POST)
            .csrf(csrf -> csrf.disable())
            // Permitir que CUALQUIER petición a cualquier ruta entre sin autenticación
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Esto mantendrá vivo tu codificador de contraseñas para que el Service no falle
        return new BCryptPasswordEncoder();
    }
}