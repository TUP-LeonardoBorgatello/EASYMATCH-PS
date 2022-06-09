package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReservaNegocioResponseDTO {
    private long idReserva;

    private String cancha;

    private String tipoCancha;

    private String jugador;

    private String fechaHora;

    private String fechaReserva;

    private String estado;
}