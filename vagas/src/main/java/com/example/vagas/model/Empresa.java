package com.example.vagas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "empresa") 
@PrimaryKeyJoinColumn(name = "id") 
public class Empresa extends Usuario { 

    @Column(unique = true, nullable = false)
    private String cnpj;

    private String descricao;
    private String cidade;

    public Empresa() {
        super(); 
        this.setRole("ROLE_EMPRESA"); 
    }

    public Empresa(String email, String senha, String nome, String cnpj, String descricao, String cidade) {
        super(email, senha, nome, "ROLE_EMPRESA");
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.cidade = cidade;
    }

    // Getters e Setters da Empresa
    public String getCnpj() { 
        return cnpj; 
    }
    public void setCnpj(String cnpj) { 
        this.cnpj = cnpj; 
    }
    public String getDescricao() { 
        return descricao; 
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }
    public String getCidade() { 
        return cidade; 
    }
    public void setCidade(String cidade) { 
        this.cidade = cidade; 
    }

}