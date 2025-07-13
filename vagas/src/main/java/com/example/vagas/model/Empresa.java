package com.example.vagas.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
  
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

    

    public Empresa(String email, String senha, String nome, String cnpj, String descricao, String cidade) {
        super(email, senha, nome, "ROLE_EMPRESA");
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.cidade = cidade;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_EMPRESA"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
