package com.example.vagas.controller;

import com.example.vagas.model.Profissional;
import com.example.vagas.repository.ProfissionalRepository;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "cpf", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    String cpfLimpo = text.replaceAll("\\D", "");
                    if (cpfLimpo.length() == 11) {
                        String cpfFormatado = cpfLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
                        setValue(cpfFormatado);
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

        binder.registerCustomEditor(String.class, "telefone", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    String telLimpo = text.replaceAll("\\D", "");
                    if (telLimpo.length() == 11) {
                        String telFormatado = telLimpo.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
                        setValue(telFormatado);
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

    @GetMapping
    public String listar(Model model) {
        Iterable<Profissional> profissionais = profissionalRepository.findAll();
        model.addAttribute("profissionais", profissionais);
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