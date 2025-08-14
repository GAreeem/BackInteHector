package com.example.BackInteWeb.Servicio.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ServicioDTO {

    @NotNull(groups = {Modify.class, ChangeStatus.class},message = "El id no puede ser nulo")
    private Long idServicio;

    @NotBlank(groups = {Register.class, Modify.class}, message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ,.]+$", message = "La descripción solo debe contener letras, comas, puntos y espacios", groups = {Register.class, Modify.class})
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo", groups = {Register.class, Modify.class})
    @Positive(message = "El precio debe ser mayor a 0", groups = {Register.class, Modify.class})
    private double precio;

    @NotNull(message = "El ID de categoria es obligatorio", groups = {Register.class})
    private Long idCategoria;

    @NotBlank(message = "La imagen debe tener una url valida", groups = {Register.class, Modify.class})
    private String imagenUrl;

    public ServicioDTO() {
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
}
