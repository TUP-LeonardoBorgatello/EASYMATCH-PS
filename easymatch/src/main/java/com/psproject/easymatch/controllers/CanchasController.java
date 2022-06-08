package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.*;
import com.psproject.easymatch.services.AuxService;
import com.psproject.easymatch.services.ServiceResponse;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/easymatch")
public class CanchasController {

    @Autowired
    AuxService auxService;

    @CrossOrigin(origins = "*")
    @GetMapping("/canchas/reservas")
    public List<CanchasReservasResponseDTO> getCanchasDescripcion() {
        return auxService.findCanchasNoReservadas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/canchas/reservas/{horario_inicial}/{fecha_reserva}")
    public List<CanchasReservasResponseDTO> getCanchasDescripcion2(@PathVariable int horario_inicial,
                                                                   @PathVariable String fecha_reserva) {
        return auxService.findCanchasNoReservadas2(horario_inicial, fecha_reserva);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/canchas")
    public List<CanchasResponseDTO> getAllCanchas() throws Exception {
        return auxService.findAllcanchas();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/canchas")
    public ResponseEntity<?> addCanchas(@NotNull @RequestBody CanchasRequestDTO nuevaCancha) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se agregó correctamente la cancha.");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "Error al intentar agregar cancha.");
        if (nuevaCancha != null) {
            try {
                auxService.addCanchas(nuevaCancha);
            } catch (Exception e) {
                return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PutMapping("cancha/update")
    public ResponseEntity<?> updateCancha(@RequestBody CanchasRequestDTO cancha) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se actualizó correctamente la cancha");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se encontró la cancha");
        try {
            auxService.updateCancha(cancha);
        } catch (Exception e) {
            return  new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

    @CrossOrigin(origins = "*")
    @PostMapping("cancha/status")
    public ResponseEntity<?> changeStatusCanchas(@RequestBody CanchasRequestDTO canchasRequestDTO) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Se cambio el estado correctamente");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se puede cambiar el estado.");
        try{
            auxService.changeStatusCancha(canchasRequestDTO);
        } catch (Exception e){
            return  new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
