package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.ClienteDTO;
import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery_api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody ClienteDTO dto) {
        logger.info("Cadastro de cliente iniciado: {}", dto.getEmail());
        try {
            ClienteResponseDTO novoCliente = clienteService.cadastrarCliente(dto);
            logger.debug("Cliente salvo com ID {}", novoCliente.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (IllegalArgumentException e) {
            logger.warn("Cadastro recusado: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao cadastrar cliente", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    @Operation(summary = "Listar clientes ativos", description = "Retorna todos os clientes com status ativo")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        logger.info("Listando todos os clientes ativos");
        return ResponseEntity.ok(clienteService.listarClientesAtivos());
    }

    @Operation(summary = "Status da API", description = "Informa que a API está online e registra os núcleos de CPU")
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados cadastrais de um cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        logger.info("Atualizando cliente ID: {}", id);
        return ResponseEntity.ok(clienteService.atualizarCliente(id, dto));
    }

    @Operation(summary = "Ativar ou desativar cliente", description = "Alterna o status ativo/inativo de um cliente")
    @ApiResponse(responseCode = "204", description = "Status alterado com sucesso")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Limpar cache", description = "Esvazia o cache de clientes")
    @CacheEvict(value = "clientes", allEntries = true)
    @GetMapping("/cache/limpar")
    public ResponseEntity<Void> limparCache() {
    logger.info("Cache de clientes limpo manualmente");
    return ResponseEntity.noContent().build();
    }
}