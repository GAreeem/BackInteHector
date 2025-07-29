package com.example.BackInteWeb.bitacora.control;

import com.example.BackInteWeb.bitacora.model.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bitacora")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping("/")
    public ResponseEntity<List<Bitacora>> obtenerTodas() {
        List<Bitacora> registros = bitacoraService.obtenerTodas();
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<String> eliminarTodos() {
        bitacoraService.eliminarTodos();
        return ResponseEntity.ok("Todos los registros de la bitácora fueron eliminados.");
    }

}

