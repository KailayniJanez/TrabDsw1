package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.repository.EmpresaRepository;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "cnpj", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    String cnpjLimpo = text.replaceAll("\\D", "");
                    if (cnpjLimpo.length() == 14) {
                        String cnpjFormatado = cnpjLimpo.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
                        setValue(cnpjFormatado);
                    } else {
                        setValue(text);
                    }
                } else {
                    setValue(null);
                }
            }
            
            @Override
            public String getAsText() {
                return (String) getValue();
            }
        });
    }

    @GetMapping("/list")
    public String listar(Model model) {
        Iterable<Empresa> empresas = empresaRepository.findAll();
        model.addAttribute("empresas", empresas);
        return "empresas/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Empresa empresa, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("empresa", empresa);
            return "empresas/form";
        }

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
        return "redirect:/admin/empresas/list";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaRepository.findById(id).orElseThrow());
        return "empresas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/admin/empresas/list";
    }
}