package com.example.vagas.controller;

import com.example.vagas.model.Profissional;
import com.example.vagas.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalRestController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @GetMapping
    public ResponseEntity<Iterable<Profissional>> listarTodos() {
        return ResponseEntity.ok(profissionalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        return profissional.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profissional> criar(@RequestBody Profissional profissional) {
        Profissional profissionalSalvo = profissionalRepository.save(profissional);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(profissionalSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(profissionalSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(@PathVariable Long id, @RequestBody Profissional profissional) {
        if (!profissionalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        profissional.setId(id);
        Profissional profissionalAtualizado = profissionalRepository.save(profissional);
        return ResponseEntity.ok(profissionalAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!profissionalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        profissionalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}