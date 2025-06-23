package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping // O método 'salvar' foi alterado
    public String salvar(@Valid @ModelAttribute Empresa empresa, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erros de validação, retorna para o mesmo formulário
            return "empresas/form";
        }
        // Se não houver erros, salva a empresa no banco de dados
        empresaRepository.save(empresa);
        return "redirect:/admin/empresas"; // Redireciona para a listagem
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaRepository.findById(id).orElseThrow());
        return "empresas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/admin/empresas";
    }
}
