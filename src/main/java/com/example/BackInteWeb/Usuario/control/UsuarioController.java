package com.example.BackInteWeb.Usuario.control;

import com.example.BackInteWeb.Usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario datos) {
        Usuario actualizado = usuarioService.actualizar(id, datos);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return usuarioService.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurar(@PathVariable Long id) {
        return usuarioService.restaurar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
