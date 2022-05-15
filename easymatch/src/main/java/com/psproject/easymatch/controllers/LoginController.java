package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.LoginRequestDTO;
import com.psproject.easymatch.services.JugadorService;
import com.psproject.easymatch.services.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/easymatch")
public class LoginController {

    @Autowired
    JugadorService jugadorService;

    @CrossOrigin(origins = "*")
    @PostMapping("/usuarios/login")
    public ResponseEntity<?> loginUsuarios(@RequestBody LoginRequestDTO loginUsuario) throws Exception {
        String mensaje;
        ServiceResponse<?> response = new ServiceResponse<>("error", "No se encontró el usuario con ese documento o los valores ingresados no corresponden");
        try {
            jugadorService.login(loginUsuario);
            if (jugadorService.loginState(loginUsuario)) {
                mensaje = "Jugador logueado";
            } else {
                mensaje = "Negocio logueado";
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<Object>(new ServiceResponse<>("success", mensaje), HttpStatus.OK);
    }


}

