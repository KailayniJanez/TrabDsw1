package com.example.vagas.controller;

import com.example.vagas.model.Vaga;
import com.example.vagas.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @GetMapping
    public String listarVagas(Model model, @RequestParam(required = false) String cidade) {
        List<Vaga> vagas = (cidade == null || cidade.isEmpty()) ?
            vagaRepository.findByDataLimiteInscricaoAfter(LocalDate.now()) :
            vagaRepository.findByCidadeAndDataLimiteInscricaoAfter(cidade, LocalDate.now());

        model.addAttribute("vagas", vagas);
        return "vagas/listagem";
    }
}

