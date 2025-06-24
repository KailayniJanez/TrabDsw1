package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.model.Vaga;
import com.example.vagas.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.vagas.service.EmpresaService;
import org.springframework.security.core.Authentication;

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
    public String listarVagas(Model model, @RequestParam(required = false) String cidade) {
        List<Vaga> vagas = (cidade == null || cidade.isEmpty()) ?
            vagaRepository.findByDataLimiteInscricaoAfter(LocalDate.now()) :
            vagaRepository.findByCidadeAndDataLimiteInscricaoAfter(cidade, LocalDate.now());

        model.addAttribute("vagas", vagas);
        return "vagas/listagem";
    }

    @GetMapping("/index")
    public String indexEmpresa(Model model, Authentication authentication) {
        Empresa empresa = empresaService.buscarPorUsuario(authentication.getName()); // ou outro método
        List<Vaga> vagas = vagaRepository.findByEmpresaAndDataLimiteInscricaoAfter(empresa, LocalDate.now());
        model.addAttribute("vagas", vagas);
        return "vagas/index";
    }

    @PostMapping
    public String salvar(@ModelAttribute Vaga vaga, Authentication authentication) {
        Empresa empresa = empresaService.buscarPorUsuario(authentication.getName());
        vaga.setEmpresa(empresa); // força a associação
        vagaRepository.save(vaga);
        return "redirect:/vagas/index";
    }


    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("vaga", new Vaga());
        return "vagas/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Vaga vaga = vagaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada"));
        model.addAttribute("vaga", vaga);
        return "vagas/form";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        vagaRepository.deleteById(id);
        return "redirect:/vagas/index";
    }

}


