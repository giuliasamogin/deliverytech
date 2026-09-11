package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.resposta.ClienteDTO;
import com.deliverytech.delivery_api.dto.resposta.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO cadastrarCliente(ClienteDTO dto);

    ClienteResponseDTO buscarClientePorId(Long id);

    ClienteResponseDTO buscarClientePorEmail(String email);

    ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto);

    void ativarDesativarCliente(Long id);

    List<ClienteResponseDTO> listarClientesAtivos();
}