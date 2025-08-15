package com.example.BackInteWeb.Usuario.control;

import com.example.BackInteWeb.Rol.model.Rol;
import com.example.BackInteWeb.Rol.model.RolRepository;
import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioDTO;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
import com.example.BackInteWeb.utils.Message;
import com.example.BackInteWeb.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Transactional
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> listar (){
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios");
            return new ResponseEntity<>(new Message(null, "No se encontraron usuarios", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        logger.info("Usuarios encontrados correctamente");
        return new ResponseEntity<>(new Message(usuarios, "Lista de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> UsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            logger.info("Usuario con ID {} encontrado", id);
            return new ResponseEntity<>(new Message(usuario.get(), "Usuario encontrado", TypesResponse.SUCCESS), HttpStatus.OK);
        }
        logger.warn("Usuario con ID {} no encontrado", id);
        return new ResponseEntity<>(new Message(null, "Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> crear(UsuarioDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Correo ya registrado: {}", dto.getEmail());
            return new ResponseEntity<>(new Message("El correo ya está registrado", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 8 || dto.getPassword().length() > 16) {
            logger.warn("La contraseña debe tener minimo 8 caracteres o maximo 16 caracteres");
            return new ResponseEntity<>(new Message("La contraseña debe tener minimo 8 caracteres o maximo 16 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Rol> rol = rolRepository.findById(dto.getIdRol());
        if (rol.isEmpty()) {
            logger.warn("Rol con ID {} no encontrado", dto.getIdRol());
            return new ResponseEntity<>(new Message("Rol no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(
                dto.getNombre(),
                dto.getApellidoP(),
                dto.getApellidoM(),
                dto.getEmail(),
                dto.getTelefono(),
                new BCryptPasswordEncoder().encode(dto.getPassword()), // Encriptar la contraseña
                true,
                rol.get()
        );

        usuario = usuarioRepository.saveAndFlush(usuario);
        logger.info("Usuario registrado exitosamente con ID {}", usuario.getIdUser());
        return new ResponseEntity<>(new Message(usuario, "Usuario registrado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> actualizar(UsuarioDTO dto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dto.getIdUser());

        if (optionalUsuario.isEmpty()) {
            logger.warn("Usuario con ID {} no encontrado", dto.getIdUser());
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();

        if (!usuario.getEmail().equals(dto.getEmail())) {
            logger.warn("Intento de cambiar el correo de un usuario con ID {}", dto.getIdUser());
            return new ResponseEntity<>(new Message("No está permitido cambiar el correo electrónico", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Rol> rol = rolRepository.findById(dto.getIdRol());
        if (rol.isEmpty()) {
            logger.warn("Rol con ID {} no encontrado", dto.getIdRol());
            return new ResponseEntity<>(new Message("Rol no encontrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellidoP(dto.getApellidoP());
        usuario.setApellidoM(dto.getApellidoM());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(rol.get());

        // Solo actualizar password si viene en el DTO y no está vacío
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }

        usuarioRepository.saveAndFlush(usuario);
        logger.info("Usuario con ID {} actualizado correctamente", dto.getIdUser());
        return new ResponseEntity<>(new Message(usuario, "Usuario actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    public boolean eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setStatus(false);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean restaurar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setStatus(true);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
