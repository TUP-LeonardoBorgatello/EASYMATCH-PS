package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDecargaResponseDTO {
    private long numeroTicket;

    private String nombreJugador;

    private String apellidoJugador;

    private String mailJugador;

    private String fechaTicket;

    private String formaPago;

    private double montoTicket;
}
