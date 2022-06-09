package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.dtos.JugadorResponseDTO;
import com.psproject.easymatch.dtos.LoginRequestDTO;
import com.psproject.easymatch.models.*;
import com.psproject.easymatch.repositories.*;
import com.psproject.easymatch.utils.JugadorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JugadorService implements iJugadorService {

    @Autowired
    JugadorRepository jugadorRepository;

    @Autowired
    NegocioRepository negocioRepository;

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    TipoDocRepository tipoDocRepository;

    @Autowired
    LoginJugadoresRepository loginJugadoresRepository;

    @Autowired
    LoginNegociosRepository loginNegociosRepository;

    @Autowired
    DetalleReservaRepository detalleReservaRepository;

    @Autowired
    CanchaRepository canchaRepository;

    @Override
    public List<JugadorResponseDTO> findAllJugadores() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        return jugadores.stream().map(JugadorMapper::toDTO).collect(Collectors.toList());
    }

    public List<JugadorResponseDTO> findJugador() {

        Long lastIdJugadorLogued = loginJugadoresRepository.lastIdJugadorLogued();
        Long lastIdNegocioLogued = loginNegociosRepository.lastIdNegocioLogued();
        LoginJugadores loginJugadores = loginJugadoresRepository.findById(lastIdJugadorLogued).orElseThrow();
        Jugador jugador = loginJugadores.getJugador();
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);

        return jugadores.stream().map(JugadorMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addJugador(JugadorRequestDTO jugadorRequestDTO) throws Exception {
        Jugador jugador = jugadorRepository.searchByDocumento1(jugadorRequestDTO.getDocumento());
        if (jugadorRepository.existsByDocumento(jugadorRequestDTO.getDocumento())) {
            if (jugador.getEstado()) {
                throw new Exception("El jugador ya existe.");
            } else {
                jugadorRepository.updateEstadoToTrue(jugador.getIdJugador());
            }
        } else if (jugadorRequestDTO.getNombre().equals("") || jugadorRequestDTO.getApellido().equals("") || jugadorRequestDTO.getDomicilio().equals("")
                || jugadorRequestDTO.getEmail().equals("") || jugadorRequestDTO.getDocumento() <= 0) {
            throw new Exception("Valores nulos");
        } else {
            Ciudad ciudad = ciudadRepository.findById(jugadorRequestDTO.getId_ciudad()).orElseThrow();
            TipoDoc tipoDoc = tipoDocRepository.findById(jugadorRequestDTO.getId_tipo_doc()).orElseThrow();
            Jugador j = new Jugador();
            j.setNombre(jugadorRequestDTO.getNombre());
            j.setApellido(jugadorRequestDTO.getApellido());
            j.setDomicilio(jugadorRequestDTO.getDomicilio());
            j.setEmail(jugadorRequestDTO.getEmail());
            j.setDocumento(jugadorRequestDTO.getDocumento());
            j.setCiudad(ciudad);
            j.setTipoDoc(tipoDoc);
            j.setEstado(true);
            j.setContraseña(jugadorRequestDTO.getContraseña());

            jugadorRepository.save(j);
        }
    }

    @Override
    public void login(LoginRequestDTO loginRequestDTO) throws Exception {
        Jugador jugador = jugadorRepository.searchByDocumento1(loginRequestDTO.getDocumentoLogin());
        Negocio negocio = negocioRepository.searchByCuil1(loginRequestDTO.getDocumentoLogin());
        if (jugador != null && jugador.getEstado()) {
            LoginJugadores loginJugadores = new LoginJugadores();
            loginJugadores.setJugador(jugador);
            loginJugadoresRepository.save(loginJugadores);
        } else if (negocio != null && negocio.getEstado()) {
            LoginNegocio loginNegocio = new LoginNegocio();
            loginNegocio.setNegocio(negocio);
            loginNegociosRepository.save(loginNegocio);
        } else {
            throw new EntityNotFoundException("No se encontro el usuario");
        }
    }

    public boolean loginState(LoginRequestDTO loginRequestDTO) {
        boolean loginFlag;
        ChangeStausForCanchas();
        Jugador jugador = jugadorRepository.searchByDocumento1(loginRequestDTO.getDocumentoLogin());
        Negocio negocio = negocioRepository.searchByCuil1(loginRequestDTO.getDocumentoLogin());
        if (jugador != null && jugador.getContraseña().equals(loginRequestDTO.getContraseña())) {
            loginFlag = true;
        } else if (negocio != null && negocio.getContraseña().equals(loginRequestDTO.getContraseña())) {
            loginFlag = false;
        } else {
            throw new EntityNotFoundException("No se encontro el usuario");
        }
        return loginFlag;
    }

    private void ChangeStausForCanchas() {
        List<DetalleReserva> detalleReservas = detalleReservaRepository.findAll();
        if (!detalleReservas.isEmpty()) {
            detalleReservas.stream()
                    .filter(detalleReserva -> detalleReserva.getFechaReserva()
                            .isAfter(LocalDateTime.now().toLocalDate()))
                    .forEach(id -> changeStatusCanchas(id.getCancha().getIdCancha()));
        }
    }

    private void changeStatusCanchas(long canchaId) {
        canchaRepository.updateEstadoToTrue(canchaId);
    }

    @Override
    public void updateJugador(JugadorRequestDTO jugadorRequestDTO) throws Exception {
        if (jugadorRequestDTO.getDocumento() != 0) {
            try {
                Jugador jugador = jugadorRepository.searchByDocumento1(jugadorRequestDTO.getDocumento());
                if (jugador != null) {
                    jugador.setNombre(jugadorRequestDTO.getNombre());
                    jugador.setApellido(jugadorRequestDTO.getApellido());
                    jugador.setEmail(jugador.getEmail());
                    Ciudad ciudad = ciudadRepository.findById(jugadorRequestDTO.getId_ciudad()).orElseThrow();
                    jugador.setCiudad(ciudad);
                    TipoDoc tipoDoc = tipoDocRepository.findById(jugadorRequestDTO.getId_tipo_doc()).orElseThrow();
                    jugador.setTipoDoc(tipoDoc);
                    jugador.setDomicilio(jugadorRequestDTO.getDomicilio());
                    jugadorRepository.save(jugador);
                }
            } catch (Exception e) {
                throw new Exception("No se pudo agregar, campos nulos.");
            }
        }
    }

    @Override
    public void deleteJugadorByDocumento(JugadorRequestDTO jugadorRequestDTO) throws Exception {
        if (jugadorRequestDTO.getDocumento() != 0) {
            try {
                Jugador jugador = jugadorRepository.searchByDocumento1(jugadorRequestDTO.getDocumento());
                if (jugador != null) {
                    jugadorRepository.updateEstadoToFalse(jugador.getIdJugador());
                }
            } catch (Exception e) {
                throw new Exception("No se pudo eliminar el jugador, campos nulos.");
            }
        }
    }
}
