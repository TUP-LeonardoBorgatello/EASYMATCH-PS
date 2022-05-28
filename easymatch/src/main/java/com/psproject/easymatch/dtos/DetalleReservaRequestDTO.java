package com.psproject.easymatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReservaRequestDTO {

    private String fecha_reserva;

    private int horario_inicial;

    private long id_cancha;

    public LocalDate fechaParseada(String fecha){
        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha_reserva, JEFormatter);
    }
}
