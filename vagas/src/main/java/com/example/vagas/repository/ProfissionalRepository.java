package com.example.vagas.repository;

import com.example.vagas.model.Profissional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ProfissionalRepository extends CrudRepository<Profissional, Long> {
    
    @Query("SELECT p FROM Profissional p WHERE p.email = :email")
    Optional<Profissional> findByEmail(@Param("email") String email);
}