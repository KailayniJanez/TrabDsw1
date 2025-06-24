package com.example.vagas.repository;

import com.example.vagas.model.Empresa;
import com.example.vagas.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByDataLimiteInscricaoAfter(LocalDate hoje);
    List<Vaga> findByCidadeAndDataLimiteInscricaoAfter(String cidade, LocalDate hoje);
    List<Vaga> findByEmpresaOrderByDataLimiteInscricaoDesc(Empresa empresa);
    List<Vaga> findByEmpresaAndDataLimiteInscricaoAfter(Empresa empresa, LocalDate data);

    @Query("SELECT v FROM Vaga v WHERE v.dataLimiteInscricao >= CURRENT_DATE")
    List<Vaga> findAllAbertas();
}
