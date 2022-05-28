package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.CanchasReservasResponseDTO;
import com.psproject.easymatch.services.AuxService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
