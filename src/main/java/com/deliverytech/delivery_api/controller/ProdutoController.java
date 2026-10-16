package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.security.SecurityUtils;
import com.deliverytech.delivery_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gestão de produtos do cardápio")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Cadastrar produto", description = "Cria um produto no cardápio do restaurante de quem fez login")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Produto>> cadastrar(@RequestBody Produto produto) {
        // Produto novo nunca vem com ID (senão daria para sobrescrever o produto de outro restaurante)
        produto.setId(null);

        // Restaurante só cadastra produto no PRÓPRIO cardápio: o restaurante enviado pelo site é ignorado
        if (!SecurityUtils.isAdmin()) {
            Restaurante meu = new Restaurante();
            meu.setId(SecurityUtils.getCurrentRestauranteId());
            produto.setRestaurante(meu);
        }

        Produto criado = produtoService.cadastrar(produto);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, criado, "Produto cadastrado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Produtos encontrados")
    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<Produto>>> listar() {
        List<Produto> produtos = produtoService.listarTodos();
        ApiResponseWrapper<List<Produto>> resposta = new ApiResponseWrapper<>(
            true, produtos, "Produtos listados com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados de um produto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Produto>> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, produto, "Produto encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza um produto (só o restaurante dono dele ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Produto de outro restaurante"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Produto>> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        exigirDonoDoProduto(produtoService.buscarPorId(id));

        Produto atualizado = produtoService.atualizar(id, produto);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, atualizado, "Produto atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Remover produto", description = "Remove um produto (só o restaurante dono dele ou um administrador)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "403", description = "Produto de outro restaurante")
    })
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        exigirDonoDoProduto(produtoService.buscarPorId(id));

        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Funções de apoio -------------------------------------------------

    // Deixa passar o administrador ou o restaurante dono do produto
    private void exigirDonoDoProduto(Produto produto) {
        if (SecurityUtils.isAdmin()) {
            return;
        }
        Long donoId = produto.getRestaurante() != null ? produto.getRestaurante().getId() : null;
        if (donoId == null || !donoId.equals(SecurityUtils.getCurrentRestauranteId())) {
            throw new AccessDeniedException("Este produto não é do seu restaurante.");
        }
    }
}