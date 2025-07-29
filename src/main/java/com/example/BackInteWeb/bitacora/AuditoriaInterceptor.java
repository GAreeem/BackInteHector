package com.example.BackInteWeb.bitacora;

import com.example.BackInteWeb.bitacora.control.BitacoraService;
import com.example.BackInteWeb.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String metodo = request.getMethod(); // GET, POST, etc.
        String uri = request.getRequestURI();

        String authHeader = request.getHeader("Authorization");
        String usuario = "Desconocido";

        // Extraer usuario desde el token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                usuario = jwtUtil.getEmailFromToken(token); // o getUsernameFromToken si cambias el método
            }
        }

        if (uri.contains("/bitacora") || uri.contains("/actuator")) {
            return true; // no registrar
        }

        String descripcion;

        if (metodo.equals("POST") && uri.equals("/auth/login")) {
            descripcion = "Inicio de sesión";
        } else if (metodo.equals("POST")) {
            descripcion = "Creación de recurso";
        } else if (metodo.equals("PUT")) {
            descripcion = "Actualización de recurso";
        } else if (metodo.equals("DELETE")) {
            descripcion = "Eliminación de recurso";
        } else if (metodo.equals("GET")) {
            descripcion = "Consulta de recurso";
        } else {
            descripcion = "Operación no identificada";
        }

        // Solo registrar POST, PUT, DELETE (o ajusta según tu lógica)
        if (!metodo.equals("GET")) {
            bitacoraService.registrar(usuario, metodo, uri, descripcion);
        }

        return true;
    }
}
