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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    // Método auxiliar para converter Iterable para List
    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                          .collect(Collectors.toList());
    }

    @PostMapping("/nova")
    public String processarCandidatura(
            @RequestParam("vagaId") Long vagaId,
            @RequestParam("curriculo") MultipartFile curriculo,
            @RequestParam(value = "mensagem", required = false) String mensagem,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            Vaga vaga = vagaRepo.findById(vagaId).orElseThrow(() -> {
                return new IllegalArgumentException("Vaga não encontrada");
            });

            Profissional profissional = profissionalRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Profissional não encontrado");
                });

            if(candidaturaRepo.existsByVagaAndProfissional(vaga, profissional)) {
                redirectAttributes.addFlashAttribute("error", "Você já se candidatou a esta vaga");
                return "redirect:/vagas/listagem";
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
            redirectAttributes.addFlashAttribute("success", "Candidatura enviada com sucesso!");
            return "redirect:/profissional/candidaturas";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao enviar candidatura: " + e.getMessage());
            return "redirect:/vagas/listagem";
        }
    }

    @GetMapping
    public String listarCandidaturas(Model model, Authentication authentication) {
        Profissional profissional = profissionalRepo.findByEmail(authentication.getName()).orElseThrow();
        Iterable<Candidatura> candidaturas = candidaturaRepo.findByProfissionalOrderByDataCandidaturaDesc(profissional);
        model.addAttribute("candidaturas", toList(candidaturas));
        return "profissional/candidaturas";
    }
    
    @GetMapping("/{id}/curriculo")
    public String verCurriculo(@PathVariable Long id, Model model) {
        Candidatura candidatura = candidaturaRepo.findById(id).orElseThrow();
        model.addAttribute("curriculoNome", candidatura.getCurriculoNome());
        model.addAttribute("curriculoBytes", candidatura.getCurriculo());
        return "curriculoView";
    }
}