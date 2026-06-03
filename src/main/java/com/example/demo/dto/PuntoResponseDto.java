package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoResponseDto {

    private String id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String direccion;

    private Double lat;
    private Double lng;

    private String horario;
    private String telefono;
    private String whatsapp;

    private List<String> materiales;
    private List<RecompensaDto> recompensas;

    private String usuarioId;

    private List<String> imagenes;

    private List<RedDto> redes;
}