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
public class PuntoRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
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
    private List<String> redes;
}