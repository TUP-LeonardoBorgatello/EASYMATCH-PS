package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.services.JugadorService;
import com.psproject.easymatch.services.ServiceResponse;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/easymatch")
public class JugadorController {

    @Autowired
    JugadorService jugadorService;

/*    @CrossOrigin(origins = "*")
    @GetMapping("/jugadores")
    public List<JugadorRespondeDTO> gettAllJugadores() {
        return jugadorService.findAllJugadores();
    }*/

    @CrossOrigin(origins = "*")
    @PostMapping("/add/jugador")
    public ResponseEntity<?> addJugador(@NotNull @RequestBody JugadorRequestDTO nuevoJugador) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se agregó correctamente el jugador.");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "Jugador existente, o seleccione una ciudad y tipo de documento válido.");
        if (nuevoJugador != null) {
            try {
                jugadorService.addJugador(nuevoJugador);
            } catch (Exception e) {
                return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
