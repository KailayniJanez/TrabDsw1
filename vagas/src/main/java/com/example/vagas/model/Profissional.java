package com.example.vagas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;         // Para validar formato de e-mail
import jakarta.validation.constraints.NotBlank;     // Para campos String que não podem ser vazios ou nulos
import jakarta.validation.constraints.NotNull;      // Para campos não String que não podem ser nulos
import jakarta.validation.constraints.Past;         // Para datas no passado
import jakarta.validation.constraints.Pattern;      // Para validar formato de CPF, telefone
import jakarta.validation.constraints.Size;         // Para validar tamanho de String

import java.time.LocalDate; // Para a data de nascimento

@Entity
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email(message = "{profissional.email.invalid}")
    @NotBlank(message = "{profissional.email.notblank}")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "{profissional.senha.notblank}")
    @Size(min = 6, message = "{profissional.senha.size}") // Exemplo: senha com no mínimo 6 caracteres
    private String senha;

    @Column(nullable = false)
    @NotBlank(message = "{profissional.cpf.notblank}")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "{profissional.cpf.pattern}") //  para CPF (XXX.XXX.XXX-XX)
    private String cpf;

    @NotBlank(message = "{profissional.nome.notblank}") // Nome é obrigatório
    @Size(min = 3, max = 100, message = "{profissional.nome.size}") // Nome deve ter entre 3 e 100 caracteres
    private String nome;

    @NotBlank(message = "{profissional.telefone.notblank}") // Telefone é obrigatório
    @Pattern(regexp = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$", message = "{profissional.telefone.pattern}") // para telefone (XX) XXXX-XXXX ou (XX) XXXXX-XXXX
    private String telefone;

    @NotBlank(message = "{profissional.sexo.notblank}") // Sexo é obrigatório
    private String sexo;

    @NotNull(message = "{profissional.dataNascimento.notnull}") // Data de nascimento não pode ser nula
    @Past(message = "{profissional.dataNascimento.past}") // Data de nascimento deve ser no passado
    private LocalDate dataNascimento;

    // Construtor padrão (importante para JPA)
    public Profissional() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
