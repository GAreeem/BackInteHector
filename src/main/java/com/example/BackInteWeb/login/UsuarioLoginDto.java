package com.example.BackInteWeb.login;

import com.example.BackInteWeb.Rol.model.Rol;

public class UsuarioLoginDto {

    private Long idUser;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String email;
    private String telefono;
    private boolean status;
    private Rol rol;

    public UsuarioLoginDto(Long idUser, String nombre, String apellidoP, String apellidoM,
                      String email, String telefono, boolean status, Rol rol) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.email = email;
        this.telefono = telefono;
        this.status = status;
        this.rol = rol;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
