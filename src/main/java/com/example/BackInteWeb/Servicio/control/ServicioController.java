package com.example.BackInteWeb.Servicio.control;

import com.example.BackInteWeb.Servicio.model.ServicioDTO;
import com.example.BackInteWeb.utils.Message;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

    private final ServicioServices servicioServices;

    @Autowired
    public ServicioController(ServicioServices servicioServices) {
        this.servicioServices = servicioServices;
    }

    @GetMapping("/")
    public ResponseEntity<Message> findAll() {
        return servicioServices.listaServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return servicioServices.ServicioPorId(id);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<Message> findByCategoria(@PathVariable Long idCategoria) {
        return servicioServices.listaServiciosPorCategoria(idCategoria);
    }

    @PostMapping("/")
    public ResponseEntity<Message> crear(@Validated({ServicioDTO.Register.class, Default.class}) @RequestBody ServicioDTO servicioDTO) {
        return servicioServices.crearServicio(servicioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizar(@Validated({ServicioDTO.Register.class, Default.class}) @RequestBody ServicioDTO servicioDTO) {
        return servicioServices.actualizarServicio(servicioDTO);
    }
}
