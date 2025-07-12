package com.example.vagas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailEntrevista(String destinatario, String vagaDescricao,
                                    LocalDateTime dataEntrevista, String linkEntrevista) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Convite para entrevista - " + vagaDescricao);

        String texto = "Parabéns! Você foi selecionado para uma entrevista.\n\n" +
                      "Detalhes da vaga: " + vagaDescricao + "\n" +
                      "Data da entrevista: " + dataEntrevista.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                      "Link da entrevista: " + linkEntrevista + "\n\n" +
                      "Atenciosamente,\nEquipe de Recrutamento";

        message.setText(texto);
        mailSender.send(message);
    }

    public void enviarEmailRejeicao(String destinatario, String vagaDescricao, String mensagem) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Resultado da sua candidatura - " + vagaDescricao);

        String texto = "Agradecemos seu interesse na vaga " + vagaDescricao + ".\n\n" +
                      "Informamos que seu perfil não foi selecionado para esta oportunidade.\n" +
                      (mensagem != null ? "Mensagem do recrutador: " + mensagem + "\n" : "") +
                      "\nAtenciosamente,\nEquipe de Recrutamento";

        message.setText(texto);
        mailSender.send(message);
    }
}