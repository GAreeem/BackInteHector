package com.example.BackInteWeb.CategoriaServicio.model;

import com.example.BackInteWeb.Servicio.model.ServicioDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ServicioRegistroRequest {
    @NotBlank(groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class}, message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras y espacios", groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class})
    private String nombre;
    @NotBlank(message = "La descripción no puede estar vacía", groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ,.]+$", message = "La descripción solo debe contener letras, comas, puntos y espacios", groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class})
    private String descripcion;
    @NotNull(message = "El precio no puede ser nulo", groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class})
    @Positive(message = "El precio debe ser mayor a 0", groups = {ServicioDTO.Register.class, ServicioDTO.Modify.class})
    private double precio;
    @NotNull(message = "El ID de categoria es obligatorio", groups = {ServicioDTO.Register.class})
    private Long idCategoria;

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
}
