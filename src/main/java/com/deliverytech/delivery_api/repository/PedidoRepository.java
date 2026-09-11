package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatusPedido(StatusPedido statusPedido);
    List<Pedido> findTop10ByOrderByDataPedidoDesc();
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT p FROM Pedido p WHERE p.statusPedido = :status AND p.dataPedido BETWEEN :inicio AND :fim")
    List<Pedido> buscarPorStatusEPeriodo(@Param("status") StatusPedido status, 
                                        @Param("inicio") LocalDateTime inicio, 
                                        @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.statusPedido = :status")
    Long contarPedidosPorStatus(@Param("status") StatusPedido status);




// copiei do ppt

    @Query("SELECT p.restaurante.nome, SUM(p.valorTotal) " +
        "FROM Pedido p " +
        "GROUP BY p.restaurante.nome " +
        "ORDER BY SUM(p.valorTotal) DESC")
    List<Object[]> calcularTotalVendasPorRestaurante();
   
    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > :valor ORDER BY p.valorTotal DESC")
    List<Pedido> buscarPedidosComValorAcimaDe(@Param("valor") BigDecimal valor);
   
    @Query("SELECT p FROM Pedido p " +
       "WHERE p.dataPedido BETWEEN :inicio AND :fim " +
       "AND p.statusPedido = :status " +     // ✅ corrigido
       "ORDER BY p.dataPedido DESC")
List<Pedido> relatorioPedidosPorPeriodoEStatus(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim,
        @Param("status") StatusPedido status);
}


    
