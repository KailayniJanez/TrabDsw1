package com.example.vagas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Pattern;  
import jakarta.validation.constraints.Size;    

@Entity
@Table(name = "empresa") 
@PrimaryKeyJoinColumn(name = "id") 
public class Empresa extends Usuario { 

    @NotNull(message = "{empresa.cnpj.notnull}") // Mensagem de erro para CNPJ nulo
    @Size(min = 14, max = 14, message = "{empresa.cnpj.size}") // Mensagem de erro para tamanho incorreto
    @Pattern(regexp = "\\d{14}", message = "{empresa.cnpj.pattern}") 
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

    public String getCnpjFormatado() {
        if (this.cnpj == null || this.cnpj.length() != 14) {
            return this.cnpj; 
        }
        return this.cnpj.substring(0, 2) + "." +
               this.cnpj.substring(2, 5) + "." +
               this.cnpj.substring(5, 8) + "/" +
               this.cnpj.substring(8, 12) + "-" +
               this.cnpj.substring(12);
    }
}