package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReservaResponseDTO {

    private String cancha;

    private String tipoCancha;

    private String negocio;

    private String fechaHora;

    private String direccion;
}