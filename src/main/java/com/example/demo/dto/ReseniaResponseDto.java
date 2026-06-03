package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReseniaResponseDto {

    private String usuarioId;
    private Integer puntaje;
    private String comentario;
    private LocalDateTime fechaCreacion; // Nuevo campo expuesto
}