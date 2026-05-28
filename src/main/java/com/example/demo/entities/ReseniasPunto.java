package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "resenias")
public class ReseniasPunto {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(value = "punto_id", targetType = FieldType.OBJECT_ID)
    private String puntoId;

    @Builder.Default
    private List<Resenia> resenias = new ArrayList<>();
}