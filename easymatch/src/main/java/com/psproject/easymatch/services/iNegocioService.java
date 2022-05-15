package com.psproject.easymatch.services;

import com.psproject.easymatch.dtos.NegocioRequestDTO;

public interface iNegocioService {

    //List<ClienteResponseDTO> findAllClientes() throws Exception;

    //List<ClienteResponseDTO> findClienteByDocumento(int documento) throws Exception;

    void addNegocio(NegocioRequestDTO negocioRequestDTO) throws Exception;

    //void updateCliente(JugadorRequestDTO jugador) throws Exception;

    //void changeClienteStatus(ClienteDeleteDTO cliente) throws Exception;
}
