package com.example.BackInteWeb.Servicio.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdServicioNot(String nombre, Long idServicio);
}
