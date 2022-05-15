package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.dtos.NegocioRequestDTO;
import com.psproject.easymatch.services.JugadorService;
import com.psproject.easymatch.services.NegocioService;
import com.psproject.easymatch.services.ServiceResponse;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/easymatch")
public class NegocioController {

    @Autowired
    NegocioService negocioService;

    @CrossOrigin(origins = "*")
    @PostMapping("/add/negocio")
    public ResponseEntity<?> addNegocio(@NotNull @RequestBody NegocioRequestDTO nuevoNegocio) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se agregó correctamente el negocio.");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "Negocio existente, o seleccione una ciudad y tipo de documento válido.");
        if (nuevoNegocio != null) {
            try {
                negocioService.addNegocio(nuevoNegocio);
            } catch (Exception e) {
                return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
