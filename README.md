# Delivery Tech API

Sistema de delivery desenvolvido em Java 21 com Spring Boot. Gerencia clientes, restaurantes, produtos e pedidos, com login seguro, monitoramento e entrega automatizada.

## Tecnologias
- Java 21
- Spring Boot 3.2.5 (Web, Data JPA, Security)
- MySQL 8
- Maven
- Docker e Docker Compose
- Prometheus (métricas) e Zipkin (rastreamento)
- Swagger (documentação da API)
- GitHub Actions (integração contínua)

## Funcionalidades
- Cadastro e consulta de clientes, restaurantes, produtos e pedidos
- Autenticação com token JWT (rotas protegidas pedem login)
- Painel de monitoramento com pedidos, receita, memória, CPU e saúde do sistema
- Identificador de rastreio (X-Correlation-ID) em cada requisição
- Carga automática de dados de teste ao iniciar

## Frontend (projeto extracurricular)

O curso pedia apenas a API (backend). O frontend, chamado **Alameda**, não fazia parte do que foi solicitado: foi uma iniciativa minha, criada para consumir a API e ver o sistema funcionando como um produto de verdade, com telas e não só rotas no Swagger.

Ele foi feito em HTML, CSS e JavaScript puros e está na pasta [`frontend/`](frontend/). A página inicial tem:

- Carrossel de destaques
- Busca por restaurante ou prato
- Filtro por categoria
- Lista de restaurantes e de pratos, carregada da API

**Ver o site:** [giuliasamogin.github.io/deliverytech/frontend](https://giuliasamogin.github.io/deliverytech/frontend/)

> **Importante:** o GitHub Pages hospeda apenas o frontend. A API roda localmente, em Docker, então a versão online pode abrir sem restaurantes e pratos. Para ver o sistema completo, suba a API (veja "Como executar") e abra o `frontend/index.html` no navegador.

## Como executar
Pré-requisitos: JDK 21 e Docker Desktop instalados.

1. Clone o repositório:
   `git clone https://github.com/giuliasamogin/deliverytech.git`
2. Entre na pasta do projeto.
3. Gere o JAR (no Windows):
   `.\mvnw.cmd clean package -DskipTests`
4. Suba todos os containers (api, banco, Prometheus e Zipkin):
   `docker-compose up --build`
5. Espere aparecer `Started DeliveryApiApplication` no terminal.
6. Para parar: `Ctrl + C` e depois `docker-compose down`.

## Endereços principais
| Endereço | O que é |
|---|---|
| http://localhost:8080/swagger-ui.html | Documentação e teste das rotas |
| http://localhost:8080/dashboard | Painel de monitoramento |
| http://localhost:8080/actuator/health | Saúde da aplicação e do banco |
| http://localhost:8080/clientes | Lista de clientes (rota pública) |
| http://localhost:9090/targets | Prometheus |
| http://localhost:9411 | Zipkin |

As demais rotas (como restaurantes, produtos e pedidos) exigem login. Use o Swagger para fazer login e clicar em **Authorize**.

## Integração contínua
A cada push na branch `main`, o GitHub Actions (workflow **CI Delivery API**)
