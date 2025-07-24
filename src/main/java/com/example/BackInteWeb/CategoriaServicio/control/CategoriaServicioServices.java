package com.example.BackInteWeb.CategoriaServicio.control;

import com.example.BackInteWeb.CategoriaServicio.model.CategoriaRepository;
import com.example.BackInteWeb.CategoriaServicio.model.CategoriaServicio;
import com.example.BackInteWeb.CategoriaServicio.model.CategoriaServicioDTO;
import com.example.BackInteWeb.utils.Message;
import com.example.BackInteWeb.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CategoriaServicioServices {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServicioServices.class);

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaServicioServices(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listaCategoria() {
        List<CategoriaServicio> categoriaServicios = categoriaRepository.findAll();

        if (categoriaServicios.isEmpty()) {
            logger.warn("No se encontraron categorías");
            return new ResponseEntity<>(
                    new Message(null, "No se encontraron categorías", TypesResponse.WARNING),
                    HttpStatus.NOT_FOUND
            );
        }

        logger.info("Lista de categorías encontrada");
        return new ResponseEntity<>(
                new Message(categoriaServicios, "Lista de categorías", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listaIdCategoria(Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaRepository.findById(id);
        if (categoriaServicio.isPresent()) {
            logger.info("Categoría con ID {} encontrada", id);
            return new ResponseEntity<>(
                    new Message(categoriaServicio.get(), "Categoría encontrada", TypesResponse.SUCCESS),
                    HttpStatus.OK
            );
        }
        logger.warn("Categoría con ID {} no encontrada", id);
        return new ResponseEntity<>(
                new Message(null, "Categoría no encontrada", TypesResponse.WARNING),
                HttpStatus.NOT_FOUND
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> crearCategoria(CategoriaServicioDTO dto) {

        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            logger.warn("El nombre ya esta registrado");
            return new ResponseEntity<>(new Message("El nombre ya esta registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() > 100 ||  dto.getDescripcion().length() > 200) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        CategoriaServicio categoriaServicio = new CategoriaServicio(dto.getNombre(), dto.getDescripcion(), true);
        categoriaServicio = categoriaRepository.saveAndFlush(categoriaServicio);

        if (categoriaServicio == null) {
            return new ResponseEntity<>(new Message("No se registró la categoria de servicios",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("Registro de categorias de sevicio exitoso");
        return new ResponseEntity<>(new Message(categoriaServicio, "Categorias de servicio registrado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> actualizarCategoria(CategoriaServicioDTO dto) {
        Optional<CategoriaServicio> categoriaOptional = categoriaRepository.findById(dto.getIdCategoriaServicio());

        if (!categoriaOptional.isPresent()) {
            return new ResponseEntity<>(new Message("El nombre de la categoria no existe",TypesResponse.ERROR),HttpStatus.NOT_FOUND);
        }

        if (categoriaRepository.existsByNombreAndIdCategoriaServicio(dto.getNombre(), dto.getIdCategoriaServicio())) {
            logger.warn("El nombre '{}' ya está siendo utilizado", dto.getNombre());
            return new ResponseEntity<>(new Message("La nueva categoria ya está registrada", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() > 100 ||  dto.getDescripcion().length() > 200) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        CategoriaServicio categoriaServicio = categoriaOptional.get();
        categoriaServicio.setNombre(dto.getNombre());
        categoriaServicio.setDescripcion(dto.getDescripcion());
        categoriaRepository.saveAndFlush(categoriaServicio);
        if (categoriaServicio == null) {
            return new ResponseEntity<>(new Message("El juego no se actualizó",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(categoriaServicio,"La categoria de servicios se actualizó correctamente",TypesResponse.SUCCESS),HttpStatus.OK);
    }

    public boolean eliminar (Long id) {
        CategoriaServicio categoriaServicio = categoriaRepository.findById(id).orElse(null);
        if (categoriaServicio != null) {
            categoriaServicio.setStatus(false);
            categoriaRepository.save(categoriaServicio);
            return true;
        }
        return false;
    }

    public boolean restaurar(Long id) {
        CategoriaServicio categoriaServicio = categoriaRepository.findById(id).orElse(null);
        if (categoriaServicio != null) {
            categoriaServicio.setStatus(true);
            categoriaRepository.save(categoriaServicio);
            return true;
        }
        return false;
    }

}
