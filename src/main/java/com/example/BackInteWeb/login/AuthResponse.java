package com.example.BackInteWeb.login;

public class AuthResponse {
    private String token;
    private UsuarioLoginDto usuario;

    public AuthResponse(String token, UsuarioLoginDto usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public UsuarioLoginDto getUsuario() {
        return usuario;
    }
}

