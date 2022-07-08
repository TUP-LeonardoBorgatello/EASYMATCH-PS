package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.NegocioRequestDTO;
import com.psproject.easymatch.dtos.NegocioResponseDTO;
import com.psproject.easymatch.models.Ciudad;
import com.psproject.easymatch.models.LoginNegocio;
import com.psproject.easymatch.models.Negocio;
import com.psproject.easymatch.repositories.CiudadRepository;
import com.psproject.easymatch.repositories.LoginNegociosRepository;
import com.psproject.easymatch.repositories.NegocioRepository;
import com.psproject.easymatch.utils.NegocioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NegocioService implements iNegocioService {

    @Autowired
    NegocioRepository negocioRepository;

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    LoginNegociosRepository loginNegociosRepository;

    public List<NegocioResponseDTO> findNegocio() {

        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio loginNegocio = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = loginNegocio.getNegocio();
        ArrayList<Negocio> negocios = new ArrayList<>();
        negocios.add(negocio);

        return negocios.stream().map(NegocioMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addNegocio(NegocioRequestDTO negocioRequestDTO) throws Exception {
        Negocio negocio = negocioRepository.searchByCuil1(negocioRequestDTO.getCuil());
        if (negocioRepository.existsByCuil(negocioRequestDTO.getCuil())) {
            if (negocio.getEstado()) {
                throw new Exception("El negocio ya existe.");
            } else {
                negocioRepository.updateNegocioToTrue(negocio.getIdNegocio());
            }
        } else if (negocioRequestDTO.getNombre() == "" || negocioRequestDTO.getDomicilio() == ""
                || negocioRequestDTO.getEmail() == "" || negocioRequestDTO.getContraseña() == "" || negocioRequestDTO.getCuil() <= 0) {
            throw new Exception("Valores nulos");
        } else {
            Ciudad ciudad = ciudadRepository.findById(negocioRequestDTO.getId_ciudad()).orElseThrow();
            Negocio n = new Negocio();
            n.setNombre(negocioRequestDTO.getNombre());
            n.setEstado(true);
            n.setDomicilio(negocioRequestDTO.getDomicilio());
            n.setEmail(negocioRequestDTO.getEmail());
            n.setCuil(negocioRequestDTO.getCuil());
            n.setCiudad(ciudad);
            n.setContraseña(negocioRequestDTO.getContraseña());

            negocioRepository.save(n);
        }
    }

    public void updateNegocio(NegocioRequestDTO negocioRequestDTO) throws Exception {
        if (negocioRequestDTO.getCuil() != 0) {
            try {
                Negocio negocio = negocioRepository.searchByCuil1(negocioRequestDTO.getCuil());
                if (negocio != null) {
                    Ciudad ciudad = ciudadRepository.findById(negocioRequestDTO.getId_ciudad()).orElseThrow();
                    negocio.setCiudad(ciudad);
                    negocio.setContraseña(negocioRequestDTO.getContraseña());
                    negocio.setDomicilio(negocioRequestDTO.getDomicilio());
                    negocio.setEmail(negocioRequestDTO.getEmail());
                    negocio.setNombre(negocioRequestDTO.getNombre());
                    negocioRepository.save(negocio);
                }
            } catch (Exception e) {
                throw new Exception("No se pudo agregar, campos nulos.");
            }
        }
    }

    public void deleteNegocioByCuil(NegocioRequestDTO negocioRequestDTO) throws Exception {
        if (negocioRequestDTO.getCuil() != 0) {
            try {
                Negocio negocio = negocioRepository.searchByCuil1(negocioRequestDTO.getCuil());
                if (negocio != null) {
                    negocioRepository.updateNegocioToFalse(negocio.getIdNegocio());
                }
            } catch (Exception e) {
                throw new Exception("No se pudo eliminar el negocio, campos nulos.");
            }
        }
    }
}
