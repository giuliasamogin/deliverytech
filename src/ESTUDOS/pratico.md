## Encontro 02 — Conhecendo o Spring Boot e Iniciação do Projeto

> Etapa 1 — Configuração do Ambiente

Instalação do Java JDK 21:

Acesse a página de downloads da Oracle: [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)

Baixe o instalador do JDK 21 para Windows (jdk-21_windows-x64_bin.exe).

Execute o arquivo baixado e avance nos passos de instalação clicando em "Next" até a conclusão.

Configuração das Variáveis de Ambiente do Java (JAVA_HOME e Path):

Pressione Windows + R, digite SystemPropertiesAdvanced e clique em OK.

Na janela exibida, clique no botão "Variáveis de Ambiente...".

Na seção "Variáveis do sistema", clique em "Novo..." e preencha:

Nome da variável: JAVA_HOME

Valor da variável: C:\Program Files\Java\jdk-21

Clique em OK.

Na mesma seção "Variáveis do sistema", selecione a variável Path e clique em "Editar...".

Clique em "Novo" e adicione o caminho: C:\Program Files\Java\jdk-21\bin

Clique em OK em todas as janelas para salvar.

Teste da instalação do Java:

Abra o Prompt de Comando (cmd).

Digite o comando:

Bash
java -version
Resultado esperado: Exibição da versão 21.x.x do Java.

Instalação do Apache Maven:

Acesse: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

Baixe o arquivo binário em formato zip (exemplo: apache-maven-3.9.10-bin.zip).

Extraia o conteúdo do zip para um diretório do sistema (exemplo: C:\Program Files\Apache\Maven\apache-maven-3.9.10).

Acesse novamente as "Variáveis de Ambiente".

Em "Variáveis do sistema", clique em "Novo..." e adicione:

Nome da variável: MAVEN_HOME

Valor da variável: C:\Program Files\Apache\Maven\apache-maven-3.9.10

Edite a variável Path do sistema, clique em "Novo" e adicione: C:\Program Files\Apache\Maven\apache-maven-3.9.10\bin

Abra o terminal e digite:

Bash
mvn -v
Resultado esperado: Exibição da versão do Apache Maven e indicação do Java 21.

Configuração da IDE (VS Code):

Abra o Visual Studio Code.

Clique no ícone de "Extensions" na barra lateral esquerda (ou Ctrl + Shift + X).

Digite Extension Pack for Java no campo de busca.

Localize o pacote oficial da Microsoft e clique em "Install".

Teste de execução Java no VS Code:

Crie um arquivo chamado teste.java.

Insira o código de teste:

Java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
Observação técnica: O slide apresentava a instrução com erro de digitação (System.out.prim(in("Hello, World!");}). A versão corrigida acima garante a compilação do teste inicial.

Execute o código clicando no botão "Run" ou pressionando Ctrl + F5.

Resultado esperado: Mensagem "Hello, World!" exibida no terminal integrado.

> Etapa 2 — Criar o projeto no Spring Initializr

Acesse o site: [https://start.spring.io/](https://start.spring.io/)

Preencha as configurações do projeto:

Project: Maven

Language: Java

Spring Boot: Selecione a versão estável recomendada (exemplo: 3.5.3 ou 3.2.x)

Project Metadata:

Group: com.deliverytech

Artifact: delivery-api

Name: delivery-api

Description: Aplicação de Delivery

Package name: com.deliverytech.delivery-api

Packaging: Jar

Java: 21

Adicione as dependências do projeto clicando em "ADD DEPENDENCIES":

Spring Web

Spring Data JPA

H2 Database   

Spring Boot DevTools   

Lombok   

Clique no botão "GENERATE" para realizar o download do arquivo .zip.   

Extraia o arquivo baixado para a pasta do seu ambiente de trabalho.   

Abra a pasta do projeto no VS Code.   

> Etapa 3 — Configurar o Repositório Git e GitHub   

Crie uma conta no GitHub acessando [https://github.com/](https://github.com/) se ainda não possuir.   

Instale o Git no computador através de [https://git-scm.com/downloads](https://git-scm.com/downloads).   

Abra o terminal e verifique a instalação:   

Bash
git --version
Crie a pasta do projeto e inicie o versionamento Git:   

Bash
mkdir deliverytech
cd deliverytech
git init
Configure as credenciais globais do autor no Git:   

Bash
git config --global user.name "Anderson Buenos"
git config --global user.email "anderson.buenos@exemplo.com"
Verifique as configurações gravadas:

Bash
git config --list
Adicione os arquivos do projeto e realize o commit inicial:

Bash
git add .
git commit -m "feat: configuração inicial do projeto Spring Boot com JDK 21"
git branch -M main
Configuração de Chave SSH para autenticação no GitHub:

Exiba a chave pública gerada no terminal:

Bash
cat ~/.ssh/id_ed25519.pub
Copie o conteúdo exibido.

No GitHub, vá no seu Perfil -> "Settings" -> "SSH and GPG keys" -> "New SSH key".

Digite um título no campo "Title", cole a chave no campo "Key" e clique em "Add SSH key".

Vincule o repositório local ao repositório remoto no GitHub e envie os arquivos:

Bash
git remote add origin git@github.com:SEU_USUARIO/NOME_DO_REPOSITORIO.git
git push -u origin main
> Etapa 4 — Configurar o arquivo application.properties
Arquivo:
src/main/resources/application.properties

Código:

Properties
spring.application.name=delivery-api
server.port=8080

# Configuração do H2 Database
spring.datasource.url=jdbc:h2:mem:deliverydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Configurações de desenvolvimento
spring.devtools.restart.enabled=true

# Configurações específicas para JDK 21
spring.jpa.open-in-view=false
logging.level.org.springframework.web=DEBUG
> Etapa 5 — Criar o Controller do Endpoint de Health Check
Estrutura de pastas esperada:

DeliveryTech/
└── delivery-api/
	├── .git/
	├── .mvn/
	├── .settings/
	├── delivery-api/
	│   └── src/
	│   	└── main/
	│      	├── java/
	│      	│   └── com/
	│      	│   	└── deliverytech/
	│      	│      	└── delivery-api/
	│      	│               ├── controller/
	│      	│               │   ├── HealthController.java
	│      	│               └── DeliveryApiApplication.java
	│      	└── resources/
	│               └── application.properties
Arquivo:
src/main/java/com/deliverytech/delivery_api/controller/HealthController.java

Código:

Java
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
       return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
       );
    }

    @GetMapping("/info")
    public AppInfo info() {
       return new AppInfo(
            "Delivery Tech API",
            "1.0.0",
            "[Seu Nome]",
            "JDK 21",
            "Spring Boot 3.2.x"
       );
    }

    // Record para demonstrar recurso do Java 14+ (disponível no JDK 21)
    public record AppInfo(
       String application,
       String version,
       String developer,
       String javaVersion,
       String framework
    ) {}
}
Observação: O slide apresenta apenas este trecho do código, omitindo as declarações explícitas de package e import.

> Etapa 6 — Testar e Validar os Endpoints da Aplicação

Inicie a aplicação Spring Boot na IDE.

Teste o endpoint /health:

Método: GET

URL: http://localhost:8080/health

Resultado esperado: Retorno JSON confirmando status "UP" e a versão do Java 21.

Teste o endpoint /info:

Método: GET

URL: http://localhost:8080/info

Resultado esperado: Retorno JSON com as informações da aplicação.

Teste de acesso ao Console do Banco H2:

Método: GET

URL: http://localhost:8080/h2-console

Resultado esperado: Exibição da tela de login do console H2.

> Etapa 7 — Atualizar a Documentação no arquivo README.md
Arquivo:
README.md

Conteúdo:

Markdown
# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias
- **Java 21 LTS** (versão mais recente)
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## ⚡ Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## 🏃‍♂️ Como executar
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8080/health

## 📋 Endpoints
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações da aplicação
- GET /h2-console - Console do banco H2

## 🔧 Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development

## 👨‍💻 Desenvolvedor
[Seu Nome] - [Sua Turma]
Desenvolvido com JDK 21 e Spring Boot 3.2.x
## Encontro 04 — Estrutura de Projetos MAVEN e Visão Geral da Arquitetura

> Etapa 1 — Mapear os Módulos e Entregáveis do Projeto
O projeto delivery-api deve ser desenvolvido seguindo a separação por responsabilidades dividida nas etapas:

Configuração de Entities e Repositories (Persistência):

ClienteRepository: Herdar JpaRepository, buscas por e-mail e status ativo.

RestauranteRepository: Buscas por nome, categoria, restaurantes ativos e ordenação por avaliação.

ProdutoRepository: Buscas por restaurante, categoria e disponibilidade.

PedidoRepository: Buscas por cliente, filtros por status, período e relatórios.

Implementação dos Services (Regras de Negócio):

ClienteService: Cadastro, validação de e-mail único, busca, atualização e inativação.

