package com.example.vagas.controller;

import com.example.vagas.model.*;
import com.example.vagas.repository.*;
import com.example.vagas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/profissional/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaRepository candidaturaRepo;
    
    @Autowired
    private VagaRepository vagaRepo;
    
    @Autowired
    private ProfissionalRepository profissionalRepo;
    
    @Autowired
    private EmailService emailService;

    // R5: Processar candidatura
    @PostMapping("/nova")
public String processarCandidatura(
        @RequestParam("vagaId") Long vagaId,
        @RequestParam("curriculo") MultipartFile curriculo,
        @RequestParam(value = "mensagem", required = false) String mensagem,
        Authentication authentication,
        RedirectAttributes redirectAttributes) {

    System.out.println("Iniciando processamento de candidatura..."); // Debug
    
    try {
        System.out.println("Buscando vaga ID: " + vagaId); // Debug
        Vaga vaga = vagaRepo.findById(vagaId).orElseThrow(() -> {
            System.out.println("Vaga não encontrada: " + vagaId); // Debug
            return new IllegalArgumentException("Vaga não encontrada");
        });

        System.out.println("Buscando profissional: " + authentication.getName()); // Debug
        Profissional profissional = profissionalRepo.findByEmail(authentication.getName())
            .orElseThrow(() -> {
                System.out.println("Profissional não encontrado"); // Debug
                return new IllegalArgumentException("Profissional não encontrado");
            });

        System.out.println("Verificando candidatura duplicada..."); // Debug
        if(candidaturaRepo.existsByVagaAndProfissional(vaga, profissional)) {
            System.out.println("Candidatura duplicada detectada"); // Debug
            redirectAttributes.addFlashAttribute("error", "Você já se candidatou a esta vaga");
            return "redirect:/vagas/listagem";
        }

        System.out.println("Criando nova candidatura..."); // Debug
        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setProfissional(profissional);
        candidatura.setDataCandidatura(LocalDateTime.now());
        candidatura.setMensagem(mensagem);
        candidatura.setCurriculoNome(curriculo.getOriginalFilename());
        candidatura.setCurriculo(curriculo.getBytes());
        candidatura.setStatus(StatusCandidatura.ABERTO);

        System.out.println("Salvando candidatura..."); // Debug
        candidaturaRepo.save(candidatura);
        System.out.println("Candidatura salva com sucesso"); // Debug

        redirectAttributes.addFlashAttribute("success", "Candidatura enviada com sucesso!");
        return "redirect:/profissional/candidaturas";

    } catch (Exception e) {
        System.out.println("ERRO: " + e.getMessage()); // Debug
        e.printStackTrace(); // Debug
        redirectAttributes.addFlashAttribute("error", "Erro ao enviar candidatura: " + e.getMessage());
        return "redirect:/vagas/listagem";
    }
}
    // R7: Listar candidaturas do profissional
    @GetMapping
    public String listarCandidaturas(Model model, Authentication authentication) {
        Profissional profissional = profissionalRepo.findByEmail(authentication.getName()).orElseThrow();
        List<Candidatura> candidaturas = candidaturaRepo.findByProfissionalOrderByDataCandidaturaDesc(profissional);
        model.addAttribute("candidaturas", candidaturas);
        return "profissional/candidaturas";
    }
    
    // Download do currículo
    @GetMapping("/{id}/curriculo")
    public String verCurriculo(@PathVariable Long id, Model model) {
        Candidatura candidatura = candidaturaRepo.findById(id).orElseThrow();
        model.addAttribute("curriculoNome", candidatura.getCurriculoNome());
        model.addAttribute("curriculoBytes", candidatura.getCurriculo());
        return "curriculoView";
    }
}