package com.example.vagas.controller;

import com.example.vagas.model.Profissional;
import com.example.vagas.repository.ProfissionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

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

    @PostMapping // O método 'salvar' será alterado
    public String salvar(@Valid @ModelAttribute Profissional profissional, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erros de validação, retorna para o mesmo formulário
            return "profissionais/form";
        }
        // Se não houver erros, salva o profissional no banco de dados
        profissionalRepository.save(profissional);
        return "redirect:/admin/profissionais"; // Redireciona para a listagem
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


