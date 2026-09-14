package com.deliverytech.delivery_api.dto.response;

import com.deliverytech.delivery_api.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {

    private Long id;
    private String numeroPedido;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;
    private BigDecimal subtotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private Long clienteId;
    private String nomeCliente;
    private Long restauranteId;
    private String nomeRestaurante;
    private List<ItemPedidoResponseDTO> itens;
    private String enderecoEntrega;
    private String cepEntrega;
    private String formaPagamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public StatusPedido getStatusPedido() { return statusPedido; }
    public void setStatusPedido(StatusPedido statusPedido) { this.statusPedido = statusPedido; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getNomeRestaurante() { return nomeRestaurante; }
    public void setNomeRestaurante(String nomeRestaurante) { this.nomeRestaurante = nomeRestaurante; }

    public List<ItemPedidoResponseDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoResponseDTO> itens) { this.itens = itens; }

    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public String getCepEntrega() { return cepEntrega; }
    public void setCepEntrega(String cepEntrega) { this.cepEntrega = cepEntrega; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
}