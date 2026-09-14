package com.deliverytech.delivery_api.controller;


import com.deliverytech.delivery_api.dto.request.RestauranteReqDTO;
import com.deliverytech.delivery_api.dto.response.ApiResponseWrapper;
import com.deliverytech.delivery_api.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery_api.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> cadastrar(@Valid @RequestBody RestauranteReqDTO dto) {
        RestauranteResponseDTO criado = restauranteService.cadastrar(dto);
        ApiResponseWrapper<RestauranteResponseDTO> resposta = new ApiResponseWrapper<>(
            true, criado, "Restaurante cadastrado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> listar() {
        List<RestauranteResponseDTO> restaurantes = restauranteService.listarTodos();
        ApiResponseWrapper<List<RestauranteResponseDTO>> resposta = new ApiResponseWrapper<>(
            true, restaurantes, "Restaurantes listados com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> buscarPorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        ApiResponseWrapper<RestauranteResponseDTO> resposta = new ApiResponseWrapper<>(
            true, restaurante, "Restaurante encontrado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>> atualizar(
            @PathVariable Long id, @Valid @RequestBody RestauranteReqDTO dto) {
        RestauranteResponseDTO atualizado = restauranteService.atualizar(id, dto);
        ApiResponseWrapper<RestauranteResponseDTO> resposta = new ApiResponseWrapper<>(
            true, atualizado, "Restaurante atualizado com sucesso"
        );
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}