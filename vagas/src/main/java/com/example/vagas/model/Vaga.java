package com.example.vagas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent; // Para datas que devem ser no futuro ou presente
import jakarta.validation.constraints.NotBlank;     // Para campos String que não podem ser vazios ou nulos
import jakarta.validation.constraints.NotNull;      // Para campos de objetos (incluindo BigDecimal e LocalDate) que não podem ser nulos
import jakarta.validation.constraints.PositiveOrZero; // Para remuneração (pode ser 0 ou positiva)
import jakarta.validation.constraints.Size;         // Para validar tamanho de String

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Garante que uma Vaga sempre esteja associada a uma Empresa
    @NotNull(message = "{vaga.empresa.notnull}") // Mensagem se a empresa for nula
    private Empresa empresa; // A empresa a qual a vaga pertence

    @Column(length = 1000)
    @NotBlank(message = "{vaga.descricao.notblank}")
    @Size(min = 10, max = 1000, message = "{vaga.descricao.size}")
    private String descricao; // perfil profissional, habilidades desejadas etc

    @NotNull(message = "{vaga.remuneracao.notnull}")
    @PositiveOrZero(message = "{vaga.remuneracao.positiveorzero}") // Remuneração pode ser 0 ou positiva
    private BigDecimal remuneracao; // remuneração

    @NotNull(message = "{vaga.dataLimite.notnull}")
    @FutureOrPresent(message = "{vaga.dataLimite.futureorpresent}") // Data limite deve ser hoje ou no futuro
    private LocalDate dataLimite; // data limite de inscrição

    @NotBlank(message = "{vaga.cidade.notblank}") // Cidade onde a vaga está localizada
    private String cidade;

    // Construtor padrão
    public Vaga() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(BigDecimal remuneracao) {
        this.remuneracao = remuneracao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}