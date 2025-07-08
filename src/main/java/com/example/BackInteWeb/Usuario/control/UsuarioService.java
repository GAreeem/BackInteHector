package com.example.BackInteWeb.Usuario.control;

import com.example.BackInteWeb.Usuario.model.Usuario;
import com.example.BackInteWeb.Usuario.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario crear(Usuario usuario) {
        usuario.setStatus(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datos) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNombre(datos.getNombre());
            usuario.setApellidoP(datos.getApellidoP());
            usuario.setApellidoM(datos.getApellidoM());
            usuario.setEmail(datos.getEmail());
            usuario.setTelefono(datos.getTelefono());
            usuario.setPassword(datos.getPassword());
            usuario.setRol(datos.getRol());
            return usuarioRepository.save(usuario);
        }
        return null;
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