RestauranteService: Gestão, validações e controle de status ativo/inativo.

ProdutoService: Cadastro por restaurante, disponibilidade e validação de preço.

PedidoService: Criação, cálculo de valores, transição de status e validações.

Controllers REST (Endpoints):

ClienteController: POST /clientes, GET /clientes, GET /clientes/{id}, PUT /clientes/{id}, DELETE /clientes/{id}.

RestauranteController: CRUD completo e busca por categoria.

ProdutoController: CRUD completo e busca de produtos por restaurante.

PedidoController: Criar pedido, consultar por cliente e atualizar status.

Testes e Validação:

Coleção de testes no Postman / Insomnia.

Validação de regras e persistência no console do banco H2.

Documentação e Repositório GitHub:

Repositório no GitHub nomeado como delivery-api-[seunome].

Branch principal: main.

> Etapa 2 — Organizar a Estrutura de Pastas do Projeto
A estrutura de arquivos e diretórios da aplicação deve ser organizada exatamente da seguinte forma:

delivery-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/deliverytech/delivery/
│   │   │       ├── controller/
│   │   │       │   ├── ClienteController.java
│   │   │       │   ├── RestauranteController.java
│   │   │       │   ├── ProdutoController.java
│   │   │       │   └── PedidoController.java
│   │   │       ├── service/
│   │   │       │   ├── ClienteService.java
│   │   │       │   ├── RestauranteService.java
│   │   │       │   ├── ProdutoService.java
│   │   │       │   └── PedidoService.java
│   │   │       ├── repository/
│   │   │       │   ├── ClienteRepository.java
│   │   │       │   ├── RestauranteRepository.java
│   │   │       │   ├── ProdutoRepository.java
│   │   │       │   └── PedidoRepository.java
│   │   │       ├── module/
│   │   │       └── DeliveryApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
├── postman/
│   └── DeliveryAPI.postman_collection.json
├── README.md
└── pom.xml
## Encontro 06 — Spring Data JPA e Repositories

> Etapa 1 — Criar o pacote repository na IDE

No VS Code, clique com o botão direito sobre a pasta do pacote principal (delivery_api).

Selecione "New Folder" e nomeie como repository.

Dentro da pasta repository, crie as interfaces Java especificadas a seguir.

> Etapa 2 — Implementar os Repositories da Aplicação

Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/ClienteRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Cliente> findByAtivoTrue();
}
Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/RestauranteRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    List<Restaurante> findByAtivoTrue();
}
Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/ProdutoRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByRestauranteId(Long restauranteId);
    List<Produto> findByDisponivelTrue();
    List<Produto> findByCategoria(String categoria);
}
Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/PedidoRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByRestauranteId(Long restauranteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);
}
Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/UsuarioRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
> Etapa 3 — Criar a classe DataLoader para Testes de Persistência

Clique com o botão direito na pasta principal delivery_api e crie a pasta config.

Dentro da pasta config, crie um novo arquivo Java chamado DataLoader.java.

Arquivo:
src/main/java/com/deliverytech/delivery_api/config/DataLoader.java

Observação: Os próximos slides apresentam partes da mesma classe. Abaixo está o código reunido na ordem em que aparece.

Código:

Java
package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.model.*;
import com.deliverytech.delivery_api.model.StatusPedido;
import com.deliverytech.delivery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
        System.out.println("=== INICIANDO CARGA DE DADOS DE TESTE ===");
        inserirClientes();
        inserirRestaurantes();
        testarConsultas();
    }

    private void inserirClientes() {
        System.out.println("--- Inserindo clientes ---");
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("11987654321");
        cliente1.setEndereco("Rua A, 123, São Paulo");
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("11998765432");
        cliente2.setEndereco("Avenida B, 456, Rio de Janeiro");
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("11912345678");
        cliente3.setEndereco("Travessa C, 789, Belo Horizonte");
        cliente3.setAtivo(false);

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
        System.out.println(" 3 clientes inseridos");
    }

    private void inserirRestaurantes() {
        System.out.println("--- Inserindo Restaurantes ---");
        Restaurante restaurantel = new Restaurante();
        restaurantel.setNome("Pizza Express");
        restaurantel.setCategoria("Italiana");
        restaurantel.setEndereco("Av. Principal, 100");
        restaurantel.setTelefone("1133333333");
        restaurantel.setTaxaEntrega(new BigDecimal("3.50"));
        restaurantel.setAtivo(true);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Burger King");
        restaurante2.setCategoria("Fast Food");
        restaurante2.setEndereco("Rua Secundária, 200");
        restaurante2.setTelefone("1144444444");
        restaurante2.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante2.setAtivo(true);

        restauranteRepository.saveAll(Arrays.asList(restaurantel, restaurante2));
        System.out.println(" 2 restaurantes inseridos");
    }

    private void testarConsultas() {
        System.out.println("\n== TESTANDO CONSULTAS DOS REPOSITORIES ==");
        
        System.out.println("\nTestes ClienteRepository");
        var clientePorEmail = clienteRepository.findByEmail("joao@email.com");
        System.out.println("Cliente por email: " + clientePorEmail.map(Cliente::getNome).orElse("Não encontrado"));

        var clientesAtivos = clienteRepository.findByAtivoTrue();
        System.out.println("Clientes ativos: " + clientesAtivos.size());

        var clientesPorNome = clienteRepository.findByNomeContainingIgnoreCase("silva");
        System.out.println("Clientes com 'silva' no nome: " + clientesPorNome.size());

        boolean existeEmail = clienteRepository.existsByEmail("maria@email.com");
        System.out.println("Existe cliente com email: " + existeEmail);
    }
}
> Etapa 4 — Adicionar Consultas Customizadas, Projeções e Relatórios

Abra o arquivo PedidoRepository.java criado na Etapa 2 e adicione as consultas personalizadas utilizando a anotação @Query:

Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/PedidoRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByRestauranteId(Long restauranteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    //=== CONSULTAS CUSTOMIZADAS ===
    @Query("SELECT p.restaurante.nome, SUM(p.valorTotal) " +
           "FROM Pedido p " +
           "GROUP BY p.restaurante.nome " +
           "ORDER BY SUM(p.valorTotal) DESC")
    List<Object[]> calcularTotalVendasPorRestaurante();

    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > :valor ORDER BY p.valorTotal DESC")
    List<Pedido> buscarPedidosComValorAcimaDe(@Param("valor") BigDecimal valor);

    @Query("SELECT p FROM Pedido p " +
           "WHERE p.dataPedido BETWEEN :inicio AND :fim " +
           "AND p.status = :status " +
           "ORDER BY p.dataPedido DESC")
    List<Pedido> relatorioPedidosPorPeriodoEStatus(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("status") StatusPedido status);
}
Crie a interface de projeção para o relatório de vendas:

Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/RelatorioVendas.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;

public interface RelatorioVendas {
    String getNomeRestaurante();
    BigDecimal getTotalVendas();
    Long getQuantidePedidos();
}
Abra o arquivo RestauranteRepository.java e inclua o método de consulta retornando a projeção RelatorioVendas:

Arquivo:
src/main/java/com/deliverytech/delivery_api/repository/RestauranteRepository.java

Código:

Java
package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    List<Restaurante> findByAtivoTrue();

    @Query("SELECT r.nome as nomeRestaurante, " +
           "SUM(p.valorTotal) as totalVendas, " +
           "COUNT(p.id) as quantidePedidos " +
           "FROM Restaurante r " +
           "LEFT JOIN Pedido p ON r.id = p.restaurante.id " +
           "GROUP BY r.id, r.nome")
    List<RelatorioVendas> relatorioVendasPorRestaurante();
}
> Etapa 5 — Criar o Enum StatusPedido
Arquivo:
src/main/java/com/deliverytech/delivery_api/model/StatusPedido.java

Código:

Java
package com.deliverytech.delivery_api.model;

