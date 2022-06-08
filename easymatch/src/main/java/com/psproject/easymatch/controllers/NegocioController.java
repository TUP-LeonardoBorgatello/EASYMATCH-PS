package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.dtos.JugadorResponseDTO;
import com.psproject.easymatch.dtos.NegocioRequestDTO;
import com.psproject.easymatch.dtos.NegocioResponseDTO;
import com.psproject.easymatch.services.JugadorService;
import com.psproject.easymatch.services.NegocioService;
import com.psproject.easymatch.services.ServiceResponse;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/easymatch")
public class NegocioController {

    @Autowired
    NegocioService negocioService;

    @CrossOrigin(origins = "*")
    @GetMapping("/negocios")
    public List<NegocioResponseDTO> getNegocio() throws Exception {
        return negocioService.findNegocio();
    }

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

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PutMapping("negocio/update")
    public ResponseEntity<?> updateNegocio(@RequestBody NegocioRequestDTO negocio) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se actualizó correctamente el negocio");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se encontró el negocio con ese cuil o los valores ingresados no corresponden");
        try {
            negocioService.updateNegocio(negocio);
        } catch (Exception e) {
            return  new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

    @CrossOrigin(origins = "*")
    @PostMapping("negocio/delete")
    public ResponseEntity<?> deleteNegocio(@RequestBody NegocioRequestDTO negocio) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se eliminó correctamente el negocio");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se puede eliminar el negocio, verifique si el cuil existe");
        try{
            negocioService.deleteNegocioByCuil(negocio);
        } catch (Exception e){
            return  new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
