package com.example.BackInteWeb.Usuario.control;

import com.example.BackInteWeb.Usuario.model.UsuarioDTO;
import com.example.BackInteWeb.utils.Message;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public ResponseEntity<Message> listar() {
        return usuarioService.listar();
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id){
        return usuarioService.UsuarioById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<Message> crear(@Validated({UsuarioDTO.Register.class, Default.class}) @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crear(usuarioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizar(
            @PathVariable Long id,
            @Validated({UsuarioDTO.Modify.class, Default.class})
            @RequestBody UsuarioDTO usuarioDTO
    ) {
        usuarioDTO.setIdUser(id); // asegurar que el id venga correcto
        return usuarioService.actualizar(usuarioDTO);
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