public enum StatusPedido {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    PREPARANDO("Preparando"),
    SAIU_PARA_ENTREGA("Saiu para Entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
> Etapa 6 — Atualizar as Configurações do Banco H2 e Logs de Execução
Arquivo:
src/main/resources/application.properties

Código:

Properties
# ===== CONFIGURAÇÃO H2 DATABASE =====
spring.datasource.url=jdbc:h2:mem:delivery
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# ===== H2 CONSOLE =====
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true

# ===== JPA/HIBERNATE =====
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# ===== LOGGING =====
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
logging.level.org.springframework.transaction=DEBUG

# ===== CONFIGURAÇÕES ADICIONAIS =====
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
> Etapa 7 — Executar a Aplicação e Validar no Console H2

Abra o terminal na pasta raiz do projeto e execute:

Bash
mvn spring-boot:run
Abra o navegador e acesse:
http://localhost:8080/h2-console

Preencha os campos de conexão com as propriedades configuradas:

JDBC URL: jdbc:h2:mem:delivery

Username: sa

Password: (manter vazio)

No campo de execução de comando SQL, digite:

SQL
SELECT * FROM cliente;
Clique no botão "Run" para verificar as linhas cadastradas.

## Encontro 08 — Camada de Serviços e Controllers REST

> Etapa 1 — Criar a Estrutura de Pacotes das Camadas da Aplicação
Na pasta src/main/java/com/deliverytech/delivery_api, crie os seguintes pacotes:

com.deliverytech.delivery_api.controller

com.deliverytech.delivery_api.dto

com.deliverytech.delivery_api.model

com.deliverytech.delivery_api.repository

com.deliverytech.delivery_api.service

com.deliverytech.delivery_api.service.impl

com.deliverytech.delivery_api.exception

com.deliverytech.delivery_api.config

> Etapa 2 — Configurar a Injeção do ModelMapper e Dependências

Garanta que as dependências spring-boot-starter-validation e modelmapper estão configuradas no arquivo pom.xml.

Crie a classe de configuração do ModelMapper:

Arquivo:
src/main/java/com/deliverytech/delivery_api/config/ModelMapperConfig.java

Código:

Java
package com.deliverytech.delivery_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
       return new ModelMapper();
    }
}
> Etapa 3 — Criar os DTOs de Entrada e Saída

DTO de Entrada do Cliente com Validações do Bean Validation:

Arquivo:
src/main/java/com/deliverytech/delivery_api/dto/ClienteDTO.java

Código:

Java
package com.deliverytech.delivery_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;

    private String endereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
DTO de Entrada de Pedidos com Validação em Cascata:

Arquivo:
src/main/java/com/deliverytech/delivery_api/dto/PedidoDTO.java

Observação: O slide destaca que a lista de itens deve conter a anotação @Valid para disparar a validação individual de cada item, e a quantidade de cada item exige @Min(1).

> Etapa 4 — Implementar as Exceções Customizadas e o Manipulador Global

Criar a classe para exceções de regras de negócio:
Arquivo: src/main/java/com/deliverytech/delivery_api/exception/BusinessException.java

Criar a classe para exceção de entidade não encontrada:
Arquivo: src/main/java/com/deliverytech/delivery_api/exception/EntityNotFoundException.java

Criar a classe GlobalExceptionHandler:
Arquivo: src/main/java/com/deliverytech/delivery_api/exception/GlobalExceptionHandler.java
Observação: Deve ser anotada com @ControllerAdvice para interceptar erros em toda a aplicação e retornar respostas HTTP apropriadas aos clientes.

> Etapa 5 — Implementar as Regras de Negócio na Camada Service

Interface do ClienteService:
Arquivo: src/main/java/com/deliverytech/delivery_api/service/ClienteService.java
Assinaturas requeridas: cadastrar, buscar por ID ou email, atualizar, ativar/desativar e listar clientes ativos.

Classe de Implementação ClienteServiceImpl:
Arquivo: src/main/java/com/deliverytech/delivery_api/service/impl/ClienteServiceImpl.java
Instruções de execução dos métodos:

cadastrarCliente(): Deve validar se o e-mail já existe utilizando existsByEmail(), converter o DTO para entidade, persistir e retornar o DTO de resposta.

buscarClientePorId(): Deve buscar o cliente no repositório por findById(), lançar EntityNotFoundException se não encontrado e converter o retorno para DTO.

atualizarCliente(): Deve buscar o registro por ID, validar duplicidade de e-mail e atualizar as informações.

ativarDesativarCliente(): Deve alternar o estado do atributo booleano ativo do cliente (operação toggle), salvar e retornar os dados atualizados.

listarClientesAtivos(): Deve buscar clientes ativos, transformar a lista utilizando Stream API (stream().map(...)) convertendo cada entidade para ClienteResponseDTO.

Implementação do PedidoService (PedidoServiceImpl.java):
Arquivo: src/main/java/com/deliverytech/delivery_api/service/impl/PedidoServiceImpl.java
Instruções de execução do método criarPedido():

Valida se o cliente existe e se está ativo no sistema.

Valida se o restaurante existe e está operacional.

Valida se os produtos solicitados existem e pertencem ao restaurante informado.

Transforma os DTOs em entidades de itens.

Utiliza a API Stream com BigDecimal e o método reduce para somar os valores dos itens e adicionar a taxa de entrega do restaurante.

Salva o pedido e os itens sob a anotação @Transactional, atribuindo o status inicial RECEBIDO e a data/hora atual.

> Etapa 6 — Implementar os Controllers REST

Controller de Clientes:

Arquivo:
src/main/java/com/deliverytech/delivery_api/controller/ClienteController.java

Instruções de construção:

Anotar com @RestController e @RequestMapping("/api/clientes").

Injetar a interface ClienteService via construtor.

Método POST: Receber @Valid @RequestBody ClienteDTO, acionar cadastrarCliente() e retornar o status 201 Created.

Controller de Pedidos:

Arquivo:
src/main/java/com/deliverytech/delivery_api/controller/PedidoController.java

Endpoints e verbos HTTP:

POST /api/pedidos: Criação de novos pedidos (status 201 Created).

GET /api/pedidos/{id}: Consulta de pedido por ID.

GET /api/pedidos/cliente/{clienteId}: Listagem de pedidos de um cliente específico.

PATCH /api/pedidos/{id}/status: Atualização do status do pedido conforme o fluxo: RECEBIDO -> CONFIRMADO -> EM_PREPARO -> SAIU_PARA_ENTREGA -> ENTREGUE.

DELETE /api/pedidos/{id}: Cancelamento de pedido (altera o status para CANCELADO e retorna 204 No Content).

> Etapa 7 — Testar as Operações via Postman

Teste de Cadastro de Cliente:

Método: POST

URL: http://localhost:8080/api/clientes

Body: JSON contendo nome, e-mail, telefone e endereço.

Resposta esperada: Status 201 Created.

Teste de Criação de Pedido:

Método: POST

URL: http://localhost:8080/api/pedidos

Body: JSON com clienteId, restauranteId e array de itens.

Resposta esperada: Status 201 Created contendo o cálculo do valor total.

Teste de Atualização de Status:

Método: PATCH

URL: http://localhost:8080/api/pedidos/1/status

Body: {"status": "CONFIRMADO"}

Resposta esperada: Status 200 OK.

Teste de Cancelamento de Pedido:

Método: DELETE

URL: http://localhost:8080/api/pedidos/1

Resposta esperada: Status 204 No Content.

## Encontro 10 — API REST Completa e Documentação com Swagger

> Etapa 1 — Configurar o Swagger / OpenAPI no Projeto

Adicione a dependência da documentação no arquivo pom.xml:
springdoc-openapi-starter-webmvc-ui

Crie a classe de configuração da documentação:   

Arquivo:
src/main/java/com/deliverytech/delivery_api/config/SwaggerConfig.java   

> Etapa 2 — Criar os Wrappers de Padronização de Respostas   

Estrutura do Wrapper Padrão (ApiResponseWrapper.java):

Arquivo:
src/main/java/com/deliverytech/delivery_api/dto/ApiResponseWrapper.java

Modelo JSON de resposta:

JSON
{
  "data": {},
  "timestamp": "2023-10-15T14:30:00Z",
  "success": true
}
Estrutura do Wrapper de Paginação (PagedResponseWrapper.java):
Modelo JSON de resposta:

JSON
{
  "data": [],
  "page": 0,
  "size": 10,
  "totalElements": 45,
  "totalPages": 5
}
Estrutura do Erro Padronizado (ErrorResponse.java):
Modelo JSON de resposta:

JSON
{
  "timestamp": "2023-10-15T14:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Dados inválidos",
  "path": "/api/pedidos"
}
> Etapa 3 — Implementar e Documentar os Controllers da Aplicação
Adicione as anotações do Swagger (@Operation, @ApiResponses, @ApiResponse) no nível de classe e em cada método dos controllers:

RestauranteController:

Implementar endpoints para cadastro, edição, listagem com filtros de busca, alteração de status ativo e gerenciamento de categorias.

ProdutoController:

Implementar CRUD completo de produtos, busca por categorias e atualização pontual de disponibilidade com a anotação @PatchMapping.

PedidoController:

Implementar criação de pedido com múltiplos itens, histórico de solicitações e transições de status.

RelatorioController:

Implementar endpoints para emissão de relatórios de vendas por restaurante e consulta de produtos mais vendidos.

> Etapa 4 — Aplicar os Status e Headers HTTP
Assegure a utilização correta dos seguintes códigos de retorno HTTP nos controllers:

200 OK: Execução de consulta ou alteração concluída com retorno de dados.

201 Created: Novo recurso criado no banco. Informe o cabeçalho Location indicando o caminho do novo recurso (ex: Location: /api/pedidos/123).

204 No Content: Remoção ou ação concluída sem corpo de retorno.

