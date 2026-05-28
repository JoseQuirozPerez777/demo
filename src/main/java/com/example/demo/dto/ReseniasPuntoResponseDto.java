package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReseniasPuntoResponseDto {

    private String id;
    private String puntoId;
    private List<ReseniaResponseDto> resenias;
}