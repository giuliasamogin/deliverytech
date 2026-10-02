package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Criação e acompanhamento de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Criar pedido", description = "Registra um novo pedido no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> cadastrar(@Valid @RequestBody PedidoDTO pedido) {
        PedidoResponseDTO criado = pedidoService.criarPedido(pedido);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, criado, "Pedido criado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna os dados de um pedido específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedido, "Pedido encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Atualizar status do pedido", description = "Altera o status de um pedido seguindo o fluxo operacional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> atualizarStatus(
            @PathVariable Long pedidoId, @RequestParam StatusPedido status) {
        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatusPedido(pedidoId, status);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedidoAtualizado, "Status do pedido atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido existente")
    @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}