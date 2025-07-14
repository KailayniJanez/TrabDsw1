package com.example.vagas.controller;

import com.example.vagas.model.Empresa;
import com.example.vagas.service.EmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/admin/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // R2: CRUD de empresas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaService.listarTodasEmpresas());
        return "empresas/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    // R2: CRUD de empresas - C (Create) / U (Update)
    @PostMapping
    public String salvar(@Valid @ModelAttribute Empresa empresa, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        

        System.out.println("=== DEBUG EMPRESA ===");
        System.out.println("ID: " + empresa.getId());
        System.out.println("Nome: " + empresa.getNome());
        System.out.println("Email: " + empresa.getEmail());
        System.out.println("CNPJ: " + empresa.getCnpj());
        System.out.println("Cidade: " + empresa.getCidade());
        System.out.println("Descrição: " + empresa.getDescricao());
        System.out.println("====================");

        if (result.hasErrors()) {
            model.addAttribute("empresa", empresa); 
            return "empresas/form";
        }

        try {
            if (empresa.getId() == null) {
                if (empresaService.buscarEmpresaPorEmail(empresa.getEmail()).isPresent()) {
                    result.rejectValue("email", "error.empresa", "Email já cadastrado."); 
                    model.addAttribute("empresa", empresa);
                    return "empresas/form";
                }
                
                empresaService.criarEmpresa(empresa);
                redirectAttributes.addFlashAttribute("successMessage", "Empresa criada com sucesso!");
            } else {
                empresaService.atualizarEmpresa(empresa.getId(), empresa);
                redirectAttributes.addFlashAttribute("successMessage", "Empresa atualizada com sucesso!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar empresa: " + e.getMessage());
            model.addAttribute("empresa", empresa); 
            return "empresas/form"; // Retornar ao formulário com a mensagem de erro
        }
        return "redirect:/admin/empresas";
    }

    // R2: CRUD de empresas - U (Update)
    @GetMapping("/editar/{id}") 
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Empresa> empresaOpt = empresaService.buscarEmpresaPorId(id);
        if (empresaOpt.isPresent()) {
            model.addAttribute("empresa", empresaOpt.get());
            return "empresas/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Empresa não encontrada para edição.");
            return "redirect:/admin/empresas";
        }
    }

    // R2: CRUD de empresas - D (Delete)
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (empresaService.deletarEmpresa(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Empresa excluída com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Empresa não encontrada para exclusão.");
        }
        return "redirect:/admin/empresas";
    }
}