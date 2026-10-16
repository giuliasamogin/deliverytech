package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.security.SecurityUtils;
import com.deliverytech.delivery_api.service.ClienteService;
import com.deliverytech.delivery_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Criação e acompanhamento de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Criar pedido", description = "Registra um novo pedido para o cliente que fez login")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> cadastrar(@Valid @RequestBody PedidoDTO pedido) {
        // O pedido é sempre de quem fez login: o clienteId enviado pelo site é ignorado
        pedido.setClienteId(meuClienteId());

        PedidoResponseDTO criado = pedidoService.criarPedido(pedido);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, criado, "Pedido criado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido (só o cliente dono, o restaurante dele ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "403", description = "Pedido de outra pessoa"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        exigirParticipanteDoPedido(pedido);

        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedido, "Pedido encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Pedidos de um cliente", description = "Lista os pedidos do cliente (cada cliente só vê os seus)")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponseWrapper<List<PedidoResponseDTO>>> listarDoCliente(@PathVariable Long clienteId) {
        if (!SecurityUtils.isAdmin() && !clienteId.equals(meuClienteId())) {
            throw new AccessDeniedException("Você só pode ver os seus próprios pedidos.");
        }

        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        ApiResponseWrapper<List<PedidoResponseDTO>> resposta = new ApiResponseWrapper<>(
            true, pedidos, "Pedidos listados com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Pedidos de um restaurante", description = "Lista os pedidos recebidos pelo restaurante (cada restaurante só vê os seus)")
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<ApiResponseWrapper<List<PedidoResponseDTO>>> listarDoRestaurante(
            @PathVariable Long restauranteId,
            @RequestParam(required = false) StatusPedido status) {
        if (!SecurityUtils.isAdmin() && !restauranteId.equals(SecurityUtils.getCurrentRestauranteId())) {
            throw new AccessDeniedException("Você só pode ver os pedidos do seu restaurante.");
        }

        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorRestaurante(restauranteId, status);
        ApiResponseWrapper<List<PedidoResponseDTO>> resposta = new ApiResponseWrapper<>(
            true, pedidos, "Pedidos listados com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Atualizar status do pedido", description = "Altera o status de um pedido (só o restaurante dele ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Pedido de outro restaurante"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<ApiResponseWrapper<PedidoResponseDTO>> atualizarStatus(
            @PathVariable Long pedidoId, @RequestParam StatusPedido status) {
        PedidoResponseDTO atual = pedidoService.buscarPedidoPorId(pedidoId);
        if (!SecurityUtils.isAdmin() && !atual.getRestauranteId().equals(SecurityUtils.getCurrentRestauranteId())) {
            throw new AccessDeniedException("Este pedido não é do seu restaurante.");
        }

        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatusPedido(pedidoId, status);
        ApiResponseWrapper<PedidoResponseDTO> resposta = new ApiResponseWrapper<>(
            true, pedidoAtualizado, "Status do pedido atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido (só o cliente dono dele ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Pedido de outra pessoa")
    })
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        PedidoResponseDTO atual = pedidoService.buscarPedidoPorId(id);
        if (!SecurityUtils.isAdmin() && !atual.getClienteId().equals(meuClienteId())) {
            throw new AccessDeniedException("Este pedido não é seu.");
        }

        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Funções de apoio -------------------------------------------------

    // Descobre qual é o cliente de quem fez login (a ligação é pelo email)
    private Long meuClienteId() {
        return clienteService.buscarClientePorEmail(SecurityUtils.getCurrentUserEmail()).getId();
    }

    // Só deixa passar: administrador, o cliente dono do pedido ou o restaurante do pedido
    private void exigirParticipanteDoPedido(PedidoResponseDTO pedido) {
        if (SecurityUtils.isAdmin()) {
            return;
        }
        if (SecurityUtils.isCliente() && pedido.getClienteId().equals(meuClienteId())) {
            return;
        }
        if (SecurityUtils.isRestaurante() && pedido.getRestauranteId().equals(SecurityUtils.getCurrentRestauranteId())) {
            return;
        }
        throw new AccessDeniedException("Você não tem acesso a este pedido.");
    }
}