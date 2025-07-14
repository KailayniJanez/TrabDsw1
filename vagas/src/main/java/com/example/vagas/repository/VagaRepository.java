package com.example.vagas.repository;

import com.example.vagas.model.Empresa;
import com.example.vagas.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
   List<Vaga> findByDataLimiteInscricaoGreaterThanEqual(LocalDate data);
    
    List<Vaga> findByEmpresaCidadeAndDataLimiteInscricaoGreaterThanEqual(
        String cidade, 
        LocalDate data
    );
    List<Vaga> findByEmpresaOrderByDataLimiteInscricaoDesc(Empresa empresa);
    List<Vaga> findByEmpresaAndDataLimiteInscricaoGreaterThanEqual(
        Empresa empresa, 
        LocalDate data
    );

    @Query("SELECT v FROM Vaga v WHERE v.dataLimiteInscricao >= CURRENT_DATE")
    List<Vaga> findAllAbertas();
}
