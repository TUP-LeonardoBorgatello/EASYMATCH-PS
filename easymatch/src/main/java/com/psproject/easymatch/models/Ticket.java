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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", length = 10)
    private long idTicket;
    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne()
    @JoinColumn(name = "id_forma_pago", nullable = false)
    private FormaPago formaPago;
}
