package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.DetalleReservaRequestDTO;
import com.psproject.easymatch.dtos.DetalleReservaResponseDTO;
import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.models.TipoDoc;
import com.psproject.easymatch.services.ReservaService;
import com.psproject.easymatch.services.ServiceResponse;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin(origins = "*")
    @PostMapping("/add/detalle/reserva")
    public ResponseEntity<?> addDetalleReserva(@NotNull @RequestBody DetalleReservaRequestDTO detalleReservaRequestDTO) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Detalle de Reserva agregada con éxito");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se puedo agregar el detalle de la Reserva");
        try {
            reservaService.addDetalleReserva(detalleReservaRequestDTO);
        } catch (Exception e) {
            return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cargartablaticket")
    public List<DetalleReservaResponseDTO> getDetalleReserva() {
        return reservaService.findDetalleReserva();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/add/ticket/{id_forma_pago}")
    public ResponseEntity<?> addTicket(@PathVariable long id_forma_pago) throws Exception {
        ServiceResponse<?> response = new ServiceResponse<>("success", "Ticket agregado con éxito");
        ServiceResponse<?> response2 = new ServiceResponse<>("error", "No se puedo agregar el ticket");
        try {
            reservaService.addTicket(id_forma_pago);
            Thread.sleep(1000);
            reservaService.addDetalleTicket();
        } catch (Exception e) {
            return new ResponseEntity<Object>(response2, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
