package com.example.vagas.controller;

import com.example.vagas.model.Profissional;
import com.example.vagas.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profissionais", profissionalRepository.findAll());
        return "profissionais/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("profissional", new Profissional());
        return "profissionais/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Profissional profissional, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("profissional", profissional);
            return "profissionais/form";
        }

        if (profissional.getId() == null) {
            profissional.setSenha(passwordEncoder.encode(profissional.getSenha()));
        } else {
            Profissional existente = profissionalRepository.findById(profissional.getId()).orElseThrow();
            if (profissional.getSenha() == null || profissional.getSenha().isEmpty()) {
                profissional.setSenha(existente.getSenha());
            } else {
                profissional.setSenha(passwordEncoder.encode(profissional.getSenha()));
            }
        }

        profissionalRepository.save(profissional);
        return "redirect:/admin/profissionais";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("profissional", profissionalRepository.findById(id).orElseThrow());
        return "profissionais/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        profissionalRepository.deleteById(id);
        return "redirect:/admin/profissionais";
    }
}