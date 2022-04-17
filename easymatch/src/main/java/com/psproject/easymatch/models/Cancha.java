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
@Table(name = "canchas")
public class Cancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancha", length = 10)
    private long idCancha;
    @Column(length = 30, nullable = false)
    private String descripcion;

    @ManyToOne()
    @JoinColumn(name = "id_tipo_cancha", nullable = false)
    private TipoCancha tipoCancha;

    @ManyToOne()
    @JoinColumn(name = "id_negocio", nullable = false)
    private Negocio negocio;
}
