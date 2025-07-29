package com.example.BackInteWeb.CategoriaServicio.control;

import com.example.BackInteWeb.CategoriaServicio.model.CategoriaServicioDTO;
import com.example.BackInteWeb.utils.Message;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaServicioController {

    private final CategoriaServicioServices categoriaServicioServices;

    @Autowired
    public CategoriaServicioController(CategoriaServicioServices categoriaServicioServices) {
        this.categoriaServicioServices = categoriaServicioServices;
    }

    @GetMapping("/")
    public ResponseEntity<Message> findAll() {
        return categoriaServicioServices.listaCategoria();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return categoriaServicioServices.listaIdCategoria(id);
    }

    @PostMapping("/")
    public ResponseEntity<Message> save(@Validated({CategoriaServicioDTO.Register.class, Default.class}) @RequestBody CategoriaServicioDTO categoriaServicioDTO) {
        return categoriaServicioServices.crearCategoria(categoriaServicioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizarCategoria(@Validated({CategoriaServicioDTO.Modify.class, Default.class}) @RequestBody CategoriaServicioDTO categoriaServicioDTO) {
        return categoriaServicioServices.actualizarCategoria(categoriaServicioDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> eliminar(@PathVariable Long id) {
        return categoriaServicioServices.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/restaurar/{id}")
    public ResponseEntity<Message> restaurar(@PathVariable Long id) {
        return categoriaServicioServices.restaurar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
