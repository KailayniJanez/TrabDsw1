package com.example.vagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vagas.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
    boolean existsByEmail(String email);
}