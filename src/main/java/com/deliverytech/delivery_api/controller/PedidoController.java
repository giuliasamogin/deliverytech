package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.resposta.PedidoResponseDTO;
import com.deliverytech.delivery_api.dto.resposta.PedidoDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoService;
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
    public ResponseEntity<PedidoResponseDTO> cadastrar(@RequestBody PedidoDTO pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(pedido));
    }

    // ATENÇÃO: não existe método "listar todos" no Service ainda.
    // Comentei por enquanto — precisa decidir se cria esse método no Service
    // (ex: usando pedidoRepository.findAll()) ou remove este endpoint.
    /*
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPedidoPorId(id));
    }

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long pedidoId, @RequestParam StatusPedido status) {
        try {
            PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatusPedido(pedidoId, status);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}