package com.example.BackInteWeb.config;

import com.example.BackInteWeb.Rol.model.Rol;
import com.example.BackInteWeb.Rol.model.RolRepository;
import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // <-- Inyectamos el encoder

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        List<String> roles = List.of("ADMIN", "USUARIO", "CLIENTE");

        for (String r : roles) {
            if (rolRepository.findByRol(r) == null) {
                rolRepository.save(new Rol(r));
            }
        }

        // Crear usuario admin
        if (usuarioRepository.findAll().isEmpty()) {
            Rol rolAdmin = rolRepository.findByRol("ADMIN");
            String passwordCifrada = passwordEncoder.encode("admin123");
            Usuario admin = new Usuario(
                    "Administrador", "General", "Sistema", "admin@gmail.com", "0000000000", passwordCifrada, true, rolAdmin
            );
            usuarioRepository.save(admin);
        }
    }
}
