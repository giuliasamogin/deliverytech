# Resumo do Curso — Arquitetura de Sistemas (Qualifica SP/TI)
### Encontros 1 a 10 — projeto DeliveryTech

---

## Encontro 1 — Aula Inaugural

Aula de apresentação: quem é a Fundação FAT, o programa Qualifica SP, o professor, e o conteúdo programático do curso (120h, 30 encontros). O curso todo vai construir, aos poucos, uma API de delivery fictícia chamada **DeliveryTech** — é o "fio condutor" prático de todas as aulas.

**Roteiro geral do curso** (pra você se situar no calendário):
- Módulo 1: fundamentos e estrutura inicial do projeto
- Módulo 2: camada de negócio, segurança e documentação
- Módulo 3: testes, deploy e entregas técnicas
- Módulo 4: integrações, monitoramento e boas práticas

---

## Encontro 2 — Prática 0: Conhecendo o Spring Boot

Primeira prática de verdade: instalar o ambiente (Java 21, VS Code, Git, GitHub) e entender o que é o **Spring Boot**.

**Termos:**
- **Spring Boot** — framework Java que facilita criar APIs e aplicações web. Vem com configurações prontas ("auto configuração"), reduzindo o trabalho manual.
- **JVM (Java Virtual Machine)** — o "motor" que roda o código Java em qualquer computador, independente do sistema operacional.

Essa é a aula onde você criou o projeto pela primeira vez, subiu pro GitHub e testou os endpoints `/health` e `/info` — que já fizemos juntas.

---

## Encontro 3 — Teórica: Spring Boot e Arquitetura MVC

Aula mais densa, cheia de conceito novo. Aqui entram as peças que explicam **por que** seu projeto é organizado do jeito que é.

### O que é o Spring Framework
Uma plataforma pra criar aplicações Java rápido e organizado, com dois diferenciais: **injeção de dependências** e **baixo acoplamento** entre as partes do sistema.

### Principais anotações que você já viu na prática
| Anotação | O que faz |
|---|---|
| `@SpringBootApplication` | marca a classe principal do projeto |
| `@RestController` | classe que responde requisições HTTP com dados (JSON) |
| `@Service` | marca a classe de regra de negócio |
| `@Repository` | marca a classe de acesso ao banco |
| `@Autowired` | injeta dependências automaticamente |

### O fluxo de uma requisição (muito importante — decorar essa sequência)
1. Usuário acessa uma URL (ex: `/clientes`)
2. O **Controller** recebe e encaminha pro **Service**
3. O **Service** aplica as regras de negócio e chama o **Repository**
4. O **Repository** conversa com o banco de dados
5. O Controller devolve a resposta pro usuário

Essa sequência é **exatamente** a estrutura que você já construiu no projeto (Repositories → Services, e os Controllers que estão por vir).

### Injeção de Dependências (Dependency Injection — DI)
Conceito-chave do Spring. Em vez de uma classe **criar** os objetos de que precisa, ela **recebe prontos** de fora — geralmente pelo construtor. Isso é o que você já fez sem perceber:

```java
public class ClienteService {
    private ClienteRepository repository;
    public ClienteService(ClienteRepository repository) {
        this.repository = repository; // recebido de fora, não criado aqui
    }
}
```

Isso deixa o código mais flexível, testável e com menos "dependência rígida" entre as peças — é o **Ioc (Inversão de Controle)**: o Spring é quem decide quando e como criar e "entregar" esses objetos, não você manualmente.

### Arquitetura MVC (Model-View-Controller)
Um padrão que separa a aplicação em 3 responsabilidades:

| Componente | Função | No seu projeto |
|---|---|---|
| **Model** | dados e regras de negócio | suas entidades (`Cliente`, `Produto`...) — usam `@Entity` |
| **View** | o que o usuário vê | HTML (Thymeleaf) ou JSON (seu caso, API pura) |
| **Controller** | recebe pedidos, decide o que fazer | `@RestController` |

**Analogia do slide (bem didática):** pense num restaurante —
- **Model** = a cozinha (dados e regras pra preparar o pedido)
- **View** = o salão (o que o cliente vê e recebe)
- **Controller** = o garçom (recebe pedido, leva pra cozinha, traz resposta)

