package com.example.BackInteWeb.Reservacion.control;

import com.example.BackInteWeb.Reservacion.model.ReservacionDTO;
import com.example.BackInteWeb.utils.Message;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservacion")
public class ReservacionController {

    private final ReservacionServices reservacionServices;

    @Autowired
    public ReservacionController(ReservacionServices reservacionServices) {
        this.reservacionServices = reservacionServices;
    }

    @GetMapping("/")
    public ResponseEntity<Message> findAll() {
        return reservacionServices.listaReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return reservacionServices.ReservacionPorId(id);
    }

    @PostMapping("/")
    public ResponseEntity<Message> crear(@Validated({ReservacionDTO.Register.class, Default.class}) @RequestBody ReservacionDTO reservacionDTO) {
        return reservacionServices.crearReservacion(reservacionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizar(@Validated({ReservacionDTO.Register.class, Default.class}) @RequestBody ReservacionDTO reservacionDTO) {
        return reservacionServices.actualizarReservacion(reservacionDTO);
    }
}
