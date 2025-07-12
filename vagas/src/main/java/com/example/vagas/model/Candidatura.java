package com.example.vagas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;
    
    @Column(nullable = false)
    private LocalDateTime dataCandidatura;
    
    @Column(columnDefinition = "TEXT")
    private String mensagem;
    
    @Column(nullable = false)
    private String curriculoNome;
    
    @Lob
    @Column(nullable = false, length = 1048576)
    private byte[] curriculo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCandidatura status;
    
    private LocalDateTime dataEntrevista;
    
    private String linkEntrevista;

    // Construtores
    public Candidatura() {
        this.status = StatusCandidatura.ABERTO;
        this.dataCandidatura = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDateTime getDataCandidatura() {
        return dataCandidatura;
    }

    public void setDataCandidatura(LocalDateTime dataCandidatura) {
        this.dataCandidatura = dataCandidatura;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCurriculoNome() {
        return curriculoNome;
    }

    public void setCurriculoNome(String curriculoNome) {
        this.curriculoNome = curriculoNome;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public StatusCandidatura getStatus() {
        return status;
    }

    public void setStatus(StatusCandidatura status) {
        this.status = status;
    }

    public LocalDateTime getDataEntrevista() {
        return dataEntrevista;
    }

    public void setDataEntrevista(LocalDateTime dataEntrevista) {
        this.dataEntrevista = dataEntrevista;
    }

    public String getLinkEntrevista() {
        return linkEntrevista;
    }

    public void setLinkEntrevista(String linkEntrevista) {
        this.linkEntrevista = linkEntrevista;
    }

    @Override
    public String toString() {
        return "Candidatura{" +
                "id=" + id +
                ", vaga=" + vaga.getId() +
                ", profissional=" + profissional.getId() +
                ", status=" + status +
                '}';
    }
}