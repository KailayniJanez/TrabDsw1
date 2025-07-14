package com.example.vagas.controller;

import com.example.vagas.model.Vaga;
import com.example.vagas.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
public class VagaRestController {

    @Autowired
    private VagaRepository vagaRepository;

    @GetMapping
    public ResponseEntity<Iterable<Vaga>> listarTodos() {
        return ResponseEntity.ok(vagaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable Long id) {
        Optional<Vaga> vaga = vagaRepository.findById(id);
        return vaga.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresas/{id}")
    public ResponseEntity<List<Vaga>> buscarPorEmpresa(@PathVariable Long id) {
        List<Vaga> vagas = vagaRepository.findByEmpresaIdAndDataLimiteInscricaoGreaterThanEqual(id, LocalDate.now());
        return ResponseEntity.ok(vagas);
    }
}