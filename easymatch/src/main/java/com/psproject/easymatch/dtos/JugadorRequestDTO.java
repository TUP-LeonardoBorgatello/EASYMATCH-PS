package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JugadorRequestDTO {

    private String nombre;

    private String apellido;

    private String domicilio;

    private String email;

    private long id_ciudad;

    private long id_tipo_doc;

    private int documento;

    private String contraseña;
}
