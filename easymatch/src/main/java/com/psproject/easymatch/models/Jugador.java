package com.psproject.easymatch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jugadores")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador", length = 10)
    private long idJugador;
    @Column(length = 30, nullable = false)
    private String nombre;
    @Column(length = 30, nullable = false)
    private int documento;
    @Column(length = 30, nullable = false)
    private String apellido;
    @Column(length = 50, nullable = false)
    private String domicilio;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String contraseña;

    @ManyToOne()
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;

    @ManyToOne()
    @JoinColumn(name = "id_tipo_doc", nullable = false)
    private TipoDoc tipoDoc;
}
