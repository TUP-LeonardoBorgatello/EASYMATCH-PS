package com.psproject.easymatch.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "ciudades")
public class Ciudades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad", length = 10)
    private long id;
    @Column(length = 30, nullable = false)
    private String descripcion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ciudades(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ciudades() {
    }
}