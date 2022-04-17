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
@Table(name = "negocios")
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_negocio", length = 10)
    private long idNegocio;
    @Column(length = 30, nullable = false)
    private String nombre;
    @Column(length = 30, nullable = false)
    private String cuil;
    @Column(length = 50, nullable = false)
    private String domicilio;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne()
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;
}
