package com.example.BackInteWeb.CategoriaServicio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_categorias")
public class CategoriaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaServicio;

    @Column(name = "nommbre", columnDefinition = "VARCHAR(100)")
    private String nommbre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    public CategoriaServicio() {}

    public CategoriaServicio(Long idCategoriaServicio, String nommbre, String descripcion, boolean status) {
        this.idCategoriaServicio = idCategoriaServicio;
        this.nommbre = nommbre;
        this.descripcion = descripcion;
        this.status = status;
    }

    public CategoriaServicio(String nommbre, String descripcion, boolean status) {
        this.nommbre = nommbre;
        this.descripcion = descripcion;
        this.status = status;
    }

    public Long getIdCategoriaServicio() {
        return idCategoriaServicio;
    }

    public void setIdCategoriaServicio(Long idCategoriaServicio) {
        this.idCategoriaServicio = idCategoriaServicio;
    }

    public String getNommbre() {
        return nommbre;
    }

    public void setNommbre(String nommbre) {
        this.nommbre = nommbre;
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
