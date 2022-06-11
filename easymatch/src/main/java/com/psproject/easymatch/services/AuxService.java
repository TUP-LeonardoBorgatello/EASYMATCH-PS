package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.*;
import com.psproject.easymatch.models.*;
import com.psproject.easymatch.repositories.*;
import com.psproject.easymatch.utils.CanchaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    TipoCanchaRepository tipoCanchaRepository;
    @Autowired
    NegocioRepository negocioRepository;
    @Autowired
    LoginNegociosRepository loginNegociosRepository;
    @Autowired
    ReservaRepository reservaRepository;

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

    public List<TipoCancha> findAllTipoCanchas() {
        List<TipoCancha> tipoCanchas = tipoCanchaRepository.findAll();
        return tipoCanchas;
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
            if (d.getReserva().getEstado().getIdEstado() != 3) {
                LocalDate fecha = d.getFechaReserva();
                int horario = d.getHorarioInicial();
                if (fecha.equals(fechaReserva) && horario == horarioInicial) {
                    canchas.add(d.getCancha());
                }
            }
        }
        List<Cancha> canchasTotal = canchaRepository.findAll();
        for (Cancha c : canchasTotal) {
            if (!c.getNegocio().getEstado() || !c.getEstado()) {
                canchas.add(c);
            }
        }
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

    public void addCanchas(CanchasRequestDTO canchasRequestDTO) throws Exception {
        if (canchasRequestDTO.getId_tipo_cancha() == 0 || canchasRequestDTO.getPrecio_cancha() < 0
                || canchasRequestDTO.getDescripcion().equals("")) {
            throw new Exception("Valores nulos");
        } else {
            TipoCancha tipoCancha = tipoCanchaRepository.findById(canchasRequestDTO.getId_tipo_cancha()).orElseThrow();
            Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
            LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
            Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
            Cancha c = new Cancha();
            c.setEstado(true);
            c.setDescripcion(canchasRequestDTO.getDescripcion());
            c.setTipoCancha(tipoCancha);
            c.setNegocio(negocio);
            c.setPrecioCancha(canchasRequestDTO.getPrecio_cancha());

            canchaRepository.save(c);
        }
    }

    public List<CanchasResponseDTO> findAllcanchas() {
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        List<Cancha> canchas = canchaRepository.findCanchasByNegocioId(login.getNegocio().getIdNegocio());
        return canchas.stream().map(CanchaMapper::toDTO).collect(Collectors.toList());
    }

    public void updateCancha(CanchasRequestDTO canchasRequestDTO) throws Exception {
        if (canchasRequestDTO.getIdCancha() != 0 && canchasRequestDTO.getPrecio_cancha() != 0
                && !canchasRequestDTO.getDescripcion().equals("")) {
            try {
                Cancha cancha = canchaRepository.searchById(canchasRequestDTO.getIdCancha());
                if (cancha != null) {
                    cancha.setPrecioCancha(canchasRequestDTO.getPrecio_cancha());
                    TipoCancha tipoCancha = tipoCanchaRepository.findById(canchasRequestDTO.getId_tipo_cancha()).orElseThrow();
                    cancha.setTipoCancha(tipoCancha);
                    cancha.setDescripcion(canchasRequestDTO.getDescripcion());
                    canchaRepository.save(cancha);
                }
            } catch (Exception e) {
                throw new Exception("No se pudo modificar, campos nulos.");
            }
        } else {
            throw new Exception("No se pudo modificar, campos nulos.");
        }
    }

    public void changeStatusCancha(CanchasRequestDTO canchasRequestDTO) throws Exception {
        if (canchasRequestDTO.getIdCancha() != 0) {
            try {
                Cancha cancha = canchaRepository.findById(canchasRequestDTO.getIdCancha()).orElseThrow();
                if (cancha.getEstado()) {
                    canchaRepository.updateEstadoToFalse(cancha.getIdCancha());
                } else {
                    canchaRepository.updateEstadoToTrue(cancha.getIdCancha());
                }
            } catch (Exception e) {
                throw new Exception("No se pudo cambiar el estado de la cancha, campos nulos.");
            }
        }
    }

    //REPORTES:

    public Reporte1ResponseDTO findCanchaMasReservada(long mes){
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
        List<Long> canchasIds = detalleReservaRepository.canchasIds(mes, negocio.getIdNegocio());

        HashMap<Long, Long> mapa = new HashMap<>();

        for (Long l : canchasIds) {
            long numero = l;
            if (mapa.containsKey(numero)) {
                mapa.put(numero, mapa.get(numero) + 1);
            } else {
                mapa.put(numero, 1L);
            }
        }
        long moda = 0, mayor = 0;
        for (HashMap.Entry<Long, Long> entry : mapa.entrySet()) {
            if (entry.getValue() > mayor) {
                mayor = entry.getValue();
                moda = entry.getKey();
            }
        }
        Cancha cancha = canchaRepository.findById(moda).orElseThrow();
        Reporte1ResponseDTO reporte1ResponseDTO = new Reporte1ResponseDTO();
        reporte1ResponseDTO.setId_cancha(cancha.getIdCancha());
        reporte1ResponseDTO.setDescripcion(cancha.getDescripcion());
        reporte1ResponseDTO.setCantidadReservada(mayor);

        return reporte1ResponseDTO;

    }


    public CantReservasXFechaDTO findCantReservasEntreFechas(String fecha1, String fecha2, long estado){
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
        LocalDate f1 = LocalDate.parse(fecha1);
        LocalDate f2 = LocalDate.parse(fecha2);
        return reservaRepository.reporte2(f1, f2, estado, negocio.getIdNegocio());

    }

    public List<CantReserXFechaGraficoDTO> findReservasXFechas(String fecha1, String fecha2){
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
        LocalDate f1 = LocalDate.parse(fecha1);
        LocalDate f2 = LocalDate.parse(fecha2);
        return reservaRepository.reporte3(f1, f2, negocio.getIdNegocio());

    }

    public List<MontoXReservaConfirmadaDTO> findMontoXReserva(){
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginNegocio login = loginNegociosRepository.findById(lastIdNegocioLogued).orElseThrow();
        Negocio negocio = negocioRepository.findById(login.getNegocio().getIdNegocio()).orElseThrow();
        return reservaRepository.reporte4(negocio.getIdNegocio());
    }
}
