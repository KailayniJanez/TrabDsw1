package com.example.vagas.controller;

import com.example.vagas.model.Vaga;
import com.example.vagas.repository.VagaRepository;
import com.example.vagas.repository.EmpresaRepository; 
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public String listarVagas(Model model, @RequestParam(required = false) String cidade) {
        List<Vaga> vagas = (cidade == null || cidade.isEmpty()) ?
            vagaRepository.findByDataLimiteAfter(LocalDate.now()) :
            vagaRepository.findByCidadeAndDataLimiteAfter(cidade, LocalDate.now());

        model.addAttribute("vagas", vagas);
        return "vagas/listagem";
    }

    // Método para exibir o formulário de cadastro de nova vaga
    @GetMapping("/form") // para mostrar o formulário de nova vaga
    public String showVagaForm(Model model) {
        model.addAttribute("vaga", new Vaga());
        // Adiciona todas as empresas ao modelo para popular um <select> no formulário,
        // permitindo associar a vaga a uma empresa existente.
        model.addAttribute("empresas", empresaRepository.findAll());
        return "vagas/form"; // Nome do template do formulário de vaga
    }

    // Método para processar o envio do formulário e salvar a vaga (com validação)
    @PostMapping
    public String saveVaga(@Valid @ModelAttribute Vaga vaga, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erros de validação, retorna para o formulário.
            model.addAttribute("empresas", empresaRepository.findAll());
            return "vagas/form"; // Retorna para o formulário para exibir erros
        }
        // Se não houver erros, salva a vaga no banco de dados
        vagaRepository.save(vaga);
        return "redirect:/vagas"; // Redireciona para a listagem de vagas (R4)
    }

    // Você também pode adicionar métodos para editar e excluir vagas aqui, seguindo o padrão CRUD
    // @GetMapping("/editar/{id}") e @GetMapping("/excluir/{id}")
}
