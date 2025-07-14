package com.example.vagas.repository;

import com.example.vagas.model.Empresa;
import com.example.vagas.model.Vaga;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface VagaRepository extends CrudRepository<Vaga, Long> {
    
    @Query("SELECT v FROM Vaga v WHERE v.dataLimiteInscricao >= :data")
    Iterable<Vaga> findByDataLimiteInscricaoGreaterThanEqual(@Param("data") LocalDate data);
    
    @Query("SELECT v FROM Vaga v WHERE v.empresa.cidade = :cidade AND v.dataLimiteInscricao >= :data")
    Iterable<Vaga> findByEmpresaCidadeAndDataLimiteInscricaoGreaterThanEqual(
        @Param("cidade") String cidade, 
        @Param("data") LocalDate data
    );
    
    @Query("SELECT v FROM Vaga v WHERE v.empresa = :empresa ORDER BY v.dataLimiteInscricao DESC")
    Iterable<Vaga> findByEmpresaOrderByDataLimiteInscricaoDesc(@Param("empresa") Empresa empresa);
    
    @Query("SELECT v FROM Vaga v WHERE v.empresa = :empresa AND v.dataLimiteInscricao >= :data")
    Iterable<Vaga> findByEmpresaAndDataLimiteInscricaoGreaterThanEqual(
        @Param("empresa") Empresa empresa, 
        @Param("data") LocalDate data
    );
    
    @Query("SELECT v FROM Vaga v WHERE v.dataLimiteInscricao >= CURRENT_DATE")
    Iterable<Vaga> findAllAbertas();

    @Query("SELECT v FROM Vaga v WHERE v.empresa.id = :empresaId AND v.dataLimiteInscricao >= CURRENT_DATE")
    List<Vaga> findByEmpresaIdAndDataLimiteInscricaoGreaterThanEqual(@Param("empresaId") Long empresaId, LocalDate data);
}
