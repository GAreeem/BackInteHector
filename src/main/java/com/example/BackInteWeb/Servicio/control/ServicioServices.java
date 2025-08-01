package com.example.BackInteWeb.Servicio.control;

import com.example.BackInteWeb.CategoriaServicio.model.CategoriaRepository;
import com.example.BackInteWeb.CategoriaServicio.model.CategoriaServicio;
import com.example.BackInteWeb.Servicio.model.Servicio;
import com.example.BackInteWeb.Servicio.model.ServicioDTO;
import com.example.BackInteWeb.Servicio.model.ServicioRepository;
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
public class ServicioServices {

    private static final Logger logger = LoggerFactory.getLogger(ServicioServices.class);

    private final ServicioRepository servicioRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ServicioServices(ServicioRepository servicioRepository,  CategoriaRepository categoriaRepository) {
        this.servicioRepository = servicioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listaServicios() {
        List<Servicio> servicios = servicioRepository.findAll();

        if (servicios.isEmpty()) {
            logger.warn("No se encontraron servicios registrados");
            return new ResponseEntity<>(new Message(null, "No se encontraron servicios", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        logger.info("Lista de servicios recuperada exitosamente");
        return new ResponseEntity<>(new Message(servicios, "Lista de servicios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> ServicioPorId(Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);

        if (servicio.isPresent()) {
            logger.info("Servicio con ID {} encontrado", id);
            return new ResponseEntity<>(new Message(servicio.get(), "Servicio encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
        }
        logger.warn("Servicio con ID {} no encontrado", id);
        return new ResponseEntity<>(new Message(null, "Servicio no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listaServiciosPorCategoria(Long idCategoria) {
        List<Servicio> servicios = servicioRepository.findByCategoria_IdCategoriaServicio(idCategoria);

        if (servicios.isEmpty()) {
            logger.warn("No se encontraron servicios para la categoría ID {}", idCategoria);
            return new ResponseEntity<>(new Message(null, "No se encontraron servicios para esta categoría", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        logger.info("Servicios encontrados para la categoría ID {}", idCategoria);
        return new ResponseEntity<>(new Message(servicios, "Lista de servicios por categoría", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> crearServicio(ServicioDTO dto) {
        if (servicioRepository.existsByNombre(dto.getNombre())) {
            logger.warn("El servicio con nombre '{}' ya existe", dto.getNombre());
            return new ResponseEntity<>(new Message("El nombre ya está registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() > 100 || dto.getDescripcion().length() > 200) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<CategoriaServicio> categoria = categoriaRepository.findById(dto.getIdCategoria());
        if (categoria.isEmpty()) {
            logger.warn("Categoría con ID {} no encontrada", dto.getIdCategoria());
            return new ResponseEntity<>(new Message("Categoría no válida", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Servicio servicio = new Servicio(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                false,
                categoria.get()
        );

        servicio = servicioRepository.saveAndFlush(servicio);
        logger.info("Servicio registrado exitosamente");
        return new ResponseEntity<>(new Message(servicio, "Servicio registrado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> actualizarServicio(ServicioDTO dto) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(dto.getIdServicio());

        if (servicioOptional.isEmpty()) {
            logger.warn("Servicio con ID {} no encontrado", dto.getIdServicio());
            return new ResponseEntity<>(new Message("Servicio no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        if (servicioRepository.existsByNombreAndIdServicioNot(dto.getNombre(), dto.getIdServicio())) {
            logger.warn("El nuevo nombre '{}' ya está en uso por otro servicio", dto.getNombre());
            return new ResponseEntity<>(new Message("El nombre ya está registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() > 100 || dto.getDescripcion().length() > 200) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<CategoriaServicio> categoria = categoriaRepository.findById(dto.getIdCategoria());
        if (categoria.isEmpty()) {
            logger.warn("Categoría con ID {} no encontrada", dto.getIdCategoria());
            return new ResponseEntity<>(new Message("Categoría no válida", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Servicio servicio = servicioOptional.get();
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setCategoria(categoria.get());

        servicioRepository.saveAndFlush(servicio);
        logger.info("Servicio con ID {} actualizado correctamente", dto.getIdServicio());
        return new ResponseEntity<>(new Message(servicio, "Servicio actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> desactivarServicio(Long idServicio) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isEmpty()) {
            logger.warn("Servicio con ID {} no encontrado para desactivación", idServicio);
            return new ResponseEntity<>(new Message("Servicio no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Servicio servicio = servicioOptional.get();
        servicio.setStatus(false);
        servicioRepository.save(servicio);
        logger.info("Servicio con ID {} desactivado correctamente", idServicio);
        return new ResponseEntity<>(new Message("Servicio desactivado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> reactivarServicio(Long idServicio) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isEmpty()) {
            logger.warn("Servicio con ID {} no encontrado para reactivación", idServicio);
            return new ResponseEntity<>(new Message("Servicio no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Servicio servicio = servicioOptional.get();
        servicio.setStatus(true);
        servicioRepository.save(servicio);
        logger.info("Servicio con ID {} reactivado correctamente", idServicio);
        return new ResponseEntity<>(new Message("Servicio reactivado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> serviciosActivos() {
        List<Servicio> activos = servicioRepository.findByStatusTrue();
        if (activos.isEmpty()) {
            logger.warn("No hay servicios activos");
            return new ResponseEntity<>(new Message(null, "No hay servicios activos", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(activos, "Lista de servicios activos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> serviciosInactivos() {
        List<Servicio> inactivos = servicioRepository.findByStatusFalse();
        if (inactivos.isEmpty()) {
            logger.warn("No hay servicios inactivos");
            return new ResponseEntity<>(new Message(null, "No hay servicios inactivos", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(inactivos, "Lista de servicios inactivos", TypesResponse.SUCCESS), HttpStatus.OK);
    }


}
