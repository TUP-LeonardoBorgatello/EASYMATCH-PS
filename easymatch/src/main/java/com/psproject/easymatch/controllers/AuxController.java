package com.psproject.easymatch.controllers;

import com.psproject.easymatch.dtos.CantReserXFechaGraficoDTO;
import com.psproject.easymatch.dtos.CantReservasXFechaDTO;
import com.psproject.easymatch.dtos.MontoXReservaConfirmadaDTO;
import com.psproject.easymatch.dtos.Reporte1ResponseDTO;
import com.psproject.easymatch.models.Ciudad;
import com.psproject.easymatch.models.FormaPago;
import com.psproject.easymatch.models.TipoCancha;
import com.psproject.easymatch.models.TipoDoc;
import com.psproject.easymatch.services.AuxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/easymatch")
public class AuxController {

    @Autowired
    AuxService auxService;

    @CrossOrigin(origins = "*")
    @GetMapping("/tiposdocumentos")
    public List<TipoDoc> getAllTipoDoc() {
        return auxService.findAllTipoDoc();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ciudades")
    public List<Ciudad> getAllCiudad() {
        return auxService.findAllCiudad();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/metodopago")
    public List<FormaPago> getAllMetodoPago() {
        return auxService.findAllMetodoPago();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/tipocancha")
    public List<TipoCancha> getAllTipoCanchas() {
        return auxService.findAllTipoCanchas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/reporte1/{mes}")
    public Reporte1ResponseDTO getCanchaMasReservada(@PathVariable long mes) {
        return auxService.findCanchaMasReservada(mes);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/reporte2/{fecha1}/{fecha2}/{estado}")
    public CantReservasXFechaDTO getReservaXFechaEstado(@PathVariable String fecha1, @PathVariable String fecha2, @PathVariable long estado) {
        return auxService.findCantReservasEntreFechas(fecha1, fecha2, estado);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/reporte3/{fecha1}/{fecha2}")
    public List<CantReserXFechaGraficoDTO> getReservaXFechas(@PathVariable String fecha1, @PathVariable String fecha2) {
        return auxService.findReservasXFechas(fecha1, fecha2);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/reporte4")
    public List<MontoXReservaConfirmadaDTO> getMontoXReserva() {
        return auxService.findMontoXReserva();
    }

}