### Arquitetura em Camadas
Divisão mais ampla que o MVC, com 3 camadas:
- **Apresentação** — interação com o usuário (Controller/View)
- **Negócio** — regras do sistema (Service)
- **Persistência** — acesso a dados (Repository)

**Analogia:** recepção = apresentação, gerente = negócio, arquivo = persistência.

### Princípios SOLID (boas práticas de design de código)
| Letra | Nome | Significado |
|---|---|---|
| S | Single Responsibility | uma classe deve ter só uma responsabilidade |
| O | Open/Closed | aberta pra extensão, fechada pra modificação |
| L | Liskov Substitution | um "filho" pode substituir o "pai" sem quebrar nada |
| I | Interface Segregation | prefira interfaces pequenas e específicas |
| D | Dependency Inversion | dependa de abstrações, não de implementações concretas |

No seu projeto: cada camada (Controller/Service/Repository) tem uma responsabilidade única (S); usar interfaces nos Repositories segue I e D.

### Clean Architecture
Ideia: o **núcleo do sistema** (as regras de negócio) não deve depender de frameworks, bancos de dados ou detalhes externos. As dependências sempre "apontam pro núcleo", nunca o contrário. No seu caso: o Service tem a regra de negócio, independente de como o Controller ou o banco funcionam.

---

## Encontro 4 — Prática 1: Repositories (primeira rodada)

Essa é a aula onde a **Atividade 1 a 5** que a gente já trabalhou juntas apareceu pela primeira vez — Repositories, Services, Controllers, Testes, Documentação. Já cobrimos isso em detalhe nas conversas anteriores.

---

## Encontro 5 — Teórica: Modelagem de Dados e Transações (JPA)

### O que é Modelagem de Dados
Processo de representar coisas do mundo real (clientes, produtos, pedidos) como **entidades Java**, que viram tabelas no banco.

### Tipos de relacionamento entre entidades
| Tipo | Definição | Exemplo | Anotação Java |
|---|---|---|---|
| **1:1** (um para um) | cada registro de A tem exatamente um de B | Cliente ↔ CPF | `@OneToOne` |
| **1:N** (um para muitos) | um registro de A se liga a vários de B | Restaurante → Produtos | `@OneToMany` |
| **N:1** (muitos para um) | vários registros de A apontam pra um só de B | Produto → Restaurante | `@ManyToOne` (você já usa!) |
| **N:N** (muitos para muitos) | vários de A se ligam a vários de B | Pedido ↔ Produto | `@ManyToMany` |

Repara que o `@ManyToOne` que expliquei antes (Produto → Restaurante, Pedido → Cliente) é só **um** dos quatro tipos possíveis — os outros três ainda não apareceram no seu projeto.

### Anotações principais (revisão)
- `@Entity` — transforma a classe numa tabela
- `@Id` — define a chave primária (identificador único)
- `@GeneratedValue` — o ID é gerado automaticamente

### O que é uma Transação
Uma sequência de operações que precisa ser executada **por inteiro** ou **nada** — se algo falhar no meio, tudo é desfeito.

**Exemplo do slide:** cliente faz pedido → sistema salva o pedido → deduz do estoque → cobra o cliente. Se a cobrança falhar mas o pedido já tiver sido salvo, o sistema fica "corrompido" (dado inconsistente). É pra isso que serve o controle de transação.

### Princípios ACID
| Letra | Nome | Significado |
|---|---|---|
| A | Atomicidade | tudo ou nada |
| C | Consistência | o banco sai de um estado válido pra outro válido |
| I | Isolamento | transações paralelas não se afetam |
| D | Durabilidade | depois de confirmado, o dado é permanente |

### `@Transactional`
Anotação do Spring que marca um método como transação. Se algo dentro dele der erro, o Spring desfaz (**rollback**) tudo automaticamente.

```java
@Service
public class PedidoService {
    @Transactional
    public void processarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
        pagamentoService.cobrar(pedido.getCliente());
        entregaService.agendarEntrega(pedido);
    }
}
```
Se `cobrar()` falhar, o `save()` anterior também é desfeito — nada fica "pela metade".

