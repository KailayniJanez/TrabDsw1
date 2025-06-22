package com.example.vagas.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        boolean isEmpresa = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_EMPRESA"));

        if (isAdmin) {
            response.sendRedirect("/admin/index");
        } else if (isEmpresa) {
            response.sendRedirect("/vagas/index");
        } else {
            response.sendRedirect("/"); 
        }
    }
}
