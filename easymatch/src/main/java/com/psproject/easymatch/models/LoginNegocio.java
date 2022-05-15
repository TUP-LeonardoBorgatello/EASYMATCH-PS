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
@Table(name = "login_negocios")
public class LoginNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login_negocio", length = 10)
    private long idLoginNegocio;

    @ManyToOne()
    @JoinColumn(name = "id_negocio", nullable = false)
    private Negocio negocio;


}
