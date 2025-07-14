package com.example.vagas.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "profissional") 
@PrimaryKeyJoinColumn(name = "id") 
public class Profissional extends Usuario { 

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Column(nullable = false)
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
  

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 0, max = 11, message = "Telefone deve ter 11 dígitos")
    private String cpf;

    private String telefone;
    private String sexo;
    private LocalDate dataNascimento;

    public Profissional() {
        super();
        this.setRole("ROLE_PROFISSIONAL");
    }

    public Profissional(String email, String senha, String nome, String cpf, String telefone, String sexo, LocalDate dataNascimento) {
        super(email, senha, nome, "ROLE_PROFISSIONAL");
        this.cpf = cpf;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters de Profissional
    public String getCpf() { 
        return cpf; 
    }
    public void setCpf(String cpf) { 
        this.cpf = cpf; 
    }
    public String getTelefone() { 
        return telefone; 
    }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }
    public String getSexo() { 
        return sexo; 
    }
    public void setSexo(String sexo) { 
        this.sexo = sexo; 
    }
    public LocalDate getDataNascimento() { 
        return dataNascimento; 
    }
    public void setDataNascimento(LocalDate dataNascimento) { 
        this.dataNascimento = dataNascimento; 
    }

}