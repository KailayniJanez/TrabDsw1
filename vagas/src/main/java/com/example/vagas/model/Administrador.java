package com.example.vagas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "administrador")
@PrimaryKeyJoinColumn(name = "id")
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String email, String senha, String nome) {
        super(email, senha, nome, "ROLE_ADMIN"); // Define a role
    }

}