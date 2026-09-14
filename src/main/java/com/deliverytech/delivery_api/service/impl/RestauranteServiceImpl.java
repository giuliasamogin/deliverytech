package com.deliverytech.delivery_api.service.impl;

import com.deliverytech.delivery_api.dto.request.RestauranteReqDTO;
import com.deliverytech.delivery_api.dto.response.RestauranteResponseDTO;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.service.RestauranteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RestauranteResponseDTO cadastrar(RestauranteReqDTO dto) {
        Restaurante restaurante = modelMapper.map(dto, Restaurante.class);
        restaurante.setAtivo(true);
        Restaurante salvo = restauranteRepository.save(restaurante);
        return modelMapper.map(salvo, RestauranteResponseDTO.class);
    }

    @Override
    public List<RestauranteResponseDTO> listarTodos() {
        return restauranteRepository.findAll().stream()
                .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestauranteResponseDTO buscarPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    public RestauranteResponseDTO atualizar(Long id, RestauranteReqDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));

        modelMapper.map(dto, restaurante);
        Restaurante atualizado = restauranteRepository.save(restaurante);
        return modelMapper.map(atualizado, RestauranteResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
        restauranteRepository.delete(restaurante);
    }
}