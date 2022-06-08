package com.psproject.easymatch.utils;

import com.psproject.easymatch.dtos.CanchasResponseDTO;
import com.psproject.easymatch.dtos.JugadorResponseDTO;
import com.psproject.easymatch.models.Cancha;
import com.psproject.easymatch.models.Jugador;

public class CanchaMapper {
    public static CanchasResponseDTO toDTO(Cancha cancha) {

        CanchasResponseDTO canchasResponseDTO = new CanchasResponseDTO();

        canchasResponseDTO.setIdCancha(cancha.getIdCancha());
        canchasResponseDTO.setTipoCancha(cancha.getTipoCancha().getDescripcion());
        canchasResponseDTO.setPrecioCancha(cancha.getPrecioCancha());
        canchasResponseDTO.setDescripcion(cancha.getDescripcion());
        canchasResponseDTO.setNombreNegocio(cancha.getNegocio().getNombre());
        if (cancha.getEstado()) {
            canchasResponseDTO.setEstado("Activa");
        } else {
            canchasResponseDTO.setEstado("Inactiva");
        }
        return canchasResponseDTO;
    }
}
