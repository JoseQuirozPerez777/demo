package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
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
@Document(collection = "puntos")
public class Punto {

    @MongoId(FieldType.OBJECT_ID)
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

    @Builder.Default
    private List<String> materiales = new ArrayList<>();

    @Builder.Default
    private List<Recompensa> recompensas = new ArrayList<>();

    @Field(value = "usuario_id", targetType = FieldType.OBJECT_ID)
    private String usuarioId;

    @Builder.Default
    private List<String> imagenes = new ArrayList<>();

    @Builder.Default
    private List<Red> redes = new ArrayList<>();

    public boolean esDeTipo(String tipoAComprobar) {
    if (this.tipo == null || tipoAComprobar == null) {
        return false;
    }
    return this.tipo.trim().equalsIgnoreCase(tipoAComprobar.trim());
}
}
