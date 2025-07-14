package com.example.vagas.repository;

import com.example.vagas.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByProfissionalOrderByDataCandidaturaDesc(Profissional profissional);
    List<Candidatura> findByVagaOrderByDataCandidaturaDesc(Vaga vaga);
    boolean existsByVagaAndProfissional(Vaga vaga, Profissional profissional);
}