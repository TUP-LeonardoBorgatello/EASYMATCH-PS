package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.CanchasReservasResponseDTO;
import com.psproject.easymatch.models.*;
import com.psproject.easymatch.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuxService {

    @Autowired
    TipoDocRepository tipoDocRepository;
    @Autowired
    CiudadRepository ciudadRepository;
    @Autowired
    FormaPagoRepository formaPagoRepository;
    @Autowired
    CanchaRepository canchaRepository;

    @Autowired
    DetalleReservaRepository detalleReservaRepository;

    public List<TipoDoc> findAllTipoDoc() {
        List<TipoDoc> tipoDocs = tipoDocRepository.findAll();
        return tipoDocs;
    }

    public List<Ciudad> findAllCiudad() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        return ciudades;
    }

    public List<FormaPago> findAllMetodoPago() {
        List<FormaPago> pagos = formaPagoRepository.findAll();
        return pagos;
    }

    public List<CanchasReservasResponseDTO> findCanchasDescripcion() {
        List<Cancha> canchas = canchaRepository.findAll();
        List<CanchasReservasResponseDTO> listaCanchasReservas = new ArrayList<>();

        for (Cancha c : canchas) {
            CanchasReservasResponseDTO canchasReservasResponseDTO = new CanchasReservasResponseDTO();

            canchasReservasResponseDTO.setDescripcion(c.getDescripcion());
            canchasReservasResponseDTO.setTipoCancha(c.getTipoCancha().getDescripcion());
            canchasReservasResponseDTO.setCiudad(c.getNegocio().getCiudad().getDescripcion());
            canchasReservasResponseDTO.setUbicacion(c.getNegocio().getDomicilio());
            canchasReservasResponseDTO.setNombrePredio(c.getNegocio().getNombre());
            canchasReservasResponseDTO.setPrecio(c.getPrecioCancha());
            canchasReservasResponseDTO.setId_cancha(c.getIdCancha());

            listaCanchasReservas.add(canchasReservasResponseDTO);
        }

        return listaCanchasReservas;
    }

    public List<CanchasReservasResponseDTO> findCanchasNoReservadas() {
        String fechaHoy = LocalDate.now().toString();
        List<Long> idCanchas = detalleReservaRepository.searchIdCanchasReservadas(fechaHoy);
        Set<Cancha> canchas = idCanchas.stream().map(id -> canchaRepository.searchById(id)).collect(Collectors.toSet());
        List<CanchasReservasResponseDTO> listaCanchasReservas = new ArrayList<>();

        for (Cancha c : canchas) {
            CanchasReservasResponseDTO canchasReservasResponseDTO = new CanchasReservasResponseDTO();

            canchasReservasResponseDTO.setDescripcion(c.getDescripcion());
            canchasReservasResponseDTO.setTipoCancha(c.getTipoCancha().getDescripcion());
            canchasReservasResponseDTO.setCiudad(c.getNegocio().getCiudad().getDescripcion());
            canchasReservasResponseDTO.setUbicacion(c.getNegocio().getDomicilio());
            canchasReservasResponseDTO.setNombrePredio(c.getNegocio().getNombre());
            canchasReservasResponseDTO.setPrecio(c.getPrecioCancha());
            canchasReservasResponseDTO.setId_cancha(c.getIdCancha());

            listaCanchasReservas.add(canchasReservasResponseDTO);
        }

        return listaCanchasReservas;
    }

    public List<CanchasReservasResponseDTO> findCanchasNoReservadas2(int horarioInicial, String fecha_reserva) {
        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaReserva = LocalDate.parse(fecha_reserva, JEFormatter);
        List<DetalleReserva> reservas = detalleReservaRepository.findAll();
        List<Cancha> canchas = new ArrayList<>();
        for (DetalleReserva d : reservas) {
            if (d.getReserva().getEstado().getIdEstado() == 1){
                LocalDate fecha = d.getFechaReserva();
                int horario = d.getHorarioInicial();
                if (fecha.equals(fechaReserva) && horario == horarioInicial) {
                    canchas.add(d.getCancha());
                }
            }
        }
        List<Cancha> canchasTotal = canchaRepository.findAll();
        canchasTotal.removeAll(canchas);
        List<CanchasReservasResponseDTO> listaCanchasReservas = new ArrayList<>();

        for (Cancha c : canchasTotal) {
            CanchasReservasResponseDTO canchasReservasResponseDTO = new CanchasReservasResponseDTO();

            canchasReservasResponseDTO.setDescripcion(c.getDescripcion());
            canchasReservasResponseDTO.setTipoCancha(c.getTipoCancha().getDescripcion());
            canchasReservasResponseDTO.setCiudad(c.getNegocio().getCiudad().getDescripcion());
            canchasReservasResponseDTO.setUbicacion(c.getNegocio().getDomicilio());
            canchasReservasResponseDTO.setNombrePredio(c.getNegocio().getNombre());
            canchasReservasResponseDTO.setPrecio(c.getPrecioCancha());
            canchasReservasResponseDTO.setId_cancha(c.getIdCancha());

            listaCanchasReservas.add(canchasReservasResponseDTO);
        }
        return listaCanchasReservas;
    }
}
