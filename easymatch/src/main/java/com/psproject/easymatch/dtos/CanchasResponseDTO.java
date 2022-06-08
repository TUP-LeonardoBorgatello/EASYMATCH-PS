package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CanchasResponseDTO {

    private long idCancha;

    private String descripcion;

    private double precioCancha;

    private String nombreNegocio;

    private String tipoCancha;

    private String estado;
}
