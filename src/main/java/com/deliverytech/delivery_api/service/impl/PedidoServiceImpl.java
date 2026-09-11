package com.deliverytech.delivery_api.service.impl;

import com.deliverytech.delivery_api.dto.resposta.PedidoDTO;
import com.deliverytech.delivery_api.dto.resposta.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.resposta.PedidoResponseDTO;
import com.deliverytech.delivery_api.dto.resposta.ItemPedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.model.ItemPedido;
import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.delivery_api.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // taxa de entrega fixa por enquanto - ainda não temos essa regra no Restaurante
    private static final BigDecimal TAXA_ENTREGA_PADRAO = new BigDecimal("5.00");

    @Override
    public PedidoResponseDTO criarPedido(PedidoDTO dto) {
        // 1. Validar cliente existe e está ativo
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + dto.getClienteId()));
        if (!cliente.getAtivo()) {
            throw new BusinessException("Cliente inativo não pode fazer pedidos");
        }

        // 2. Validar restaurante existe e está ativo
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));
        if (!restaurante.getAtivo()) {
            throw new BusinessException("Restaurante não está ativo no momento");
        }

        // 3. Validar produtos e criar os itens
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoDTO itemDto : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId()));

            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new BusinessException("Produto " + produto.getNome() + " não pertence a este restaurante");
            }
            if (!produto.getDisponivel()) {
                throw new BusinessException("Produto " + produto.getNome() + " não está disponível");
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(BigDecimal.valueOf(produto.getPreco()));
            itensPedido.add(item);
        }

        // 4. Calcular valores
        BigDecimal subtotal = itensPedido.stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorTotal = subtotal.add(TAXA_ENTREGA_PADRAO);

        // 5. Montar e salvar o pedido
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.RECEBIDO);
        pedido.setSubtotal(subtotal);
        pedido.setTaxaEntrega(TAXA_ENTREGA_PADRAO);
        pedido.setValorTotal(valorTotal);
        pedido.setItens(itensPedido);

        for (ItemPedido item : itensPedido) {
            item.setPedido(pedido);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 6. Retornar DTO de resposta
        return converterParaResponseDTO(pedidoSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
        return converterParaResponseDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
        pedido.setStatusPedido(status);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return converterParaResponseDTO(pedidoAtualizado);
    }

    @Override
    public BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemPedidoDTO itemDto : itens) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId()));
            BigDecimal precoItem = BigDecimal.valueOf(produto.getPreco()).multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
            subtotal = subtotal.add(precoItem);
        }
        return subtotal.add(TAXA_ENTREGA_PADRAO);
    }

    @Override
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        if (pedido.getStatusPedido() == StatusPedido.ENTREGUE || pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new BusinessException("Pedido não pode ser cancelado no status atual: " + pedido.getStatusPedido());
        }

        pedido.setStatusPedido(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    // Método auxiliar: converte Pedido (entidade) em PedidoResponseDTO
    private PedidoResponseDTO converterParaResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setNumeroPedido(pedido.getNumeroPedido());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatusPedido(pedido.getStatusPedido());
        dto.setSubtotal(pedido.getSubtotal());
        dto.setTaxaEntrega(pedido.getTaxaEntrega());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setNomeCliente(pedido.getCliente().getNome());
        dto.setRestauranteId(pedido.getRestaurante().getId());
        dto.setNomeRestaurante(pedido.getRestaurante().getNome());

        List<ItemPedidoResponseDTO> itensDto = pedido.getItens().stream().map(item -> {
            ItemPedidoResponseDTO itemDto = new ItemPedidoResponseDTO();
            itemDto.setProdutoId(item.getProduto().getId());
            itemDto.setNomeProduto(item.getProduto().getNome());
            itemDto.setQuantidade(item.getQuantidade());
            itemDto.setPrecoUnitario(item.getPrecoUnitario());
            itemDto.setSubtotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
            return itemDto;
        }).collect(Collectors.toList());
        dto.setItens(itensDto);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
            return pedidoRepository.findAll().stream()
            .map(this::converterParaResponseDTO)
            .collect(Collectors.toList());
    }
}