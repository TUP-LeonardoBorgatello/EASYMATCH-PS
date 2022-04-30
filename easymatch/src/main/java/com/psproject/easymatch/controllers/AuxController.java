package com.psproject.easymatch.controllers;

import com.psproject.easymatch.models.Ciudad;
import com.psproject.easymatch.models.FormaPago;
import com.psproject.easymatch.models.TipoDoc;
import com.psproject.easymatch.services.AuxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
