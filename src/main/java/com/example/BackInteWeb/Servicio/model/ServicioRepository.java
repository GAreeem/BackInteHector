package com.example.BackInteWeb.Servicio.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdServicioNot(String nombre, Long idServicio);

    List<Servicio> findByCategoria_IdCategoriaServicioAndStatusTrue(Long idCategoria);

    List<Servicio> findByStatusTrue();
    List<Servicio> findByStatusFalse();

}
