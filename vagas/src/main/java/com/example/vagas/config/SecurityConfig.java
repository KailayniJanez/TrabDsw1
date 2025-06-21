package com.example.vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/vagas/**", "/images/**", "/login", "/css/**", "/js/**").permitAll() // Faltam esses arquivos "/login", "/css/**", "/js/**
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/profissional/**").hasRole("PROFISSIONAL")
                .requestMatchers("/empresa/**").hasRole("EMPRESA")
                .anyRequest().authenticated() // protege tudo que não foi explicitamente liberado
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
            .username("admin@admin.com")
            .password("{noop}admin")
            .roles("ADMIN")
            .build();

        UserDetails profissional = User.builder()
            .username("prof@prof.com")
            .password("{noop}prof")
            .roles("PROFISSIONAL")
            .build();

        UserDetails empresa = User.builder()
            .username("empresa@empresa.com")
            .password("{noop}empresa")
            .roles("EMPRESA")
            .build();

        return new InMemoryUserDetailsManager(admin, profissional, empresa);    
        
    }
}


