package com.example.demo.repositories;

import com.example.demo.entities.ReseniasPunto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReseniasPuntoRepository extends MongoRepository<ReseniasPunto, String> {

    Optional<ReseniasPunto> findByPuntoId(String puntoId);
}