400 Bad Request: Requisição recusada por erro de validação.

404 Not Found: Identificador não localizado no banco de dados.

409 Conflict: Violação de regra por duplicidade de informação.

500 Internal Server Error: Erro interno no processamento do servidor.

Headers que devem ser utilizados:

Content-Type: application/json

Location: URI do recurso criado

Cache-Control: Diretivas de controle de cache

CORS: Access-Control-Allow-Origin, Access-Control-Allow-Methods, Access-Control-Allow-Headers

> Etapa 5 — Configurar a Paginação nos Endpoints
Nos métodos de consulta dos controllers, adicione os parâmetros de paginação:

page: Número da página (iniciando em 0)

size: Quantidade de elementos por página

sort: Campo e direção da ordenação

Exemplo de chamada HTTP:
GET /api/produtos?page=0&size=20&sort=nome,asc

> Etapa 6 — Implementar Testes de Integração com MockMvc
Na pasta src/test/java, crie as classes de testes de integração para validar a API:

RestauranteControllerIT: Testes automatizados para cadastro, filtros de listagem e busca por ID.

ProdutoControllerIT: Testes do CRUD, alternância de disponibilidade e busca por categorias.

PedidoControllerIT: Testes da criação transacional de pedidos, cálculo de totais e mudanças de status.

> Etapa 7 — Acessar a Interface do Swagger UI

Execute a aplicação no terminal utilizando:

Bash
mvn spring-boot:run
Abra o navegador de sua preferência e navegue até a URL:
http://localhost:8080/swagger-ui.html

Na página interativa exibida:

Verifique se todos os controllers e seus respectivos endpoints estão visíveis e agrupados.

Abra um endpoint e clique no botão "Try it out" para enviar requisições de teste em tempo real diretamente pelo navegador.

Confira a documentação dos esquemas das respostas (Schemas), parâmetros e respostas possíveis.


## Encontro 14 — Validações de Entrada e Tratamento Uniforme de Erros
> Objetivo da prática
Implementar um sistema robusto para validação de dados e tratamento padronizado de erros na API REST de delivery utilizando Spring Boot. A solução aplica o padrão RFC 7807 para estruturação de respostas de erro, desenvolve exceções customizadas e aplica anotações do Bean Validation nos DTOs do projeto.
> Estrutura do projeto e pacotes
A atividade organiza os componentes nos seguintes pacotes principais da aplicação:
•	src/main/java/com/deliverytech/exception/: Classes de exceções customizadas e manipulador global de erros.
•	src/main/java/com/deliverytech/validation/: Validadores customizados e anotações de validação de domínio (CEP, Telefone).
•	src/main/java/com/deliverytech/dto/: Data Transfer Objects com anotações do Bean Validation.
•	src/main/java/com/deliverytech/controller/: Controladores REST atualizados para tratar e lançar as exceções.
> Etapa 1 — Criar as Exceções Customizadas
Crie as classes de exceção específicas da aplicação no pacote com.deliverytech.exception para representar erros de negócio, recursos não encontrados e conflitos de duplicidade.
Caminho: Arquivo:
src/main/java/com/deliverytech/exception/BusinessException.java
package com.deliverytech.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

Caminho: Arquivo:
src/main/java/com/deliverytech/exception/EntityNotFoundException.java
package com.deliverytech.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Long id) {
        super(entity + " com ID " + id + " não encontrado");
    }
}

Caminho: Arquivo:
src/main/java/com/deliverytech/exception/ConflictException.java
package com.deliverytech.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String entity, String detail) {
        super(entity + " com " + detail + " já existe");
    }
}

> Etapa 2 — Criar Validadores Customizados
Crie as anotações e os validadores customizados no pacote com.deliverytech.validation para validar formatos específicos do domínio brasileiro, como CEP e Telefone.
Observação: Os slides indicam a criação das anotações @ValidCEP e @ValidTelefone juntamente com suas classes implementadoras de ConstraintValidator.
Caminho: Arquivo:
src/main/java/com/deliverytech/validation/CEPValidator.java
package com.deliverytech.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<ValidCEP, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches("\\d{5}-\\d{3}") || value.matches("\\d{8}");
    }
}

Caminho: Arquivo:
src/main/java/com/deliverytech/validation/TelefoneValidator.java
package com.deliverytech.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches("\\(\\d{2}\\)\\s\\d{5}-\\d{4}") || value.matches("\\d{11}");
    }
}

> Etapa 3 — Criar DTOs com Anotações de Validação
Aplique anotações de validação do Jakarta Validation nos objetos de transferência de dados para interceptar erros antes de atingir as camadas de negócio.
•	RestauranteDTO.java: Contém validações como @NotBlank para nome e endereço, @ValidTelefone para telefone, @ValidCEP para CEP e validação de categoria.
•	ProdutoDTO.java: Contém validações para nome, descrição, preço (@Positive com restrição min/max) e categoria.
•	PedidoDTO.java: Contém validações para clienteId, restauranteId, itens (@NotEmpty(message = "Pedido deve ter pelo menos um item")), enderecoEntrega, cepEntrega e formaPagamento.
•	ItemPedidoDTO.java: Contém validações para produtoId e quantidade (entre 1 e 99).
> Etapa 4 — Criar a Classe ErrorResponse (Padrão RFC 7807)
Crie a classe ErrorResponse no pacote com.deliverytech.exception seguindo a especificação RFC 7807 para padronizar as respostas de erro enviadas aos clientes da API.
Caminho: Arquivo:
src/main/java/com/deliverytech/exception/ErrorResponse.java
Exemplo de estrutura JSON de erro gerada no formato RFC 7807:
{
  "timestamp": "2023-11-10T15:20:30.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/restaurantes",
  "details": {
    "nome": "Nome é obrigatório",
    "telefone": "Telefone inválido"
  }
}

> Etapa 5 — Criar o GlobalExceptionHandler
Implemente um manipulador global de exceções utilizando @RestControllerAdvice para interceptar erros e retornar a estrutura ErrorResponse acompanhada do código HTTP correspondente.
•	400 Bad Request: Mapeado para erros de validação de DTOs (@Valid / MethodArgumentNotValidException).
•	404 Not Found: Mapeado para recursos não localizados (EntityNotFoundException).
•	409 Conflict: Mapeado para violações de unicidade e dados duplicados (ConflictException).
•	400 Bad Request (BusinessException): Mapeado para regras gerais de negócio violadas.
> Etapa 6 — Atualizar os Controllers com Exceções Customizadas
Atualize os controladores REST para que utilizem as exceções criadas em pontos estratégicos das operações.
Caminho: Arquivo:
src/main/java/com/deliverytech/controller/RestauranteController.java
Trecho 1 — Buscar Restaurante por ID com EntityNotFoundException:
@GetMapping("/{id}")
public ResponseEntity<RestauranteDTO> buscarPorId(@PathVariable Long id) {
    RestauranteDTO restaurante = service.buscarPorId(id)
        .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
    return ResponseEntity.ok(restaurante);
}

Trecho 2 — Criar Restaurante com verificação de duplicidade e ConflictException:
@PostMapping
public ResponseEntity<RestauranteDTO> criar(@Valid @RequestBody RestauranteDTO dto) {
    if (service.existePorNome(dto.getNome())) {
        throw new ConflictException("Restaurante", "nome " + dto.getNome());
    }
    RestauranteDTO criado = service.salvar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(criado);
}

> Etapa 7 — Executar os Testes de Validação e Erros no Postman
Realize as requisições no Postman para verificar se a API retorna as validações e os códigos de erro conforme o esperado.
Teste 1 — Nome vazio ao criar restaurante
•	Método: POST
•	Endpoint: /api/restaurantes
// Requisição Body (JSON):
{
  "nome": "",
  "telefone": "(11) 98765-4321",
  "cep": "01234-567",
  "endereco": "Av. Paulista, 1000",
  "categoria": "ITALIANA"
}

// Resultado Esperado: 400 Bad Request
{
  "timestamp": "2023-11-10T14:30:45.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/restaurantes",
  "details": {
    "nome": "Nome é obrigatório"
  }
}

Teste 2 — Preço negativo no produto
•	Método: POST
•	Endpoint: /api/produtos
// Requisição Body (JSON):
{
  "nome": "Pizza Margherita",
  "descricao": "Pizza tradicional italiana com tomate e manjericão",
  "preco": -10.00,
  "restauranteId": 1,
  "categoria": "PIZZA"
}

// Resultado Esperado: 400 Bad Request
{
  "timestamp": "2023-11-10T14:35:12.456Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/produtos",
  "details": {
    "preco": "Preço deve ser maior que zero"
  }
}

Teste 3 — ID inexistente ao buscar restaurante
•	Método: GET
•	Endpoint: /api/restaurantes/999
// Resultado Esperado: 404 Not Found
{
  "timestamp": "2023-11-10T14:40:22.789Z",
  "status": 404,
  "error": "Not Found",
  "message": "Restaurante com ID 999 não encontrado",
  "path": "/api/restaurantes/999"
}

