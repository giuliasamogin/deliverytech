package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.resposta.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.resposta.PedidoDTO;
import com.deliverytech.delivery_api.dto.resposta.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoDTO dto);

    PedidoResponseDTO buscarPedidoPorId(Long id);

    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);

    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens);

    void cancelarPedido(Long id);

    List<PedidoResponseDTO> listarTodos();
}