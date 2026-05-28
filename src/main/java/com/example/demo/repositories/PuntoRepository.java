package com.example.demo.repositories;

import com.example.demo.entities.Punto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntoRepository extends MongoRepository<Punto, String> {

    List<Punto> findByTipo(String tipo);

    List<Punto> findByUsuarioId(String usuarioId);
}