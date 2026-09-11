package com.deliverytech.delivery_api.enums;

public enum StatusPedido {
    RECEBIDO("Recebido"), 
    CONFIRMADO("Confirmado"),
    PENDENTE("Pendente"), 
    EM_PREPARO("Em Preparo"), 
    SAIU_PARA_ENTREGA("Saiu para Entrega"), 
    ENTREGUE("Entregue"), 
    CONCLUIDO("Concluido"), 
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

