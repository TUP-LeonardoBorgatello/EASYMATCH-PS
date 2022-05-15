package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NegocioResponseDTO {
    private String nombre;

    private String domicilio;

    private String email;

    private String ciudad;

    private int cuil;
}
