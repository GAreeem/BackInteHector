package com.example.BackInteWeb.Rol.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RolDTO {

    @NotNull(message = "El ID no puede ser nulo", groups = {Modify.class, ChangeStatus.class})
    private Long idRol;

    @NotBlank(message = "El nombre del rol no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El rol solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String rol;

    public RolDTO() {}

    public RolDTO(Long idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}
