package com.example.BackInteWeb.Reservacion.control;

import com.example.BackInteWeb.Reservacion.model.Reservacion;
import com.example.BackInteWeb.Reservacion.model.ReservacionDTO;
import com.example.BackInteWeb.Reservacion.model.ReservacionRepository;
import com.example.BackInteWeb.Servicio.model.Servicio;
import com.example.BackInteWeb.Servicio.model.ServicioRepository;
import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
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
public class ReservacionServices {

    private static final Logger logger = LoggerFactory.getLogger(ReservacionServices.class);

    private final ReservacionRepository reservacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;

    @Autowired
    public ReservacionServices(ReservacionRepository reservacionRepository,
                               UsuarioRepository usuarioRepository,
                               ServicioRepository servicioRepository) {
        this.reservacionRepository = reservacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listaReservaciones() {
        List<Reservacion> reservaciones = reservacionRepository.findAll();
        if (reservaciones.isEmpty()) {
            logger.warn("No se encontraron reservaciones");
            return new ResponseEntity<>(new Message(null, "No se encontraron reservaciones", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        logger.info("Reservaciones encontradas");
        return new ResponseEntity<>(new Message(reservaciones, "Lista de reservaciones", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> ReservacionPorId(Long id) {
        Optional<Reservacion> reservacion = reservacionRepository.findById(id);
        if (reservacion.isPresent()) {
            logger.info("Reservación con ID {} encontrada", id);
            return new ResponseEntity<>(new Message(reservacion.get(), "Reservación encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
        }
        logger.warn("Reservación con ID {} no encontrada", id);
        return new ResponseEntity<>(new Message(null, "Reservación no encontrada", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
    }
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> crearReservacion(ReservacionDTO dto) {

        //if (reservacionRepository.existsByNombre(dto.getNombre())) {
        //    logger.warn("El servicio con nombre '{}' ya existe", dto.getNombre());
        //    return new ResponseEntity<>(new Message("El nombre ya está registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        //}

        if (dto.getNombre().length() > 100 || dto.getDescripcion().length() > 200) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Servicio> servicio = servicioRepository.findById(dto.getIdServicio());
        if (servicio.isEmpty()) {
            logger.warn("Servicio con ID {} no encontrado", dto.getIdServicio());
            return new ResponseEntity<>(new Message("Servicio no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuario = usuarioRepository.findById(dto.getIdUsuario());
        if (usuario.isEmpty()) {
            logger.warn("Usuario con ID {} no encontrado", dto.getIdUsuario());
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Reservacion reservacion = new Reservacion(
                dto.getNombre(),
                dto.getDescripcion(),
                true,
                servicio.get(),
                usuario.get(),
                dto.getReservationDate()
        );

        reservacion = reservacionRepository.saveAndFlush(reservacion);
        logger.info("Reservación creada exitosamente");
        return new ResponseEntity<>(new Message(reservacion, "Reservación creada correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> obtenerReservacionesPorUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            logger.warn("Usuario con ID {} no encontrado", idUsuario);
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        List<Reservacion> reservaciones = reservacionRepository.findByUsuarioIdUser(idUsuario);
        logger.info("Se encontraron {} reservaciones para el usuario con ID {}", reservaciones.size(), idUsuario);
        return new ResponseEntity<>(new Message(reservaciones, "Reservaciones obtenidas correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> actualizarReservacion(ReservacionDTO dto) {
        Optional<Reservacion> reservacionOptional = reservacionRepository.findById(dto.getIdReservacion());

        if (reservacionOptional.isEmpty()) {
            logger.warn("Reservacion con ID {} no encontrado", dto.getIdServicio());
            return new ResponseEntity<>(new Message("Reservacion no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        if (reservacionRepository.existsByNombreAndIdReservacionNot(dto.getNombre(), dto.getIdReservacion())){
            logger.warn("El nuevo nombre '{}' ya está en uso por otro servicio", dto.getNombre());
            return new ResponseEntity<>(new Message("El nombre ya está registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() > 100 || dto.getDescripcion().length() > 300) {
            logger.warn("Uno o más campos exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Uno o más campos exceden el límite de caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Servicio> servicio = servicioRepository.findById(dto.getIdServicio());
        if (servicio.isEmpty()) {
            logger.warn("Servicio con ID {} no encontrado", dto.getIdServicio());
            return new ResponseEntity<>(new Message("Servicio no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuario = usuarioRepository.findById(dto.getIdUsuario());
        if (usuario.isEmpty()) {
            logger.warn("Usuario con ID {} no encontrado", dto.getIdUsuario());
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Reservacion reservacion = reservacionOptional.get();
        reservacion.setNombre(dto.getNombre());
        reservacion.setDescripcion(dto.getDescripcion());
        reservacion.setServicio(servicio.get());
        reservacion.setUsuario(usuario.get());
        reservacion.setReservationDate(dto.getReservationDate() != null ? dto.getReservationDate() : reservacion.getReservationDate());

        reservacionRepository.saveAndFlush(reservacion);
        logger.info("Reservación con ID {} actualizada correctamente", dto.getIdReservacion());
        return new ResponseEntity<>(new Message(reservacion, "Reservación actualizada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> cancelarReservacion(Long idReservacion) {
        Optional<Reservacion> reservacionOptional = reservacionRepository.findById(idReservacion);

        if (reservacionOptional.isEmpty()) {
            logger.warn("Reservación con ID {} no encontrada", idReservacion);
            return new ResponseEntity<>(new Message("Reservación no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Reservacion reservacion = reservacionOptional.get();

        // Verificar si ya está cancelada
        if (!reservacion.isStatus()) {
            logger.warn("Reservación con ID {} ya está cancelada", idReservacion);
            return new ResponseEntity<>(new Message("La reservación ya está cancelada", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Cambiar el estado a cancelado (false)
        reservacion.setStatus(false);
        reservacionRepository.saveAndFlush(reservacion);

        logger.info("Reservación con ID {} cancelada correctamente", idReservacion);
        return new ResponseEntity<>(new Message(reservacion, "Reservación cancelada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