---

## Encontro 6 — Prática 2: Repositories (Spring Data JPA)

Essa aula detalha a implementação dos Repositories que você já fez — `ClienteRepository`, com métodos como `findByEmail`, `existsByEmail`, `findByAtivoTrue`. É a base técnica que a gente já revisou em detalhe.

---

## Encontro 7 — Teórica: Metodologias Ágeis (Scrum) — conectado ao projeto

Essa aula muda de assunto: sai da parte técnica do Java e entra em **como equipes de desenvolvimento se organizam**.

### Por que "Ágil"?
Métodos tradicionais (Waterfall/cascata) definem tudo no início e seguem uma sequência rígida — problema: o mundo muda, requisitos mudam, e detectar erros só no final custa caro. O **Manifesto Ágil** (2001) propôs 4 valores:
1. Indivíduos e interações mais que processos e ferramentas
2. Software funcionando mais que documentação extensa
3. Colaboração com o cliente mais que negociação de contrato
4. Responder a mudanças mais que seguir um plano rígido

### Scrum — o framework mais usado
Organiza o trabalho em **Sprints** (ciclos curtos, 1-4 semanas). Papéis:
- **Product Owner** — prioriza o que precisa ser feito, "voz do cliente"
- **Scrum Master** — facilita o processo, remove obstáculos
- **Time de Desenvolvimento** — quem constrói

**Eventos do Scrum:**
- **Sprint Planning** — planeja o que será feito na Sprint
- **Daily Scrum** — reunião diária de 15min ("o que fiz ontem, o que farei hoje, tenho impedimentos?")
- **Sprint Review** — demonstra o que foi entregue
- **Sprint Retrospective** — reflete sobre o que melhorar

**Artefatos:**
- **Product Backlog** — lista de tudo que pode ser necessário no produto
- **Sprint Backlog** — itens escolhidos pra Sprint atual
- **Incremento** — o resultado pronto e utilizável de cada Sprint

### Kanban (alternativa/complemento ao Scrum)
Quadro visual com colunas (A Fazer / Em Andamento / Concluído), sem ciclos fixos de tempo. Limita quantas tarefas podem estar "em andamento" ao mesmo tempo (WIP).

### Conexão com seu projeto
O slide usa exatamente o trabalho que você fez (Repositories, Spring Data JPA) como exemplo de "Incremento de Sprint" — cada Repository implementado é um pedaço de valor entregue, testável e íntegro. A ideia de "User Story" também aparece:
> "Como desenvolvedor, quero criar repositórios para as entidades principais para implementar operações CRUD de forma eficiente."

Isso é só pra você entender que o **trabalho técnico** que você fez nas últimas aulas pode ser "traduzido" pra linguagem de gestão de projetos — é assim que equipes reais planejam o trabalho.

---

## Encontro 8 — Prática 3: Services e Controllers REST

Aqui está a origem dos Services que revisamos juntas (`ClienteService`, `PedidoService`, etc.) e o início dos Controllers.

**Contexto do slide:** a DeliveryTech já tem os Repositories prontos, mas ainda não expõe isso pra fora — falta a camada de Service (regras de negócio) e Controller (endpoints REST).

**Tarefas da atividade:**
- `ClienteService`: cadastrar, buscar, atualizar, **ativar/desativar** clientes (isso confirma o que corrigimos — "ativar/desativar" é literalmente o que o método `inativar()` devia fazer de verdade!)
- `RestauranteService`: cadastro, busca, atualização, **cálculo de taxa de entrega**
- `ProdutoService`: gerenciar disponibilidade e filtragem por restaurante
- `PedidoService`: criar, buscar, cancelar pedidos com regras de negócio complexas

**Conceito novo: DTO (Data Transfer Object)**
Uma classe usada **só pra transportar dados** entre camadas (por exemplo, entre o Controller e quem faz a requisição), separando o que é "exposto pra fora" do que é a entidade "interna" do banco. Isso evita expor detalhes internos da entidade diretamente na API.

---

## Encontro 9 — Teórica: API REST e Documentação com Swagger

