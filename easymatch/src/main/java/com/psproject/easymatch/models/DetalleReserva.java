package com.psproject.easymatch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_reserva")
public class DetalleReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_reserva", length = 10)
    private long idDetalleReserva;
    @Column(nullable = false)
    private LocalDate fechaReserva;
    @Column(nullable = false)
    private int horarioInicial;
    @Column(nullable = false)
    private int horarioFinal;

    @ManyToOne()
    @JoinColumn(name = "id_cancha", nullable = false)
    private Cancha cancha;

    @ManyToOne()
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;
}
