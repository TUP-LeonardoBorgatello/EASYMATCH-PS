package com.psproject.easymatch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", length = 10)
    private long idReserva;
    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne()
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne()
    @JoinColumn(name = "id_negocio", nullable = false)
    private Negocio negocio;

    @ManyToOne()
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;
}
