package com.example.BackInteWeb.Reservacion.model;

import com.example.BackInteWeb.Servicio.model.Servicio;
import com.example.BackInteWeb.Usuario.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservaciones")
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservacion;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Usuario usuario;

    @Column(name = "reservation_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime reservationDate;

    public Reservacion() {}

    public Reservacion(String nombre, String descripcion, boolean status, Servicio servicio, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.status = status;
        this.servicio = servicio;
        this.usuario = usuario;
        this.reservationDate = LocalDateTime.now();
    }
    public Reservacion(Long idReservacion, String nombre, String descripcion, boolean status, Servicio servicio, Usuario usuario) {
        this.idReservacion = idReservacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.status = status;
        this.servicio = servicio;
        this.usuario = usuario;
        this.reservationDate = LocalDateTime.now();
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
