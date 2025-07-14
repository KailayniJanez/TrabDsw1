package com.example.vagas.controller;

import com.example.vagas.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.example.vagas.model.Vaga;


@Controller
public class HomeController {

    @Autowired
    private VagaRepository vagaRepo;

    @GetMapping("/")
    public String inicio(Model model) {
        Iterable<Vaga> vagas = vagaRepo.findAllAbertas();
        model.addAttribute("vagas", StreamSupport.stream(vagas.spliterator(), false)
                                             .collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
