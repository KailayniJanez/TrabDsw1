package com.example.vagas;

import com.example.vagas.model.UsuarioAdmin;
import com.example.vagas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VagasApplication.class, args);
    }

    @Bean
CommandLineRunner initAdmin(UsuarioRepository repo, PasswordEncoder encoder) {
    return args -> {
        if (!repo.existsByEmail("admin@admin.com")) {
            UsuarioAdmin admin = new UsuarioAdmin();
            admin.setEmail("admin@admin.com");
            admin.setSenha(encoder.encode("admin"));
            admin.setNome("Administrador");
            repo.save(admin);
        }
    };
}
}
