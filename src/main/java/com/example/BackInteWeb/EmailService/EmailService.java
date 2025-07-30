package com.example.BackInteWeb.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String token) {
        try {
            String resetUrl = "http://localhost:4200/reset-password?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Recuperación de contraseña");
            message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetUrl);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }
}
