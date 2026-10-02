package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gestão de produtos do cardápio")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Produto>> cadastrar(@RequestBody Produto produto) {
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

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Produto>> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Produto atualizado = produtoService.atualizar(id, produto);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, atualizado, "Produto atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Remover produto", description = "Remove um produto do sistema")
    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}