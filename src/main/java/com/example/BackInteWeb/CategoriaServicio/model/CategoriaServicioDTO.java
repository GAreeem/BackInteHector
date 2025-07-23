package com.example.BackInteWeb.CategoriaServicio.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CategoriaServicioDTO {

    @NotNull(groups = {Modify.class,ChangeStatus.class},message = "El id no puede ser nulo")
    private Long idCategoriaServicio;

    @NotBlank(groups = {Register.class,Modify.class},message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(groups = {Register.class,Modify.class},message = "La descripción no puede estar vacía")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ,.]+$", message = "La descripción solo debe contener letras, espacios, comas y puntos")
    private String descripcion;

    public CategoriaServicioDTO() {
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

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
}
