package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.request.RestauranteReqDTO;
import com.deliverytech.delivery_api.dto.response.RestauranteResponseDTO;

import java.util.List;

public interface RestauranteService {

    RestauranteResponseDTO cadastrar(RestauranteReqDTO dto);

    List<RestauranteResponseDTO> listarTodos();

    RestauranteResponseDTO buscarPorId(Long id);

    RestauranteResponseDTO atualizar(Long id, RestauranteReqDTO dto);

    void deletar(Long id);
}