package com.example.vagas.service;

import com.example.vagas.model.Empresa;
import com.example.vagas.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    public Empresa buscarPorUsuario(String email) {
        return empresaRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }
}
