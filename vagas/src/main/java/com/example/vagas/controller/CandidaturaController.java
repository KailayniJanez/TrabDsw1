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
            Authentication authentication) throws IOException {
        
        Vaga vaga = vagaRepo.findById(vagaId).orElseThrow();
        Profissional profissional = profissionalRepo.findByEmail(authentication.getName()).orElseThrow();
        
        // Verifica se já existe candidatura
        if(candidaturaRepo.existsByVagaAndProfissional(vaga, profissional)) {
            return "redirect:/vagas/listagem?error=duplicado";
        }
        
        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setProfissional(profissional);
        candidatura.setDataCandidatura(LocalDateTime.now());
        candidatura.setMensagem(mensagem);
        candidatura.setCurriculoNome(curriculo.getOriginalFilename());
        candidatura.setCurriculo(curriculo.getBytes());
        candidatura.setStatus(StatusCandidatura.ABERTO);
        
        candidaturaRepo.save(candidatura);
        
        return "redirect:/profissional/candidaturas?success";
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