package com.example.BackInteWeb.Servicio.model;

import com.example.BackInteWeb.CategoriaServicio.model.CategoriaServicio;
import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(name = "nombre", columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "price", columnDefinition = "DECIMAL(10,2)")
    private double precio;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaServicio categoria;

    public Servicio() {}

    public Servicio(Long idServicio, String nombre, String descripcion, double precio, boolean status, CategoriaServicio categoria) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.status = status;
        this.categoria = categoria;
    }

    public Servicio(String nombre, String descripcion, double precio, boolean status, CategoriaServicio categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.status = status;
        this.categoria = categoria;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CategoriaServicio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaServicio categoria) {
        this.categoria = categoria;
    }
}
