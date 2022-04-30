package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.JugadorRequestDTO;

public interface iJugadorService {

    //List<ClienteResponseDTO> findAllClientes() throws Exception;

    //List<ClienteResponseDTO> findClienteByDocumento(int documento) throws Exception;

    void addJugador(JugadorRequestDTO jugadorRequestDTO) throws Exception;

    //void updateCliente(JugadorRequestDTO jugador) throws Exception;

    //void changeClienteStatus(ClienteDeleteDTO cliente) throws Exception;
}