### O que é uma API REST
Um estilo de arquitetura pra construir serviços web, com regras como: cada recurso tem uma URL própria, usa os verbos HTTP corretos (`GET`, `POST`, `PUT`, `DELETE`), e as respostas são estruturadas (geralmente JSON).

### Swagger / OpenAPI
Ferramenta que **documenta automaticamente** sua API, gerando uma interface visual e interativa (o "Swagger UI") onde qualquer pessoa pode ver todos os endpoints disponíveis, testar diretamente pelo navegador, sem precisar de Postman.

**Principais anotações do Swagger:**
| Anotação | Função |
|---|---|
| `@Operation` | descreve o que um endpoint faz |
| `@ApiResponses` | documenta as possíveis respostas (sucesso, erro) |
| `@Tag` | agrupa endpoints por domínio/módulo (ex: todos os de Cliente numa "aba") |
| `@Parameter` | documenta parâmetros de rota, query ou cabeçalho |
| `@Schema` | documenta campos de um DTO |

### Padronização de respostas
A aula recomenda criar uma "classe wrapper" (envelope) padrão pra todas as respostas da API, garantindo consistência — por exemplo, sempre incluir campos como `status`, `dados`, `mensagem`. O mesmo vale pra erros: uma estrutura padrão de erro (`ErrorResponse`) evita que cada endpoint devolva erros de formato diferente.

### Testes de Integração
Diferente dos testes unitários (que testam uma peça isolada), testes de integração verificam o **comportamento completo** da API, simulando requisições reais:
- **MockMvc** — simula chamadas HTTP sem precisar rodar um servidor real
- **TestRestTemplate** — faz chamadas HTTP reais contra a aplicação rodando

---

## Encontro 10 — Prática 4: API REST completa + Swagger

Última prática do bloco: **finalizar** todos os Controllers REST (`RestauranteController`, `ProdutoController`, `PedidoController`, `RelatorioController`) com endpoints completos, e documentar tudo com Swagger.

**Contexto usado no slide (bem didático):** pense no iFood, Uber Eats — eles têm um app mobile, um portal web pra restaurantes, e integrações externas (pagamento, logística) — **todos** consumindo a mesma API REST bem documentada. Isso é exatamente a ideia de "API = motor central" que já conversamos.

**O que cada Controller deve fazer:**
| Controller | Responsabilidades |
|---|---|
| `RestauranteController` | cadastro, edição, listagem com filtros, categorias |
| `ProdutoController` | CRUD completo, busca por categoria, controle de disponibilidade |
| `PedidoController` | criação, acompanhamento, atualização de status, cálculo de valores |
| `RelatorioController` | relatórios de vendas, produtos mais vendidos, análise por período |

**Boas práticas exigidas:**
- Verbos HTTP corretos (`GET` pra ler, `POST` pra criar, `PUT`/`PATCH` pra atualizar, `DELETE` pra remover)
- Códigos HTTP apropriados (200 OK, 201 Created, 404 Not Found, etc.)
- Validação de entrada com `@Valid` (Bean Validation)
- Documentação Swagger completa em cada endpoint

---

## 🗺️ Panorama geral: onde cada aula te levou

```
Encontro 1  → Boas-vindas, visão geral do curso
Encontro 2  → Ambiente + primeiro contato com Spring Boot
Encontro 3  → Teoria: Spring Boot, MVC, SOLID, Clean Architecture
Encontro 4  → Prática: Repositories (1ª vez)
Encontro 5  → Teoria: Modelagem JPA, relacionamentos, transações (ACID)
Encontro 6  → Prática: Repositories (Spring Data JPA, aprofundado)
Encontro 7  → Teoria: Scrum/Ágil (gestão do projeto)
Encontro 8  → Prática: Services + início dos Controllers  ← você está por aqui
Encontro 9  → Teoria: API REST + Swagger/OpenAPI
Encontro 10 → Prática: Controllers REST completos + documentação Swagger
```

Você já cobriu, na prática, os Encontros 4 a 8 (Repositories e Services do seu projeto DeliveryTech estão prontos e compilando). O próximo passo natural — que também é a Atividade 3 do enunciado que vimos — são os **Controllers REST**, que conectam com o conteúdo dos Encontros 9 e 10 (Swagger).
