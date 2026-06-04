package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Punto;

@Repository
public interface PuntoRepository extends MongoRepository<Punto, String> {
    @Query("{ 'lng' : { $exists: true }, 'lat' : { $exists: true } }") // Filtrado manual en servicio o indexado si usas GeoSpatial
    List<Punto> findAll();

    List<Punto> findByTipo(String tipo);

    List<Punto> findByTipoIgnoreCase(String tipo);
    
    List<Punto> findByUsuarioId(String usuarioId);

}