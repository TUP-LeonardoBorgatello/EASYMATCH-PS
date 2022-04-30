package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.models.Ciudad;
import com.psproject.easymatch.models.Jugador;
import com.psproject.easymatch.models.TipoDoc;
import com.psproject.easymatch.repositories.CiudadRepository;
import com.psproject.easymatch.repositories.JugadorRepository;
import com.psproject.easymatch.repositories.TipoDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JugadorService implements iJugadorService{

    @Autowired
    JugadorRepository jugadorRepository;

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    TipoDocRepository tipoDocRepository;

    @Override
    public void addJugador(JugadorRequestDTO jugadorRequestDTO) throws Exception {

        if (jugadorRepository.existsByDocumento(jugadorRequestDTO.getDocumento())) {
            throw new Exception("El jugador ya existe.");
        } else {
            if (jugadorRequestDTO.getNombre() == "" || jugadorRequestDTO.getApellido() == "" || jugadorRequestDTO.getDomicilio() == ""
                    || jugadorRequestDTO.getEmail() == "" || jugadorRequestDTO.getDocumento() <= 0) {
                throw new Exception("Valores nulos");
            } else {
                Ciudad ciudad = ciudadRepository.findById(jugadorRequestDTO.getId_ciudad()).orElseThrow();
                TipoDoc tipoDoc = tipoDocRepository.findById(jugadorRequestDTO.getId_tipo_doc()).orElseThrow();
                Jugador j = new Jugador();
                j.setNombre(jugadorRequestDTO.getNombre());
                j.setEstado(true);
                j.setApellido(jugadorRequestDTO.getApellido());
                j.setDomicilio(jugadorRequestDTO.getDomicilio());
                j.setEmail(jugadorRequestDTO.getEmail());
                j.setDocumento(jugadorRequestDTO.getDocumento());
                j.setCiudad(ciudad);
                j.setTipoDoc(tipoDoc);
                j.setContraseña(jugadorRequestDTO.getContraseña());

                jugadorRepository.save(j);
            }
        }
    }
}
