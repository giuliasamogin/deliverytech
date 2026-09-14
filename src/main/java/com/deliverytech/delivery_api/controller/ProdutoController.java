package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Produto>> cadastrar(@RequestBody Produto produto) {
        Produto criado = produtoService.cadastrar(produto);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, criado, "Produto cadastrado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<Produto>>> listar() {
        List<Produto> produtos = produtoService.listarTodos();
        ApiResponseWrapper<List<Produto>> resposta = new ApiResponseWrapper<>(
            true, produtos, "Produtos listados com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Produto>> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, produto, "Produto encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Produto>> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Produto atualizado = produtoService.atualizar(id, produto);
        ApiResponseWrapper<Produto> resposta = new ApiResponseWrapper<>(
            true, atualizado, "Produto atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}