package com.example.BackInteWeb.login;

import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
import com.example.BackInteWeb.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(authRequest.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(authRequest.getEmail())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // Construir el DTO sin password
        UsuarioLoginDto usuarioDTO = new UsuarioLoginDto(
                usuario.getIdUser(),
                usuario.getNombre(),
                usuario.getApellidoP(),
                usuario.getApellidoM(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.isStatus(),
                usuario.getRol()
        );

        return ResponseEntity.ok(new AuthResponse(token, usuarioDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }
}
