package com.example.BackInteWeb.bitacora.control;

import com.example.BackInteWeb.bitacora.model.Bitacora;
import com.example.BackInteWeb.bitacora.model.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    public void registrar(String usuario, String metodoHttp, String endpoint, String descripcion) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsuario(usuario);
        bitacora.setMetodoHttp(metodoHttp);
        bitacora.setEndpoint(endpoint);
        bitacora.setDescripcion(descripcion);
        bitacora.setFechaHora(LocalDateTime.now());
        bitacoraRepository.save(bitacora);
    }

    public List<Bitacora> obtenerTodas() {
        return bitacoraRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaHora")); // ordenadas por fecha
    }

    public void eliminarTodos() {
        bitacoraRepository.deleteAll();
    }
}

