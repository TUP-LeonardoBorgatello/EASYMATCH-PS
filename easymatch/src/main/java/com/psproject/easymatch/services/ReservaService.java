package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.*;
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

    @Autowired
    NegocioRepository negocioRepository;

    @Autowired
    LoginNegociosRepository loginNegociosRepository;

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
                Estado e = estadoRepository.findById(1L).orElseThrow();
                r.setEstado(e);
                detalleReserva.setReserva(r);
                detalleReserva.setFechaReserva(detalleReservaRequestDTO
                        .fechaParseada(detalleReservaRequestDTO.getFecha_reserva()));
                detalleReserva.setCancha(c);
                detalleReserva.setHorarioInicial(detalleReservaRequestDTO.getHorario_inicial());

                detalleReservaRepository.save(detalleReserva);
                reservaRepository.updateEstadoToFinalizado(lastReservaId);
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
        reservaRepository.updateEstadoToFacturado(lastReservaId);
    }

    public TicketDecargaResponseDTO findDetalleTicket() {
        Long lastIdTicket = ticketRepository.lastTicketId();
        Ticket ticket = ticketRepository.findById(lastIdTicket).orElseThrow();
        String fechaTicket = String.valueOf(ticket.getFecha());
        String formaPago = ticket.getFormaPago().getDescripcion();
        List<DetalleTicket> detalleTickets = detalleTicketRepository.findDetalleByTicketId(ticket.getIdTicket());
        DetalleTicket d = detalleTickets.stream().findFirst().orElseThrow();

        TicketDecargaResponseDTO ticketDecargaResponseDTO = new TicketDecargaResponseDTO();

        ticketDecargaResponseDTO.setNumeroTicket(ticket.getIdTicket());
        ticketDecargaResponseDTO.setFechaTicket(fechaTicket);
        ticketDecargaResponseDTO.setMontoTicket(d.getMonto());
        ticketDecargaResponseDTO.setApellidoJugador(d.getReserva().getJugador().getApellido());
        ticketDecargaResponseDTO.setNombreJugador(d.getReserva().getJugador().getNombre());
        ticketDecargaResponseDTO.setMailJugador(d.getReserva().getJugador().getEmail());
        ticketDecargaResponseDTO.setFormaPago(formaPago);

        return ticketDecargaResponseDTO;
    }

    public List<DetalleReservaConsultaResponseDTO> findDetalleReservaConsulta() {
        Long lastIdJugadorLogued = jugadoresRepository.lastIdJugadorLogued();
        LoginJugadores login = jugadoresRepository.findById(lastIdJugadorLogued).orElseThrow();
        Jugador j = jugadorRepository.findById(login.getJugador().getIdJugador()).orElseThrow();
        List<Reserva> reservas = reservaRepository.findReservaByJugador(j.getIdJugador());
        List<DetalleReservaConsultaResponseDTO> detalleReservaConsultaResponseDTOS = new ArrayList<>();
        for (Reserva r : reservas) {
            //El siguiente if es para validar que la fecha sea antes que hoy, entonces cambia el estado de la reserva a Cancelada.
            if (r.getFecha().isBefore(LocalDate.now())) {
                reservaRepository.updateEstadoToCancelado(r.getIdReserva());
            }
            DetalleReservaConsultaResponseDTO deDTO = new DetalleReservaConsultaResponseDTO();
            List<DetalleReserva> detalleReservas = detalleReservaRepository.findDetalleByReservaId(r.getIdReserva());
            if (!detalleReservas.isEmpty()) {
                for (DetalleReserva de : detalleReservas) {
                    deDTO.setIdReserva(r.getIdReserva());
                    deDTO.setFechaHora(de.getFechaReserva() + " " + de.getHorarioInicial());
                    deDTO.setFechaReserva(r.getFecha().toString());
                    deDTO.setCancha(de.getCancha().getDescripcion());
                    deDTO.setTipoCancha(de.getCancha().getTipoCancha().getDescripcion());
                    deDTO.setDireccion(de.getCancha().getNegocio().getDomicilio());
                    deDTO.setEstado(r.getEstado().getDescripcion());
                    deDTO.setNegocio(de.getCancha().getNegocio().getNombre());
                    detalleReservaConsultaResponseDTOS.add(deDTO);
                }
            }
        }
        return detalleReservaConsultaResponseDTOS;
    }

    public List<DetalleReservaNegocioResponseDTO> findDetalleReservaNegocio() {
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
        List<Cancha> canchas = canchaRepository.findCanchasByNegocioId(negocio.getIdNegocio());
        List<DetalleReservaNegocioResponseDTO> detalleReservaNegocioResponseDTOList = new ArrayList<>();
        for (Cancha c : canchas) {
            List<DetalleReserva> detalles = detalleReservaRepository.findDetalleReservaByCancha(c.getIdCancha());
            for (DetalleReserva dd : detalles) {
                DetalleReservaNegocioResponseDTO detalleReservaNegocioResponseDTO = new DetalleReservaNegocioResponseDTO();
                detalleReservaNegocioResponseDTO.setCancha(c.getDescripcion());
                detalleReservaNegocioResponseDTO.setTipoCancha(c.getTipoCancha().getDescripcion());
                detalleReservaNegocioResponseDTO.setIdReserva(dd.getReserva().getIdReserva());
                detalleReservaNegocioResponseDTO.setFechaReserva(dd.getReserva().getFecha().toString());
                detalleReservaNegocioResponseDTO.setFechaHora(dd.getFechaReserva() + " hora: " + dd.getHorarioInicial());
                detalleReservaNegocioResponseDTO.setEstado(dd.getReserva().getEstado().getDescripcion());
                detalleReservaNegocioResponseDTO.setJugador(dd.getReserva().getJugador().getNombre() + " " + dd.getReserva().getJugador().getApellido());
                detalleReservaNegocioResponseDTOList.add(detalleReservaNegocioResponseDTO);
            }
        }
        return detalleReservaNegocioResponseDTOList;
    }

    public void deleteReserva(ReservaDeleteRequestDTO reservaDelete) throws Exception {

        List<DetalleReserva> detalleReservas = detalleReservaRepository.findDetalleByReservaId(reservaDelete.getIdReserva());
        Reserva reserva = reservaRepository.findById(reservaDelete.getIdReserva()).orElseThrow();
        if (detalleReservas.isEmpty()) {
            reservaRepository.deleteById(reservaDelete.getIdReserva());
        } else if (reserva.getEstado().getIdEstado() == 2) {
            reservaRepository.updateEstadoToCancelado(reservaDelete.getIdReserva());
        } else {
            throw new Exception("La reserva está finalizada o cancelada");
        }
    }
}
