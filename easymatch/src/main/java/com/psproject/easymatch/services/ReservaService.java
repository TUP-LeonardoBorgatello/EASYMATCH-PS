package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.DetalleReservaRequestDTO;
import com.psproject.easymatch.dtos.DetalleReservaResponseDTO;
import com.psproject.easymatch.models.*;
import com.psproject.easymatch.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReservaService {

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    LoginJugadoresRepository jugadoresRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    JugadorRepository jugadorRepository;

    @Autowired
    CanchaRepository canchaRepository;

    @Autowired
    DetalleReservaRepository detalleReservaRepository;

    @Autowired
    FormaPagoRepository formaPagoRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    DetalleTicketRepository detalleTicketRepository;

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

            reservaRepository.save(reserva);
        } else {
            throw new Exception("Error al agregar reserva, valor nulo como Id de Jugador.");
        }
    }

    public void addDetalleReserva(DetalleReservaRequestDTO detalleReservaRequestDTO) throws Exception {
        DetalleReserva detalleReserva = new DetalleReserva();
        Long lastReservaId = reservaRepository.lastReservaId();
        if (lastReservaId != null) {
            Cancha c = canchaRepository.findById(detalleReservaRequestDTO.getId_cancha()).orElseThrow();
            List<DetalleReserva> detalleReservas = detalleReservaRepository
                    .searchDetReservaByCanchaFechaHora(detalleReservaRequestDTO.getFecha_reserva(),
                            detalleReservaRequestDTO.getId_cancha(), detalleReservaRequestDTO.getHorario_inicial());
            if (detalleReservas.isEmpty()) {
                Reserva r = reservaRepository.findById(lastReservaId).orElseThrow();
                Estado e = estadoRepository.findById(2L).orElseThrow();
                r.setEstado(e);
                detalleReserva.setReserva(r);
                detalleReserva.setFechaReserva(detalleReservaRequestDTO
                        .fechaParseada(detalleReservaRequestDTO.getFecha_reserva()));
                detalleReserva.setCancha(c);
                detalleReserva.setHorarioInicial(detalleReservaRequestDTO.getHorario_inicial());

                detalleReservaRepository.save(detalleReserva);
                // canchaRepository.updateEstadoToFalse(detalleReservaRequestDTO.getId_cancha());
            } else {
                throw new Exception("Error al agregar detalle de reserva, cancha reservada.");
            }
        } else {
            throw new Exception("Error al agregar detalle de reserva, valor nulo como Id de Reserva.");
        }
    }

    public List<DetalleReservaResponseDTO> findDetalleReserva() {
        Long lasIdReserva = reservaRepository.lastReservaId();
        List<DetalleReserva> detalleReservas = detalleReservaRepository.findDetalleByReservaId(lasIdReserva);
        List<DetalleReservaResponseDTO> detalleReservaResponseDTOS = new ArrayList<>();

        for (DetalleReserva d : detalleReservas) {
            DetalleReservaResponseDTO de = new DetalleReservaResponseDTO();
            Cancha cancha = canchaRepository.findById(d.getCancha().getIdCancha()).orElseThrow();
            String negocio = cancha.getNegocio().getNombre();
            String direccion = cancha.getNegocio().getDomicilio();

            de.setDireccion(direccion);
            de.setNegocio(negocio);
            de.setCancha(cancha.getDescripcion());
            de.setTipoCancha(cancha.getTipoCancha().getDescripcion());
            de.setFechaHora(d.getFechaReserva() + " Hora: " + d.getHorarioInicial());

            detalleReservaResponseDTOS.add(de);
        }
        return detalleReservaResponseDTOS;
    }

    public void addTicket(long idFormaPago) throws Exception {
        Ticket ticket = new Ticket();
        ticket.setFecha(LocalDate.now());
        FormaPago f = formaPagoRepository.findById(idFormaPago).orElseThrow();
        ticket.setFormaPago(f);

        ticketRepository.save(ticket);
        //TODO Continuar con el detalle del ticket. Falta hacer el metodo en el front para guardar el ticket, y luego la impresion del ticket.

    }

    public void addDetalleTicket() throws Exception {
        DetalleTicket detalleTicket = new DetalleTicket();
        long lastReservaId = reservaRepository.lastReservaId();
        List<DetalleReserva> detalleReservas = detalleReservaRepository.findDetalleByReservaId(lastReservaId);
        double montoFinal = 0;
        for (DetalleReserva d : detalleReservas) {
            montoFinal += d.getCancha().getPrecioCancha();
        }
        detalleTicket.setMonto(montoFinal);
        Long lastTicketId = ticketRepository.lastTicketId();
        Ticket ticket = ticketRepository.findById(lastTicketId).orElseThrow();
        detalleTicket.setTicket(ticket);
        Reserva reserva = reservaRepository.findById(lastReservaId).orElseThrow();
        detalleTicket.setReserva(reserva);

        detalleTicketRepository.save(detalleTicket);
        reservaRepository.updateEstadoToFinalizado(lastReservaId);
    }
}
