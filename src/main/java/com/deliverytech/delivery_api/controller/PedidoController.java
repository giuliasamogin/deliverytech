package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> cadastrar(@Valid @RequestBody PedidoDTO pedido) {
        PedidoResponseDTO criado = pedidoService.criarPedido(pedido);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, criado, "Pedido criado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedido, "Pedido encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> atualizarStatus(
            @PathVariable Long pedidoId, @RequestParam StatusPedido status) {
        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatusPedido(pedidoId, status);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedidoAtualizado, "Status do pedido atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}