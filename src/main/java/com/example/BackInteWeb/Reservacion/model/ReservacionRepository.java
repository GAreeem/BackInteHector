package com.example.BackInteWeb.Reservacion.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion,Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdReservacionNot(String nombre, Long idReservacion);

    List<Reservacion> findByUsuarioIdUser(Long idUser);
}
