package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReseniasPuntoRequestDto {

    @NotBlank(message = "El puntoId es obligatorio")
    private String puntoId;

    private List<ReseniaRequestDto> resenias;
}