package com.psproject.easymatch.utils;

import com.psproject.easymatch.dtos.JugadorResponseDTO;
import com.psproject.easymatch.models.Jugador;

public class JugadorMapper {
    public static JugadorResponseDTO toDTO(Jugador jugador) {

        JugadorResponseDTO jugadorResponseDTO = new JugadorResponseDTO();

        jugadorResponseDTO.setNombre(jugador.getNombre());
        jugadorResponseDTO.setApellido(jugador.getApellido());
        jugadorResponseDTO.setDocumento(jugador.getDocumento());
        jugadorResponseDTO.setCiudad(jugador.getCiudad().getDescripcion());
        jugadorResponseDTO.setEmail(jugador.getEmail());
        jugadorResponseDTO.setDomicilio(jugador.getDomicilio());

        return jugadorResponseDTO;
    }
}
