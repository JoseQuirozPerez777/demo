package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resenia {

    @Field(value = "usuario_id", targetType = FieldType.OBJECT_ID)
    private String usuarioId;

    private Integer puntaje;

    private String comentario;
}
