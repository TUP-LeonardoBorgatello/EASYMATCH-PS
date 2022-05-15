package com.psproject.easymatch.services;

import com.psproject.easymatch.models.Estado;
import com.psproject.easymatch.models.Jugador;
import com.psproject.easymatch.models.LoginJugadores;
import com.psproject.easymatch.models.Reserva;
import com.psproject.easymatch.repositories.EstadoRepository;
import com.psproject.easymatch.repositories.JugadorRepository;
import com.psproject.easymatch.repositories.LoginJugadoresRepository;
import com.psproject.easymatch.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class ReservaService {

    @Autowired
    ReservaRepository repository;

    @Autowired
    LoginJugadoresRepository jugadoresRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    JugadorRepository jugadorRepository;

    public void addReserva() throws Exception {
        Reserva reserva = new Reserva();
        Long lastIdJugadorLogued = jugadoresRepository.lastIdJugadorLogued();
        if (lastIdJugadorLogued != null) {
            //El estado 1 en la BD significa que está en Proceso.
            Estado e = estadoRepository.findById(1L).orElseThrow();
            reserva.setEstado(e);
            reserva.setFecha(LocalDate.now());
            LoginJugadores login = jugadoresRepository.findById(lastIdJugadorLogued).orElseThrow();
            Jugador j = jugadorRepository.findById(login.getJugador().getIdJugador()).orElseThrow();
            reserva.setJugador(j);

            repository.save(reserva);
        } else {
            throw new Exception("Error al agregar reserva, valor nulo como Id de Jugador.");
        }
    }
}
