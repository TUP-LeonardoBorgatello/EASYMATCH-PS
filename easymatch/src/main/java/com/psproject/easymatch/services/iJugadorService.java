package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.JugadorRequestDTO;
import com.psproject.easymatch.dtos.JugadorResponseDTO;
import com.psproject.easymatch.dtos.LoginRequestDTO;

import java.util.List;

public interface iJugadorService {

    List<JugadorResponseDTO> findAllJugadores() throws Exception;

    //List<ClienteResponseDTO> findClienteByDocumento(int documento) throws Exception;

    void addJugador(JugadorRequestDTO jugadorRequestDTO) throws Exception;

    void updateJugador(JugadorRequestDTO jugador) throws Exception;

    void deleteJugadorByDocumento(JugadorRequestDTO jugador) throws Exception;

    void login(LoginRequestDTO loginRequestDTO) throws Exception;

}
