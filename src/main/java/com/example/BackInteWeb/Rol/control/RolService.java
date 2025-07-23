package com.example.BackInteWeb.Rol.control;

import com.example.BackInteWeb.Rol.model.Rol;
import com.example.BackInteWeb.Rol.model.RolRepository;
import com.example.BackInteWeb.utils.Message;
import com.example.BackInteWeb.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RolService {

    private static final Logger logger = LoggerFactory.getLogger(RolService.class);

    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> lista() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()) {
            logger.warn("No hay roles registrados");
            return new ResponseEntity<>(
                    new Message(null, "No se encontraron roles", TypesResponse.WARNING),
                    HttpStatus.NOT_FOUND
            );
        }
        logger.info("Lista de roles obtenida correctamente");
        return new ResponseEntity<>(
                new Message(roles, "Lista de roles", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> RolPorId(Long id) {
        Optional<Rol> rolOptional = rolRepository.findById(id);
        if (rolOptional.isPresent()) {
            logger.info("Rol con ID {} encontrado", id);
            return new ResponseEntity<>(
                    new Message(rolOptional.get(), "Rol encontrado", TypesResponse.SUCCESS),
                    HttpStatus.OK
            );
        }
        logger.warn("Rol con ID {} no encontrado", id);
        return new ResponseEntity<>(
                new Message(null, "Rol no encontrado", TypesResponse.WARNING),
                HttpStatus.NOT_FOUND
        );
    }
}
