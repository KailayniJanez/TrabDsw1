package com.example.vagas.repository;

import com.example.vagas.model.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    
    @Query("SELECT e FROM Empresa e WHERE e.email = :email")
    Optional<Empresa> findByEmail(@Param("email") String email);
    
    @Query("SELECT e FROM Empresa e WHERE e.cidade = :cidade")
    List<Empresa> findByCidade(@Param("cidade") String cidade);
}