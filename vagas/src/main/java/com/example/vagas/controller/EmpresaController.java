package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("admin/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public String listar(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Empresa empresa) {
        if (empresa.getId() == null) {
            empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
        } else {
            Empresa existente = empresaRepository.findById(empresa.getId()).orElseThrow();
            if (empresa.getSenha() == null || empresa.getSenha().isEmpty()) {
                empresa.setSenha(existente.getSenha());
            } else {
                empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
            }
        }

        empresaRepository.save(empresa);
        return "redirect:/admin/empresas";
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
