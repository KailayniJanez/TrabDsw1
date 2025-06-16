package com.example.vagas.repository;

import com.example.vagas.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByDataLimiteAfter(LocalDate hoje);
    List<Vaga> findByCidadeAndDataLimiteAfter(String cidade, LocalDate hoje);
}
