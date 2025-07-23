package com.example.BackInteWeb.Usuario.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UsuarioDTO {

    @NotNull(message = "El ID del usuario no puede ser nulo", groups = {Modify.class, ChangeStatus.class})
    private Long idUser;

    @NotBlank(message = "El nombre no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String nombre;

    @NotBlank(message = "El apellido paterno no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido paterno solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String apellidoP;

    @NotBlank(message = "El apellido materno no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido materno solo debe contener letras y espacios", groups = {Register.class, Modify.class})
    private String apellidoM;

    @NotBlank(message = "El correo electrónico no puede estar vacío", groups = {Register.class, Modify.class})
    @Email(message = "Formato de correo electrónico inválido", groups = {Register.class, Modify.class})
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Correo electrónico inválido. Debe tener un formato válido",
            groups = {Register.class, Modify.class}
    )
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío", groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos", groups = {Register.class, Modify.class})
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía", groups = {Register.class})
    private String password;

    @NotNull(message = "El ID del rol es obligatorio", groups = {Register.class, Modify.class})
    private Long idRol;

    public UsuarioDTO() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}
