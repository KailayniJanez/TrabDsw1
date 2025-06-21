package com.example.vagas;

import com.example.vagas.domain.Admin;
import com.example.vagas.repository.AdminRepository;
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
    CommandLineRunner initAdmin(AdminRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByEmail("admin@admin.com")) {
                Admin admin = new Admin();
                admin.setEmail("admin@admin.com");
                admin.setSenha(encoder.encode("admin")); // Senha segura
                repo.save(admin);
            }
        };
    }
}
