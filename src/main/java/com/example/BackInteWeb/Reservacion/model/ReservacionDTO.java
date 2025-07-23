package com.example.BackInteWeb.Reservacion.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class ReservacionDTO {

    @NotNull(groups = {Modify.class,ChangeStatus.class}, message = "El id no puede ser nulo")
    private Long idReservacion;

    @NotBlank(message = "El nombre no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ,.]+$", message = "La descripción solo debe contener letras, comas, puntos y espacios", groups = {Register.class, Modify.class})
    private String descripcion;

    @NotNull(message = "El ID del servicio es obligatorio", groups = {Register.class})
    private Long idServicio;

    @NotNull(message = "El ID del usuario es obligatorio", groups = {Register.class})
    private Long idUsuario;

    private LocalDateTime reservationDate;

    public ReservacionDTO() {
    }

    public Long getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Long idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
}
