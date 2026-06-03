package com.example.demo.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    public enum Rol {
        ADMIN,
        USER
    }

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String nombre;

    private String correo;

    private String password;

    @Builder.Default
    private Rol rol = Rol.USER;
}