Teste 4 — Pedido sem itens
•	Método: POST
•	Endpoint: /api/pedidos
// Requisição Body (JSON):
{
  "clienteId": 1,
  "restauranteId": 2,
  "itens": [],
  "enderecoEntrega": "Rua das Flores, 123",
  "cepEntrega": "04567-890",
  "formaPagamento": "CARTAO_CREDITO"
}

// Resultado Esperado: 400 Bad Request
{
  "timestamp": "2023-11-10T14:45:33.147Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/pedidos",
  "details": {
    "itens": "Pedido deve ter pelo menos um item"
  }
}

Teste 5 — Telefone inválido
•	Método: POST
•	Endpoint: /api/restaurantes
// Requisição Body (JSON):
{
  "nome": "Restaurante Japonês",
  "telefone": "123",
  "cep": "01234-567",
  "endereco": "Av. Paulista, 1000",
  "categoria": "JAPONESA"
}

// Resultado Esperado: 400 Bad Request
{
  "timestamp": "2023-11-10T14:50:18.258Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/restaurantes",
  "details": {
    "telefone": "Telefone inválido"
  }
}

Teste 6 — Nome duplicado
•	Método: POST
•	Endpoint: /api/restaurantes
// Requisição Body (JSON):
{
  "nome": "Pizzaria Napoli",
  "telefone": "(11) 98765-4321",
  "cep": "01234-567",
  "endereco": "Av. Paulista, 1000",
  "categoria": "ITALIANA"
}

// Resultado Esperado: 409 Conflict
{
  "timestamp": "2023-11-10T14:55:42.369Z",
  "status": 409,
  "error": "Conflict",
  "message": "Restaurante com nome Pizzaria Napoli já existe",
  "path": "/api/restaurantes"
}

Teste 7 — CEP inválido
•	Método: POST
•	Endpoint: /api/restaurantes
// Requisição Body (JSON):
{
  "nome": "Restaurante Mineiro",
  "telefone": "(11) 98765-4321",
  "cep": "123",
  "endereco": "Av. Paulista, 1000",
  "categoria": "BRASILEIRA"
}

// Resultado Esperado: 400 Bad Request
{
  "timestamp": "2023-11-10T15:00:55.741Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validação falhou",
  "path": "/api/restaurantes",
  "details": {
    "cep": "CEP inválido"
  }
}

> Resultado final do encontro
A aplicação passa a contar com validação automática de dados na camada DTO e um manipulador global de exceções que intercepta erros e os devolve no padrão padronizado RFC 7807 com as devidas respostas HTTP (400, 404, 409 e 500).
> Checklist do encontro
•	[X] Exceções BusinessException, EntityNotFoundException e ConflictException criadas.
•	[X] Validadores customizados CEPValidator e TelefoneValidator implementados.
•	[X] DTOs anotados com Bean Validation.
•	[X] Estrutura ErrorResponse (RFC 7807) criada.
•	[X] GlobalExceptionHandler configurado.
•	[X] Controllers atualizados.
•	[X] 7 cenários de teste validados no Postman.
## Encontro 16 — Segurança e Autenticação com Spring Security e JWT
> Objetivo da prática
Implementar segurança, autenticação e autorização stateless na API REST de delivery com Spring Security e JSON Web Tokens (JWT). A prática envolve a proteção de rotas privadas e a liberação de rotas públicas de autenticação e documentação.
> Mapeamento de pacotes e classes
•	Configuração: com.deliverytech.config.SecurityConfig — Configura as regras de segurança e o filtro JWT.
•	Autenticação: com.deliverytech.auth.AuthController — Controlador dos endpoints de autenticação.
•	Segurança JWT: com.deliverytech.security.JwtUtil — Classe utilitária para geração e validação de tokens.
•	Filtro: com.deliverytech.security.JwtAuthenticationFilter — Filtro para interceptar e validar requisições.
•	Serviço de Usuário: com.deliverytech.service.UserDetailsServiceImpl — Serviço para carregar dados de usuário do banco.
> Etapa 1 — Abrir o Projeto no VS Code
Instruções:
1. Abra o terminal e navegue até a pasta do projeto executando:
code pratica6_delivery

2. Caso prefira, abra o VS Code e utilize o menu File -> Open Folder e selecione a pasta do projeto (ex: encontro01_delivery ou DeliveryTech).
> Etapa 2 — Criar DTO para Login (AuthRequest)
Caminho: Arquivo:
src/main/java/com/deliverytech/dto/AuthRequest.java
package com.deliverytech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 3, message = "Senha deve ter pelo menos 3 caracteres")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

> Etapa 3 — Criar DTO de Resposta com Token (AuthResponse)
Caminho: Arquivo:
src/main/java/com/deliverytech/dto/AuthResponse.java
package com.deliverytech.dto;

public class AuthResponse {

    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

> Etapa 4 — Criar Classe Utilitária JwtUtil
Caminho: Arquivo:
src/main/java/com/deliverytech/security/JwtUtil.java
package com.deliverytech.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Gera um token JWT para o usuário
    public String gerarToken(String email) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Valida um token JWT e retorna as claims
    public Claims validarToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null; // Token inválido
        }
    }

    // Extrai o email (subject) do token
    public String getEmailFromToken(String token) {
        Claims claims = validarToken(token);
        return claims != null ? claims.getSubject() : null;
    }
}

> Etapa 5 — Implementar UserDetailsServiceImpl
Caminho: Arquivo:
src/main/java/com/deliverytech/service/UserDetailsServiceImpl.java
package com.deliverytech.service;

import com.deliverytech.model.User;
import com.deliverytech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getSenha())
            .authorities("USER") // Ou use as roles do usuário
            .build();
    }
}

> Etapa 6 — Inicializar Usuário de Teste (DataInitializer)
Caminho: Arquivo:
src/main/java/com/deliverytech/config/DataInitializer.java
package com.deliverytech.config;

import com.deliverytech.model.User;
import com.deliverytech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe um usuário admin
        if (!userRepository.existsByEmail("admin@delivery.com")) {
            // Cria um usuário admin com senha codificada
            User user = User.builder()
                .email("admin@delivery.com")
                .senha(passwordEncoder.encode("123"))
                .nome("Administrador")
                .build();

            userRepository.save(user);
            System.out.println("Usuário admin criado com sucesso!");
        }
    }
}

> Etapa 7 — Implementar AuthController
Caminho: Arquivo:
src/main/java/com/deliverytech/auth/AuthController.java
package com.deliverytech.auth;

