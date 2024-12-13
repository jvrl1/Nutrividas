package com.example.Nutrividas.repository;

import com.example.Nutrividas.model.Consulta;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByNutricionistaId(Long nutricionistaId);
    List<Consulta> findByUsuarioId(Long usuarioId);
    List<Consulta> findByDataBetween(LocalDate startDate, LocalDate endDate);
}

