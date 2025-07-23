package com.example.BackInteWeb.CategoriaServicio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_categorias")
public class CategoriaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaServicio;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    public CategoriaServicio() {}

    public CategoriaServicio(String nombre, String descripcion, boolean status) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.status = status;
    }

    public CategoriaServicio(Long idCategoriaServicio, String nombre, String descripcion, boolean status) {
        this.idCategoriaServicio = idCategoriaServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.status = status;
    }

    public Long getIdCategoriaServicio() {
        return idCategoriaServicio;
    }

    public void setIdCategoriaServicio(Long idCategoriaServicio) {
        this.idCategoriaServicio = idCategoriaServicio;
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
}
