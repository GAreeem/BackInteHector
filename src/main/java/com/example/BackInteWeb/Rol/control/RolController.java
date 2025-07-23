package com.example.BackInteWeb.Rol.control;

import com.example.BackInteWeb.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/")
    public ResponseEntity<Message> obtenerTodos() {
        return rolService.lista();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> obtenerPorId(@PathVariable Long id) {
        return rolService.RolPorId(id);
    }
}