import com.deliverytech.dto.AuthRequest;
import com.deliverytech.dto.AuthResponse;
import com.deliverytech.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthRequest request) {
        try {
            // Tenta autenticar com as credenciais fornecidas
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            // Se a autenticação for bem-sucedida, gera um token JWT
            String email = authentication.getName();
            String token = jwtUtil.gerarToken(email);

            // Retorna o token na resposta
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            // Retorna erro 401 se as credenciais forem inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

> Etapa 8 — Configurar Segurança em SecurityConfig
Observação: Os próximos slides apresentam partes da mesma classe. Abaixo está o código reunido na ordem em que aparece nos slides.
Caminho: Arquivo:
src/main/java/com/deliverytech/config/SecurityConfig.java
package com.deliverytech.config;

import com.deliverytech.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // Adiciona o filtro JWT antes do filtro padrão de autenticação
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

> Etapa 9 — Executar a Aplicação
No terminal do VS Code, inicie a aplicação com o Maven Wrapper:
./mvnw spring-boot:run

> Etapa 10 — Testar Autenticação e Acesso no Postman
1. Testar Login (Obtenção do Token JWT):
•	Método: POST
•	URL: http://localhost:8080/api/auth/login
•	Header: Content-Type: application/json
// Body (raw JSON):
{
  "email": "admin@delivery.com",
  "senha": "123"
}

// Resposta esperada (Status 200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBkZWxpdmVyeS5jb20iLCJpYXQiOjE2MzQ1Njc4OTAsImV4cCI6MTYzNDY1NDI5MH0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}

2. Copiar o Token retornado (sem as aspas).
3. Acessar Rota Protegida com Token Válido:
•	Método: GET
•	URL: http://localhost:8080/api/clientes
•	Header: Authorization: Bearer <seu_token_aqui>
Resultado Esperado: Status 200 OK com o retorno dos dados da API de clientes.
4. Acessar Rota Protegida Sem Token ou com Token Inválido:
•	Método: GET
•	URL: http://localhost:8080/api/clientes
Sem o cabeçalho Authorization ou com token adulterado.
Resultado Esperado: Status 401 Unauthorized ou 403 Forbidden.
> Resultado final do encontro
A API REST passa a ter todas as rotas privadas devidamente protegidas por autenticação JWT stateless, enquanto as rotas públicas de autenticação (/api/auth/**), documentação (/swagger-ui/**) e console H2 (/h2-console/**) permanecem acessíveis.
> Checklist do encontro
•	[X] Classes AuthRequest e AuthResponse criadas.
•	[X] Utilitário JwtUtil configurado com chave secreta e expiração.
•	[X] UserDetailsServiceImpl implementado para autenticação JPA.
•	[X] DataInitializer populando o usuário admin inicial.
•	[X] AuthController expondo o endpoint POST /api/auth/login.
•	[X] SecurityConfig configurado com sessão stateless e rotas públicas/privadas.
•	[X] Testes de login e acesso a rotas protegidas validados no Postman.
## Encontro 18 — Documentação com Swagger
> Objetivo da prática
Adicionar a biblioteca springdoc-openapi ao projeto Spring Boot 3.5.3 (com Java 21) para gerar automaticamente a documentação técnica interativa da API REST no formato OpenAPI 3 e disponibilizar a interface Swagger UI no navegador.
> Etapa 1 — Adicionar Dependência do springdoc-openapi
Instruções:
1. Abra o arquivo pom.xml localizado na raiz do projeto no VS Code.
2. Adicione a dependência dentro da tag <dependencies>:
Caminho: Arquivo:
pom.xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>

3. Salve o arquivo para que o Maven faça o download automatizado do pacote.
> Etapa 2 — Liberar Endpoints do Swagger no SecurityConfig
Abra a classe de configuração de segurança do projeto e garanta a liberação pública dos caminhos do Swagger.
Caminho: Arquivo:
src/main/java/com/deliverytech/config/SecurityConfig.java
.requestMatchers(
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**"
).permitAll()

> Etapa 3 — Personalizar com OpenAPIConfig
Crie uma classe de configuração para definir as informações gerais da documentação como título, versão e descrição.
Caminho: Arquivo:
src/main/java/com/deliverytech/config/OpenAPIConfig.java
package com.deliverytech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("API DeliveryTech")
            .version("1.0")
            .description("Documentação da API REST para o sistema de delivery."));
    }
}

> Etapa 4 — Adicionar Anotações de Documentação nos Controllers
Utilize as anotações do pacote io.swagger.v3.oas.annotations para documentar os controladores e métodos:
•	@Tag: Agrupa e categoriza os endpoints na interface gráfica.
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")

•	@Operation: Descreve o objetivo e resumo de um método/endpoint específico.
@Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados")

•	@ApiResponse: Documenta as respostas e códigos HTTP retornados.
@ApiResponse(responseCode = "200", description = "Clientes encontrados")

> Etapa 5 — Rodar a Aplicação
No terminal do VS Code, execute o comando:
./mvnw spring-boot:run

> Etapa 6 — Acessar e Testar no Swagger UI
1. Abra o navegador e acesse a URL da interface Swagger UI:
http://localhost:8080/swagger-ui.html (ou http://localhost:8080/swagger-ui/index.html)
2. Para visualizar a especificação OpenAPI em formato JSON:
http://localhost:8080/v3/api-docs
3. Passos para executar requisições interativas via Swagger UI:
•	1. Expansão: Clique sobre o endpoint desejado para expandir suas opções.
•	2. Interatividade: Clique no botão 'Try it out'.
•	3. Parâmetros: Preencha os parâmetros e dados solicitados.
•	4. Execução: Clique em 'Execute' e analise o código de retorno, headers e body JSON.
> Resultado final do encontro
Documentação técnica completa e interativa gerada automaticamente a partir do código fonte, permitindo que desenvolvedores e testadores visualizem e executem todos os endpoints da API pelo navegador.
> Checklist do encontro
•	[X] Dependência springdoc-openapi-starter-webmvc-ui adicionada ao pom.xml.
•	[X] End-points do Swagger liberados na SecurityConfig.
•	[X] Classe OpenAPIConfig criada com título e versão.
•	[X] Anotações @Tag, @Operation e @ApiResponse incluídas nos controllers.
•	[X] Aplicação iniciada com sucesso.
•	[X] Swagger UI acessado e testado em http://localhost:8080/swagger-ui.html.
## Encontro 20 — Testes Automatizados com Spring Boot
> Objetivo da prática
Implementar testes automatizados de integração/controller para a API de clientes utilizando JUnit e MockMvc do ecossistema Spring Boot, validando comportamentos de sucesso e rejeição de entradas inválidas em isolamento.
> Estrutura do projeto de testes
•	src/test/java/com/deliverytech/: Local onde devem ser criadas as classes de teste e configurações de teste.
•	Classe de teste de controller: src/test/java/com/deliverytech/controller/ClienteControllerTest.java
•	Configuração de segurança para testes: src/test/java/com/deliverytech/config/TestSecurityConfig.java
> Etapa 1 — Abrir o Projeto e Verificar Estrutura
Instruções:
1. Abra o terminal e acesse a pasta do projeto:
code deliverytech

2. Verifique a presença das pastas src/main/java e src/test/java.
3. Execute o projeto para garantir que o ambiente está funcional antes de criar os testes:
./mvnw spring-boot:run

> Etapa 2 — Criar Classe de Configuração para Testes (TestSecurityConfig)
Crie a classe TestSecurityConfig para desabilitar a proteção do Spring Security durante a execução dos testes isolados com MockMvc.
Caminho: Arquivo:
src/test/java/com/deliverytech/config/TestSecurityConfig.java
package com.deliverytech.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }
}

> Etapa 3 — Criar e Implementar ClienteControllerTest
Observação: Os próximos slides apresentam partes da mesma classe. Abaixo está o código reunido na ordem em que aparece nos slides.
Caminho: Arquivo:
src/test/java/com/deliverytech/controller/ClienteControllerTest.java
package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String json = "{\"nome\":\"Alexandre\",\"email\":\"alexandre@teste.com\"}";

        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarClienteComCpfInvalido() throws Exception {
        String json = "{\"nome\":\"Alexandre\",\"cpf\":\"000\"}";

        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }
}

> Etapa 4 — Validar Conteúdo do JSON da Resposta
Exemplo de verificação de valores dos campos do JSON retornado via jsonPath:
mockMvc.perform(get("/api/clientes/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.nome").value("Alexandre"))
    .andExpect(jsonPath("$.cpf").value("12345678900"));

> Etapa 5 — Executar os Testes Automatizados com Maven
Instruções de execução:
1. Executar todos os testes da aplicação:
./mvnw test

2. Executar apenas a classe de testes de cliente:
mvn test -Dtest=ClienteControllerTest

> Resultado final do encontro
Suíte de testes automatizados com JUnit e MockMvc validando os contratos de criação e busca de clientes na API, garantindo retornos com status 201 Created para cenários válidos e status 400 Bad Request para validações com erro.
> Checklist do encontro
•	[X] Classe TestSecurityConfig criada em src/test/java/com/deliverytech/config.
•	[X] Classe ClienteControllerTest criada com anotações @SpringBootTest e @AutoConfigureMockMvc.
•	[X] Teste deveCriarClienteComSucesso executado e aprovado.
•	[X] Teste naoDeveCriarClienteComCpfInvalido executado e aprovado.
•	[X] Validações com jsonPath testadas.
•	[X] Comando ./mvnw test executado com sucesso no terminal.
## Encontro 22 — Monitoramento, Logging e Observabilidade
> Objetivo da prática
Tornar a aplicação observável em tempo real implementando monitoramento com Spring Boot Actuator, métricas via Micrometer e logs com SLF4J/Logger na camada de controle da API de delivery.
> Etapa 1 — Abrir o Projeto no VS Code
No terminal, navegue até a pasta do projeto:
cd ~/projetos
code DeliveryTech

> Etapa 2 — Adicionar o Spring Boot Actuator no pom.xml
Caminho: Arquivo:
pom.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

> Etapa 3 — Configurar Propriedades de Monitoramento e Logs
Caminho: Arquivo:
src/main/resources/application.properties
# Expor todos os endpoints do Actuator
management.endpoints.web.exposure.include=*

# Mostrar detalhes do /actuator/health
management.endpoint.health.show-details=always

# Nível de log da aplicação
logging.level.root=INFO
logging.level.com.deliverytech=DEBUG

> Etapa 4 — Adicionar Logging com SLF4J no ClienteController
Observação: Os próximos slides apresentam partes da mesma classe. Abaixo está o código reunido na ordem em que aparece nos slides.
Caminho: Arquivo:
src/main/java/com/deliverytech/controller/ClienteController.java
package com.deliverytech.controller;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.model.Cliente;
import com.deliverytech.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        logger.info("Cadastro de cliente iniciado: {}", request.getEmail());
        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .ativo(true)
                .build();
        Cliente salvo = clienteService.cadastrar(cliente);
        logger.debug("Cliente salvo com ID {}", salvo.getId());
        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        logger.info("Listando todos os clientes ativos");
        return clienteService.listarAtivos().stream()
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return clienteService.buscarPorId(id)
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Cliente com ID {} não encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        logger.info("Atualizando cliente ID: {}", id);
        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();
        Cliente salvo = clienteService.atualizar(id, atualizado);
        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}

> Etapa 5 — Executar a Aplicação
No terminal do VS Code, inicie o servidor:
./mvnw spring-boot:run

> Etapa 6 — Acessar e Testar Endpoints de Monitoramento e Logs
URLs para acesso e verificação no navegador ou Postman:
•	Health Endpoint: http://localhost:8080/actuator/health (Verifica disponibilidade e status de saúde da aplicação)
•	Metrics Endpoint: http://localhost:8080/actuator/metrics (Exibe métricas de sistema, JVM, memória e requisições)
•	Loggers Endpoint: http://localhost:8080/actuator/loggers (Permite consultar e alterar os níveis de log em tempo real)
•	Status Endpoint: http://localhost:8080/api/clientes/status (Retorna o status online e registra as informações de CPU no console)
> Resultado final do encontro
Aplicação com observabilidade ativada em tempo real, emitindo logs estruturados nos níveis INFO, DEBUG e WARN durante o ciclo de vida das requisições e expondo métricas via endpoints do Spring Boot Actuator.
> Checklist do encontro
•	[X] spring-boot-starter-actuator adicionado ao pom.xml.
•	[X] Propriedades do Actuator e níveis de log gravados em application.properties.
•	[X] Logger SLF4J instanciado e aplicado no ClienteController.
•	[X] Endpoint /api/clientes/status implementado e testado.
•	[X] Endpoints /actuator/health e /actuator/metrics validados no navegador.

## Encontro 26 — Implementação de Cache para Otimização de Performance
> Objetivo da Prática
Utilizar mecanismo de cache no serviço de listagem de uma aplicação de delivery para armazenar temporariamente resultados de consultas frequentes, reduzindo o tempo de resposta e a carga no banco de dados. Além disso, criar um endpoint para realizar a limpeza manual do cache quando necessário.
> Etapa 1 — Adicionar dependências de Cache no arquivo pom.xml
Para o cache simples padrão do Spring Boot (baseado em ConcurrentHashMap), não é necessária nenhuma dependência adicional. Caso deseje utilizar implementações específicas como Redis ou Ehcache, adicione as dependências correspondentes ao arquivo de configuração do Maven.
Arquivo:
pom.xml
Código:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>

> Etapa 2 — Habilitar o suporte a Cache na classe principal da aplicação
Abra a classe principal da aplicação e adicione a anotação @EnableCaching acima da anotação @SpringBootApplication para ativar o suporte a cache em todo o projeto.
Arquivo:
src/main/java/com/deliverytech/DeliveryTechApiApplication.java
Código:
package com.deliverytech;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class DeliveryTechApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryTechApiApplication.class, args);
    }
}

Observação: Nos slides é apresentada também a variação DeliveryApiApplication.java sob o pacote com.exemplo:
Arquivo alternativo:
src/main/java/com/exemplo/DeliveryApiApplication.java
Código:
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DeliveryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApiApplication.class, args);
    }
}

> Etapa 3 — Implementar Cache e simulação de latência na classe de Serviço
Abra a classe de serviço do cliente e adicione a anotação @Cacheable("clientes") no método de listagem para que os resultados sejam mantidos em memória. Adicione também um método privado simulateDelay() com Thread.sleep(3000) para simular um atraso de 3 segundos no processamento de banco de dados e demonstrar visualmente o ganho de performance obtido com o cache.
Arquivo:
src/main/java/com/deliverytech/service/impl/ClienteServiceImpl.java
Código:
package com.deliverytech.service.impl;

import com.deliverytech.model.Cliente;
import com.deliverytech.repository.ClienteRepository;
import com.deliverytech.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    @Override
    public Cliente atualizar(Long id, Cliente atualizado) {
        return clienteRepository.findById(id)
                .map(c -> {
                    c.setNome(atualizado.getNome());
                    return clienteRepository.save(c);
                }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public void ativarDesativar(Long id) {
        clienteRepository.findById(id).ifPresent(c -> {
            c.setAtivo(!c.getAtivo());
            clienteRepository.save(c);
        });
    }

    private void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

Observação: Os slides apresentam também o seguinte trecho focado no uso da anotação @Cacheable("clientes") no serviço:
Código:
import org.springframework.cache.annotation.Cacheable;
// Outros imports

@Service
public class ClienteService {
       
    @Autowired
    private ClienteRepository repository;
       
    @Cacheable("clientes")
    public List<ClienteDTO> listarTodos() {
       simulateDelay(); // simular demora
       return repository.findAll().stream()
            .map(ClienteDTO::new).toList();
    }
       
    // Outros métodos
}

> Etapa 4 — Criar endpoint para limpeza manual do Cache na classe Controller
Adicione um novo endpoint HTTP GET na classe controller anotado com @CacheEvict(value = "clientes", allEntries = true). Esse método removerá todas as entradas do cache "clientes", forçando uma nova consulta completa ao banco na chamada seguinte.
Arquivo:
src/main/java/com/deliverytech/controller/ClienteController.java
Código:
import org.springframework.cache.annotation.CacheEvict;
// Outros imports

@RestController
@RequestMapping("/api")
public class ClienteController {

    // Outros endpoints

    @CacheEvict(value = "clientes", allEntries = true)
    @GetMapping("/clientes/cache/limpar")
    public ResponseEntity<Void> limparCache() {
       return ResponseEntity.noContent().build();
    }
}

> Etapa 5 — Executar a aplicação
Abra o terminal na pasta raiz do projeto ou utilize o VS Code para iniciar a aplicação.
Opção 1 — Terminal:
./mvnw spring-boot:run

Opção 2 — VS Code:
1. Clique no ícone 'Run' na barra lateral.
2. Selecione 'Run Java'.
3. Escolha a classe principal (DeliveryTechApiApplication ou DeliveryApiApplication).
Aguarde o log de inicialização do Spring Boot confirmar:
Tomcat started on port(s): 8080
> Etapa 6 — Testar a performance do Cache e a limpeza manual
1. Primeira chamada ao endpoint de listagem:
•	URL: http://localhost:8080/api/clientes
•	Método: GET
•	Resultado esperado: Tempo de resposta de aproximadamente 3 segundos (devido ao delay simulado do simulateDelay). Os dados retornados são armazenados no cache 'clientes'.
2. Segunda chamada ao mesmo endpoint de listagem:
•	URL: http://localhost:8080/api/clientes
•	Método: GET
•	Resultado esperado: Resposta instantânea (poucos milissegundos), pois os dados foram recuperados diretamente do cache sem executar o método de listagem ou o banco.
3. Chamada ao endpoint de limpeza de cache:
•	URL: http://localhost:8080/api/clientes/cache/limpar
•	Método: GET
•	Resultado esperado: HTTP Status 204 No Content (sem corpo na resposta). O cache 'clientes' é esvaziado.
4. Nova chamada ao endpoint de listagem após a limpeza:
•	URL: http://localhost:8080/api/clientes
•	Método: GET
•	Resultado esperado: O tempo de resposta volta a ser de aproximadamente 3 segundos na primeira requisição e instantâneo nas chamadas seguintes.
> Resultado final do encontro
A aplicação Spring Boot está com o mecanismo de cache habilitado e funcionando. A consulta repetida de clientes passa a responder em milissegundos e o endpoint de limpeza permite renovar as informações em memória sempre que houver alterações no banco de dados.
> Checklist do encontro
•	Cache habilitado com a anotação @EnableCaching na classe principal.
•	Anotação @Cacheable("clientes") aplicada no método de listagem da classe de serviço.
•	Atraso artificial simulateDelay() inserido para evidenciar os ganhos de performance.
•	Endpoint de limpeza de cache @CacheEvict(value = "clientes", allEntries = true) implementado na controller com retorno HTTP 204.
•	Projeto executado e testes de tempo de resposta realizados com sucesso no navegador ou Postman.
## Encontro 28 — Empacotamento e Containerização com Docker e Docker Compose
> Objetivo da Prática
Empacotar a aplicação Spring Boot em um container Docker, criando um ambiente padronizado, isolado e portátil. Configurar a orquestração multi-container com Docker Compose integrando a API Java ao banco de dados MySQL para execução simplificada de todo o ecossistema.
> Etapa 1 — Gerar o arquivo JAR da aplicação
Antes de construir a imagem Docker, compile e empacote a aplicação utilizando o Maven Wrapper na pasta raiz do projeto.
Comando:
./mvnw clean package -DskipTests

Execute este comando no terminal, na pasta raiz do projeto. O arquivo JAR compilado será gerado dentro da pasta target/.
> Etapa 2 — Criar o arquivo Dockerfile
Crie um arquivo chamado Dockerfile na raiz do projeto para definir as instruções de montagem da imagem Docker.
Arquivo:
Dockerfile
Versão 1 — Configuração simples de runtime:
FROM openjdk:21
VOLUME /tmp
COPY target/delivery-api.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

Observação: Os slides apresentam também uma versão multi-stage do Dockerfile utilizando Maven e Alpine Linux:
Versão 2 — Multi-stage Build:
# Etapa de build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/delivery-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

> Etapa 3 — Construir a Imagem Docker
Execute o comando do Docker para construir a imagem da API com base no Dockerfile criado.
Comando:
docker build -t delivery-api .

Execute este comando no terminal na raiz do projeto. O parâmetro -t define a tag 'delivery-api' e o ponto '.' indica o diretório atual como contexto de build.
> Etapa 4 — Executar o Container individualmente (Opcional/Teste manual)
Teste a execução isolada da API em um container Docker apontando a porta 8080 da máquina hospedeira para a porta 8080 do container.
Comando:
docker run -p 8080:8080 delivery-api

> Etapa 5 — Configurar as propriedades de conexão com o banco MySQL
Ajuste o arquivo de propriedades da aplicação para apontar para o serviço do banco de dados MySQL que será executado via Docker Compose. O hostname do banco deve ser 'db', que é o nome do serviço definido no arquivo docker-compose.yml.
Arquivo:
src/main/resources/application.properties
Código:
spring.datasource.url=jdbc:mysql://db:3306/delivery
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

> Etapa 6 — Criar o arquivo docker-compose.yml
Crie o arquivo docker-compose.yml na raiz do projeto para orquestrar os containers da API e do banco de dados MySQL.
Arquivo:
docker-compose.yml
Versão 1 — Estrutura base com MySQL:
version: '3.8'
services:
  api:
    build: .
    ports:
      - "8080:8080"
  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: delivery
    ports:
      - "3306:3306"

Observação: Os slides apresentam variação de parâmetros e adição de volume para persistência de dados no MySQL:
Versão 2 — Com persistência por volume e perfil ativo:
version: '3.8'
services:
  delivery-api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=default
    restart: unless-stopped
  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: delivery
    ports:
      - "3306:3306"
    volumes:
      - dbdata:/var/lib/mysql

volumes:
  dbdata:

> Etapa 7 — Executar a aplicação com Docker Compose
Suba todo o ambiente multi-container (API e MySQL) através do Docker Compose.
Comando:
docker-compose up --build

Execute este comando no terminal na raiz do projeto. A flag --build força a recompilação da imagem da API antes de iniciar os containers.
> Etapa 8 — Validar o funcionamento e testar os endpoints
1. Acesse a documentação Swagger da API no navegador:
•	URL: http://localhost:8080/swagger-ui.html
2. Execute um teste de criação de pedido chamando o endpoint POST.
3. Execute um teste de listagem de pedidos chamando o endpoint GET e verifique a persistência dos dados no MySQL.
> Etapa 9 — Comandos úteis de monitoramento e manutenção
Lista de comandos apresentados nos slides para diagnosticar, gerenciar e limpar o ambiente Docker:
•	Listar todos os containers (ativos e parados): docker ps -a
•	Ver logs de um container específico: docker logs <nome_container>
•	Acompanhar logs em tempo real: docker logs -f <nome_container>
•	Acessar o terminal interno do container: docker exec -it <nome_container> bash
•	Visualizar o uso de recursos (CPU, Memória): docker stats
•	Reconstruir imagens ignorando o cache: docker-compose build --no-cache
•	Parar e remover os containers da aplicação: docker-compose down
•	Parar containers e remover volumes de dados: docker-compose down -v
•	Remover imagens e containers não utilizados: docker system prune -f
•	Remover volumes não utilizados: docker volume prune -f
Observação: Caso seja necessário instalar o Docker via linha de comando no Windows (PowerShell Administrador):
Comando para download do instalador:
Invoke-WebRequest -Uri "https://desktop.docker.com/win/main/amd64/Docker Desktop Installer.exe" -OutFile "$env:USERPROFILE\Downloads\DockerInstaller.exe"

Comando para instalação silenciosa:
Start-Process -Wait -FilePath "$env:USERPROFILE\Downloads\DockerInstaller.exe" -ArgumentList "install", "--quiet" -Verb RunAs

Comando para habilitar o WSL Ubuntu:
wsl --install -d Ubuntu

> Estrutura de pasta final esperada do projeto
delivery-api/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
│           └── application.properties
├── target/
│   └── delivery-api.jar
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
└── pom.xml

> Resultado final do encontro
A aplicação Spring Boot e o banco de dados MySQL estão totalmente containerizados e orquestrados pelo Docker Compose, podendo ser inicializados em qualquer ambiente com um único comando.
> Checklist do encontro
•	Arquivo Dockerfile criado na raiz do projeto com as instruções de build e runtime.
•	Arquivo JAR compilado via ./mvnw clean package -DskipTests.
•	Arquivo docker-compose.yml configurado com os serviços da API e do banco MySQL.
•	Propriedades no application.properties ajustadas para utilizar o hostname 'db'.
•	Containers inicializados via docker-compose up --build e endpoints validados via Swagger UI.
## Encontro 30 — Automatização de Testes, Build e Deploy com CI/CD
> Objetivo da Prática
Implementar um pipeline automatizado de Integração Contínua (CI) e Entrega Contínua (CD) utilizando o GitHub Actions. O workflow automatizado executará verificação de código, testes unitários e de integração, compilação do projeto e geração do artefato JAR a cada push ou Pull Request enviado ao repositório.
> Etapa 1 — Criar a estrutura de diretórios para o Workflow
No terminal ou no VS Code, crie a pasta oculta .github/workflows na raiz do projeto.
Comando:
mkdir -p .github/workflows

> Etapa 2 — Criar o arquivo de definição do Workflow ci.yml
Dentro do diretório .github/workflows/, crie um novo arquivo chamado ci.yml. Os slides apresentam a construção progressiva do arquivo:
1. Configuração do nome, eventos e job base (Passo 2 do slide):
name: CI Delivery API

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

2. Configuração do ambiente Java 21 Temurin (Passo 3 do slide):
- name: Setup Java
  uses: actions/setup-java@v3
  with:
    java-version: '21'
    distribution: 'temurin' 

3. Execução de Build e Testes com Maven Wrapper (Passo 4 do slide):
- name: Build e Testes
  run: ./mvnw clean verify

4. Geração do artefato JAR (Passo 5 do slide):
- name: Gerar JAR
  run: ./mvnw package -DskipTests

5. Upload do artefato gerado para o GitHub Actions (Passo 6 do slide):
- name: Upload do JAR
  uses: actions/upload-artifact@v3
  with:
    name: delivery-api
    path: target/delivery-api.jar

Código reunido completo do arquivo .github/workflows/ci.yml:
Arquivo:
.github/workflows/ci.yml
Código:
name: CI Delivery API

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Build e Testes
        run: ./mvnw clean verify

      - name: Gerar JAR
        run: ./mvnw package -DskipTests

      - name: Upload do JAR
        uses: actions/upload-artifact@v3
        with:
          name: delivery-api
          path: target/delivery-api.jar

> Etapa 3 — Fazer commit e push das alterações para o GitHub
Adicione os novos arquivos ao Git, crie um commit e envie as alterações para a branch principal no repositório remoto.
Comandos:
git add .
git commit -m "feat: adiciona CI"
git push origin main

> Etapa 4 — Acompanhar a execução do Workflow no GitHub
1. Acesse o repositório do projeto no GitHub pelo navegador.
2. Clique na aba 'Actions' no menu superior.
3. Selecione o workflow chamado 'CI Delivery API'.
4. Clique na execução em andamento para observar o status em tempo real de cada etapa e conferir os logs detalhados.
> Resultados Esperados do Workflow
•	Checkout: Código obtido do repositório com sucesso.
•	Java Setup: Ambiente com JDK 21 Temurin instalado e pronto para execução.
•	Testes: Execução do comando ./mvnw clean verify com status de sucesso.
•	JAR Build / Upload: Arquivo JAR gerado e armazenado como artefato da execução com permanência de até 90 dias no GitHub.
> Estrutura de pasta esperada do repositório
.github/
└── workflows/
    └── ci.yml

> Resultado final do encontro
A pipeline de Integração Contínua está configurada no GitHub Actions. Qualquer alteração ou nova funcionalidade subida para o repositório passará automaticamente por testes unitários e de integração, validação de build e geração do arquivo JAR para implantação.
> Checklist do encontro
•	Estrutura .github/workflows/ criada corretamente.
•	Arquivo ci.yml configurado com os steps checkout, setup-java, build/testes, geração do JAR e upload-artifact.
•	Push realizado na branch main do repositório remoto.
•	Execução do workflow acompanhada na aba Actions do GitHub e todas as etapas concluídas com sucesso.
