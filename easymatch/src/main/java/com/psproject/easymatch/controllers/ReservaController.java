package com.psproject.easymatch.controllers;

import com.psproject.easymatch.services.ReservaService;
import com.psproject.easymatch.services.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/easymatch")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @CrossOrigin(origins = "*")
    @GetMapping("/add/reserva")
    public ResponseEntity<?> addReserva() throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Reserva agregada con éxito");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se puedo agregar la Reserva");
        try {
            reservaService.addReserva();
        } catch (Exception e) {
            return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
