package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.ClienteDTO;
import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery_api.security.SecurityUtils;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Cadastrar cliente", description = "Cria o cadastro do cliente que fez login")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody ClienteDTO dto) {
        // Cliente comum só cria o próprio cadastro: o email é sempre o da conta que fez login
        if (!SecurityUtils.isAdmin()) {
            dto.setEmail(SecurityUtils.getCurrentUserEmail());
        }

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

    @Operation(summary = "Listar clientes ativos", description = "Retorna todos os clientes ativos (só administrador)")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados")
    @PreAuthorize("hasRole('ADMIN')")
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

    @Operation(summary = "Buscar cliente por email", description = "Retorna o cliente com esse email (só o próprio cliente ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "403", description = "Email de outra pessoa"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @GetMapping("/por-email")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(@RequestParam String email) {
        exigirDonoOuAdmin(email);
        return ResponseEntity.ok(clienteService.buscarClientePorEmail(email));
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente (só o próprio cliente ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "403", description = "Cliente de outra pessoa"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        ClienteResponseDTO cliente = clienteService.buscarClientePorId(id);
        exigirDonoOuAdmin(cliente.getEmail());
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados cadastrais (só o próprio cliente ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Cliente de outra pessoa"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        logger.info("Atualizando cliente ID: {}", id);
        ClienteResponseDTO atual = clienteService.buscarClientePorId(id);
        exigirDonoOuAdmin(atual.getEmail());

        // Cliente comum não pode trocar o email (ele liga o cadastro à conta de login)
        if (!SecurityUtils.isAdmin()) {
            dto.setEmail(atual.getEmail());
        }
        return ResponseEntity.ok(clienteService.atualizarCliente(id, dto));
    }

    @Operation(summary = "Ativar ou desativar cliente", description = "Alterna o status ativo/inativo de um cliente (só administrador)")
    @ApiResponse(responseCode = "204", description = "Status alterado com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Limpar cache", description = "Esvazia o cache de clientes (só administrador)")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "clientes", allEntries = true)
    @GetMapping("/cache/limpar")
    public ResponseEntity<Void> limparCache() {
        logger.info("Cache de clientes limpo manualmente");
        return ResponseEntity.noContent().build();
    }

    // ---- Funções de apoio -------------------------------------------------

    // Deixa passar o administrador ou o dono do cadastro (o email do cadastro = o email do login)
    private void exigirDonoOuAdmin(String emailDoCliente) {
        if (SecurityUtils.isAdmin()) {
            return;
        }
        if (!SecurityUtils.getCurrentUserEmail().equalsIgnoreCase(emailDoCliente)) {
            throw new AccessDeniedException("Você só pode acessar os seus próprios dados.");
        }
    }
}