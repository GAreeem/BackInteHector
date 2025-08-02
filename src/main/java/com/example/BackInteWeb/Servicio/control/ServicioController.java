package com.example.BackInteWeb.Servicio.control;

import com.example.BackInteWeb.CategoriaServicio.model.ServicioRegistroRequest;
import com.example.BackInteWeb.Servicio.model.ServicioDTO;
import com.example.BackInteWeb.utils.Message;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//    @PostMapping("/")
//    public ResponseEntity<Message> crear(@Validated({ServicioDTO.Register.class, Default.class}) @RequestBody ServicioDTO servicioDTO) {
//        return servicioServices.crearServicio(servicioDTO);

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> crear(@Validated @RequestPart("datos")ServicioRegistroRequest datos, @RequestPart("imagen")MultipartFile imagen) {
        return servicioServices.crearServicio(datos, imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizar(@Validated({ServicioDTO.Register.class, Default.class}) @RequestBody ServicioDTO servicioDTO) {
        return servicioServices.actualizarServicio(servicioDTO);
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Message> desactivarServicio(@PathVariable Long id) {
        return servicioServices.desactivarServicio(id);
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Message> activarServicio(@PathVariable Long id) {
        return servicioServices.reactivarServicio(id);
    }

    @GetMapping("/activos")
    public ResponseEntity<Message> listarServiciosActivos() {
        return servicioServices.serviciosActivos();
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Message> listarServiciosInactivos() {
        return servicioServices.serviciosInactivos();
    }
    
}
