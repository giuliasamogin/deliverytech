package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;

public interface PedidoResumoProjection {
    Long getId();
    String getNomeCliente();
    LocalDateTime getDataPedido();
    String getStatus();
}