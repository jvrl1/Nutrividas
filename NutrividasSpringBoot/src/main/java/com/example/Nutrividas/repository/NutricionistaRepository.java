package com.example.Nutrividas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Nutrividas.model.Nutricionista;

import java.util.Optional;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista,Long> {
    @Override
    Optional<Nutricionista> findById(Long id);
}
