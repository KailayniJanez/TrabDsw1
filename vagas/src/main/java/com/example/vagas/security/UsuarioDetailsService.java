package com.example.vagas.security;

import com.example.vagas.model.Admin;
import com.example.vagas.model.Empresa;
import com.example.vagas.repository.AdminRepository;
import com.example.vagas.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private EmpresaRepository empresaRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return User.withUsername(admin.getEmail())
                    .password(admin.getSenha())
                    .roles("ADMIN")
                    .build();
        }

        Optional<Empresa> empresaOpt = empresaRepo.findByEmail(email);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            return User.withUsername(empresa.getEmail())
                    .password(empresa.getSenha())
                    .roles("EMPRESA")
                    .build();
        }

        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
