package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NegocioRequestDTO {

    private String nombre;

    private String domicilio;

    private String email;

    private long id_ciudad;

    private int cuil;

    private String contraseña;
}
