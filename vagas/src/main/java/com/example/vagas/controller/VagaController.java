package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.model.Vaga;
import com.example.vagas.repository.VagaRepository;
import com.example.vagas.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/listagem")
    public String listarVagasPublicas(Model model, 
                                    @RequestParam(required = false) String cidade) {
        try {
            List<Vaga> vagas;
            
            if (cidade != null && !cidade.isEmpty()) {
                vagas = vagaRepository.findByEmpresaCidadeAndDataLimiteInscricaoGreaterThanEqual(
                    cidade, 
                    LocalDate.now()
                );
            } else {
                vagas = vagaRepository.findByDataLimiteInscricaoGreaterThanEqual(LocalDate.now());
            }
            
            if (vagas.isEmpty()) {
                model.addAttribute("mensagem", 
                    cidade != null ? 
                    "Nenhuma vaga disponível em " + cidade : 
                    "Nenhuma vaga disponível no momento");
            }
            
            model.addAttribute("vagas", vagas);
            return "vagas/listagem";
            
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar vagas: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/index")
    public String indexEmpresa(Model model, Authentication authentication) {
        try {
            Empresa empresa = empresaService.buscarPorUsuario(authentication.getName());
            List<Vaga> vagas = vagaRepository.findByEmpresaAndDataLimiteInscricaoGreaterThanEqual(empresa, LocalDate.now());
            
            if (vagas.isEmpty()) {
                model.addAttribute("mensagem", "Nenhuma vaga cadastrada ou ativa");
            }
            
            model.addAttribute("vagas", vagas);
            return "vagas/index";
            
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar vagas: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/form")
    public String form(Model model, Authentication authentication) {
        Vaga vaga = new Vaga();
        Empresa empresa = empresaService.buscarPorUsuario(authentication.getName());
        vaga.setEmpresa(empresa);
        
        model.addAttribute("vaga", vaga);
        return "vagas/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Vaga vaga, Authentication authentication) {
        Empresa empresa = empresaService.buscarPorUsuario(authentication.getName());
        vaga.setEmpresa(empresa);
        vagaRepository.save(vaga);
        return "redirect:/vagas/index";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, Authentication authentication) {
        Vaga vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vaga não encontrada"));
        
        Empresa empresaLogada = empresaService.buscarPorUsuario(authentication.getName());
        if (!vaga.getEmpresa().getId().equals(empresaLogada.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar esta vaga");
        }
        
        model.addAttribute("vaga", vaga);
        return "vagas/form";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, Authentication authentication) {
        Vaga vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vaga não encontrada"));
        
        Empresa empresaLogada = empresaService.buscarPorUsuario(authentication.getName());
        if (!vaga.getEmpresa().getId().equals(empresaLogada.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir esta vaga");
        }
        
        vagaRepository.deleteById(id);
        return "redirect:/vagas/index";
    }
}