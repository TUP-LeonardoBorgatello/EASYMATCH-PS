package com.psproject.easymatch.services;

import com.psproject.easymatch.models.Ciudad;
import com.psproject.easymatch.models.FormaPago;
import com.psproject.easymatch.models.TipoDoc;
import com.psproject.easymatch.repositories.CiudadRepository;
import com.psproject.easymatch.repositories.FormaPagoRepository;
import com.psproject.easymatch.repositories.TipoDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuxService {

    @Autowired
    TipoDocRepository tipoDocRepository;
    @Autowired
    CiudadRepository ciudadRepository;
    @Autowired
    FormaPagoRepository formaPagoRepository;

    public List<TipoDoc> findAllTipoDoc() {
        List<TipoDoc> tipoDocs = tipoDocRepository.findAll();
        return tipoDocs;
    }

    public List<Ciudad> findAllCiudad() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        return ciudades;
    }

    public List<FormaPago> findAllMetodoPago() {
        List<FormaPago> pagos = formaPagoRepository.findAll();
        return pagos;
    }
}
