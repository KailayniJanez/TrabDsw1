package com.example.vagas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;         // Para validar formato de e-mail
import jakarta.validation.constraints.NotBlank;     // Para campos String que não podem ser vazios ou nulos
import jakarta.validation.constraints.Pattern;      // Para validar formato de CNPJ
import jakarta.validation.constraints.Size;         // Para validar tamanho de String

@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email(message = "{empresa.email.invalid}")
    @NotBlank(message = "{empresa.email.notblank}")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "{empresa.senha.notblank}")
    @Size(min = 6, message = "{empresa.senha.size}") // Exemplo: senha com no mínimo 6 caracteres
    private String senha;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "{empresa.cnpj.notblank}")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "{empresa.cnpj.pattern}") // para CNPJ (XX.XXX.XXX/XXXX-XX)
    private String cnpj;

    @NotBlank(message = "{empresa.nome.notblank}") // Nome é obrigatório
    @Size(min = 3, max = 100, message = "{empresa.nome.size}") // Nome deve ter entre 3 e 100 caracteres
    private String nome;

    @NotBlank(message = "{empresa.descricao.notblank}") // Descrição é obrigatória
    @Size(min = 10, max = 500, message = "{empresa.descricao.size}") // Descrição deve ter entre 10 e 500 caracteres
    private String descricao;

    @NotBlank(message = "{empresa.cidade.notblank}") // Cidade é obrigatória
    private String cidade;

    // Construtor padrão
    public Empresa() {
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
