package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private String id;
    private String nombre;
    private String correo;
    private String rol;
    private String foto;
}