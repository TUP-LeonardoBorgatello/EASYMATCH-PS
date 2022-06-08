package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CanchasRequestDTO {

    private long idCancha;

    private String descripcion;

    private double precio_cancha;

    private long id_tipo_cancha;
}
