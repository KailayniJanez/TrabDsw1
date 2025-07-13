package com.example.vagas.controller;

import com.example.vagas.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VagaRepository vagaRepo;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("vagas", vagaRepo.findAllAbertas());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
