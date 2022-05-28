package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CanchasReservasResponseDTO {
    private long id_cancha;

    private String descripcion;

    private String nombrePredio;

    private String ubicacion;

    private String ciudad;

    private String tipoCancha;

    private double precio;
}
