package com.example.vagas.repository;

import com.example.vagas.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CandidaturaRepository extends CrudRepository<Candidatura, Long> {
    
    @Query("SELECT c FROM Candidatura c WHERE c.profissional = :profissional ORDER BY c.dataCandidatura DESC")
    Iterable<Candidatura> findByProfissionalOrderByDataCandidaturaDesc(@Param("profissional") Profissional profissional);
    
    @Query("SELECT c FROM Candidatura c WHERE c.vaga = :vaga ORDER BY c.dataCandidatura DESC")
    Iterable<Candidatura> findByVagaOrderByDataCandidaturaDesc(@Param("vaga") Vaga vaga);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidatura c WHERE c.vaga = :vaga AND c.profissional = :profissional")
    boolean existsByVagaAndProfissional(@Param("vaga") Vaga vaga, @Param("profissional") Profissional profissional);
}