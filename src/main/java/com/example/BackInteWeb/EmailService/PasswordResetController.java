package com.example.BackInteWeb.EmailService;

import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
import com.example.BackInteWeb.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("El correo no está registrado");
        }

        String token = jwtUtil.generatePasswordResetToken(email);
        emailService.sendResetPasswordEmail(email, token);

        return ResponseEntity.ok("Corre enviado");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.badRequest().body("Token invalido o expirado");
        }

        String email = jwtUtil.getEmailFromToken(token);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña actualizada");
    }
}
