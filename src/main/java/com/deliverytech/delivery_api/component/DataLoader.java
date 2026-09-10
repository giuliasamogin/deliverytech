package com.deliverytech.delivery_api.component;

import com.deliverytech.delivery_api.model.*;
import com.deliverytech.delivery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.deliverytech.delivery_api.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DADOS DE TESTE (DATALOADER) ---");

        // 1. Inserir 3 Clientes
        Cliente c1 = new Cliente();
        c1.setNome("João Silva");
        c1.setEmail("joao@email.com");
        c1.setAtivo(true);

        Cliente c2 = new Cliente();
        c2.setNome("Maria Souza");
        c2.setEmail("maria@email.com");
        c2.setAtivo(true);

        Cliente c3 = new Cliente();
        c3.setNome("Carlos Inativo");
        c3.setEmail("carlos@email.com");
        c3.setAtivo(false);

        clienteRepository.saveAll(Arrays.asList(c1, c2, c3));

        // 2. Inserir 2 Restaurantes
        Restaurante r1 = new Restaurante();
        r1.setNome("Pizzaria Bella Italia");
        r1.setCategoria("Italiana");
        r1.setAvaliacao(4.8);
        r1.setAtivo(true);

        Restaurante r2 = new Restaurante();
        r2.setNome("Burger King Dom");
        r2.setCategoria("Lanches");
        r2.setAvaliacao(4.2);
        r2.setAtivo(true);

        restauranteRepository.saveAll(Arrays.asList(r1, r2));

        // 3. Inserir 5 Produtos variados
        Produto p1 = new Produto();
        p1.setNome("Pizza Margherita");
        p1.setCategoria("Pizza");
        p1.setPreco(45.00);
        p1.setDisponivel(true);
        p1.setRestaurante(r1);

        Produto p2 = new Produto();
        p2.setNome("Pizza Calabresa");
        p2.setCategoria("Pizza");
        p2.setPreco(48.00);
        p2.setDisponivel(true);
        p2.setRestaurante(r1);

        Produto p3 = new Produto();
        p3.setNome("X-Burger Especial");
        p3.setCategoria("Lanches");
        p3.setPreco(25.00);
        p3.setDisponivel(true);
        p3.setRestaurante(r2);

        Produto p4 = new Produto();
        p4.setNome("Batata Frita");
        p4.setCategoria("Porções");
        p4.setPreco(18.00);
        p4.setDisponivel(true);
        p4.setRestaurante(r2);

        Produto p5 = new Produto();
        p5.setNome("Refrigerante Lata");
        p5.setCategoria("Bebidas");
        p5.setPreco(6.00);
        p5.setDisponivel(true);
        p5.setRestaurante(r1);

        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // 4. Inserir Pedidos de Exemplo (utilizando o pedidoRepository)
        Pedido ped1 = new Pedido();
        ped1.setCliente(c1);
        ped1.setRestaurante(r1);
        ped1.setDataPedido(LocalDateTime.now());
        ped1.setStatusPedido(StatusPedido.PENDENTE);

        Pedido ped2 = new Pedido();
        ped2.setCliente(c2);
        ped2.setRestaurante(r2);
        ped2.setDataPedido(LocalDateTime.now().minusHours(1));
        ped2.setStatusPedido(StatusPedido.CONCLUIDO);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

        System.out.println("--- CARGA DE DADOS DE TESTE FINALIZADA COM SUCESSO ---");
    }
}