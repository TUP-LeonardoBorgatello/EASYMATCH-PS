package com.psproject.easymatch.utils;

import com.psproject.easymatch.dtos.NegocioResponseDTO;
import com.psproject.easymatch.models.Negocio;

public class NegocioMapper {
    public static NegocioResponseDTO toDTO(Negocio negocio) {

        NegocioResponseDTO negocioResponseDTO = new NegocioResponseDTO();

        negocioResponseDTO.setCuil(negocio.getCuil());
        negocioResponseDTO.setDomicilio(negocio.getDomicilio());
        negocioResponseDTO.setCiudad(negocio.getCiudad().getDescripcion());
        negocioResponseDTO.setEmail(negocio.getEmail());
        negocioResponseDTO.setNombre(negocio.getNombre());

        return negocioResponseDTO;
    }
}
