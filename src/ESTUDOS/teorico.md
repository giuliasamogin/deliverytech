# RESUMO SLIDES 
Curso: Arquitetura de Sistemas (Programa Qualifica - TIC)   

## Encontro 03

Tema Central: Fundamentos do Spring Boot e Arquitetura MVC para Microsserviços em Sistemas de Delivery   
PPTX

Objetivo: Capacitar o estudante para criar autonomamente o ambiente de desenvolvimento de um microsserviço com Spring Boot.   
PPTX

1. > Fundamentos do Spring Boot
O que é e Vantagens
Conceito: O *Spring Boot* é uma plataforma baseada em Java criada para simplificar o desenvolvimento de aplicações web e APIs através da autoconfiguração e do baixo acoplamento.   
PPTX

Produtividade: Elimina grande parte da configuração manual ao disponibilizar definições pré-configuradas e servidores embutidos (como o Tomcat).   
PPTX

Integrabilidade: Facilita a integração com bases de dados, mecanismos de segurança e rotinas de testes.   
PPTX

Anotações Principais (Annotations)
@SpringBootApplication: Identifica e marca a classe principal responsável pelo arranque da aplicação.   
PPTX

@RestController: Define uma classe responsável por receber e responder a requisições HTTP.   
PPTX

@Service: Identifica a classe que contém as regras de negócio do sistema.   
PPTX

@Repository: Marca a classe que executa a gestão e o acesso à base de dados.   
PPTX

@Autowired: Injeta dependências de forma automática gerida pelo Spring.   
PPTX

Injeção de Dependências (DI) e IoC
Inversão de Controlo (IoC): O próprio framework gere o ciclo de vida dos objetos em vez de serem instanciados manualmente no código.   
PPTX

Injeção de Dependências: Fornece as dependências necessárias a uma classe a partir do exterior, tornando o código modular, desacoplado e fácil de testar.   
PPTX

2. > Arquitetura MVC e Camadas
Componentes da Arquitetura MVC
Model: Representa a lógica de negócio, entidades e regras de dados (ex.: @Entity).   
PPTX

View: Camada visual de interação com o utilizador (páginas HTML com Thymeleaf ou dados estruturados em formato JSON/XML para APIs REST).   
PPTX

Controller: Atua como intermediário, recebendo as requisições HTTP, acionando as regras de negócio e devolvendo a resposta à View.   
PPTX

Arquitetura em Camadas
Camada de Apresentação (Controller / View): Recebe os pedidos dos utilizadores e coordena a exibição das respostas.   
PPTX

Camada de Negócio (Service): Centraliza toda a lógica e regras operacionais do sistema.   
PPTX

Camada de Persistência (Repository): Realiza as operações de leitura e escrita na base de dados (ex.: Spring Data JPA).   
PPTX

Fluxo de Requisição
O utilizador efetua uma requisição via navegador ou aplicação (ex.: GET /restaurantes).   
PPTX

O Controller capta o pedido e invoca o serviço correspondente (Service).   
PPTX

O Service aplica a regra de negócio e solicita a informação à base de dados através do Repository.   
PPTX

Os dados retornam ao Controller, que formata a resposta e a entrega ao utilizador através da View.   
PPTX

3. > Princípios de Design e Boas Práticas
Princípios SOLID:

S (Single Responsibility): Cada classe deve possuir apenas uma única responsabilidade.   
PPTX

Interfaces (I / D): A utilização de interfaces nas camadas de Service e Repository garante que as classes dependam de abstrações e não de implementações diretas.   
PPTX

Clean Architecture: O núcleo da aplicação (regras de negócio) deve ser independente de frameworks, bases de dados ou bibliotecas externas.   
PPTX

Documentação Arquitetural: Recomenda-se o uso de diagramas UML, diagramas Mermaid e registos de decisões arquiteturais (ADR) para facilitar a manutenção da aplicação.   
PPTX

4. > Estrutura Prática do Projeto (delivery_api)
Ferramentas Utilizadas
IDE & Gestão: VSCode, Spring Initializr e Apache Maven (para gestão de dependências via pom.xml).   
PPTX

Base de Dados & Front-End: Banco H2 em memória, motor de templates Thymeleaf e Bootstrap.   
PPTX

Organização de Diretórios
Diretório / Ficheiro	Responsabilidade Principal
model/	
Estrutura dos dados e entidades do domínio (ex.: Cliente.java, Restaurante.java). 
PPTX

repository/	
Interface de comunicação com a base de dados (CRUD via Spring Data JPA). 
PPTX

controller/	
Gestão de rotas, requisições e respostas HTTP (ex.: ClienteController.java). 
PPTX

templates/	
Interface visual em HTML/Thymeleaf (ex.: clientes.html). 
PPTX

DeliveryTechApplication.java	
Classe que inicializa a aplicação Spring Boot. 
PPTX

application.properties	
Ficheiro de configurações do projeto. 
PPTX

data.sql	
Ficheiro opcional para povoar a base de dados com registos iniciais. 
PPTX

## Encontro 05 – Teórica 3: Modelagem de Dados e Transações em Sistemas com Spring Boot e JPA

1. > Tema Central e Objetivos
Tema Central: Modelagem de Dados e Transações em Sistemas com Spring Boot e JPA .

Objetivo: Ensinar a estruturar dados no banco de dados de forma relacional e segura, recorrendo à modelação de entidades, relacionamentos e gestão transacional no ecossistema Spring Boot com Spring Data JPA .

2. >  Introdução à Modelação de Dados
Definição: Processo de representação de elementos do mundo real (ex.: clientes, produtos, pedidos) em entidades Java que são mapeadas para tabelas na base de dados relacional .

Importância: Garante a clareza da arquitetura da aplicação, eficiência no armazenamento dos dados e definição correta das associações .

3. >  Tipos de Relacionamento
1:N (Um para Muitos): Um registo na entidade A associa-se a múltiplos registos na entidade B, mas cada registo de B liga-se apenas a um de A .

Exemplos: Turma → Alunos; Cliente → Pedidos .

N:1 (Muitos para Um): Perspetiva inversa do 1:N. Múltiplos registos de A associam-se a um único registo em B .

Exemplo: Múltiplos Pedidos → Um Cliente .

N:N (Muitos para Muitos): Um registo de A liga-se a múltiplos de B, e um registo de B liga-se a múltiplos de A .

Exemplos: Alunos ↔ Disciplinas; Professor ↔ Turma .

Implementação: Exige a criação de uma tabela intermediária (tabela de junção) na base de dados .

1:1 (Um para Um): Um registo de A está associado a exatamente um registo de B (Ex.: Cliente ↔ CPF) .

4. >  Entidades e Anotações Mapeadas
Uma entidade é uma classe Java anotada com @Entity, representando uma tabela .

Anotações Principais:

@Entity: Mapeia a classe Java para uma tabela na base de dados .

@Id: Define o atributo como chave primária da tabela .

@GeneratedValue: Especifica a estratégia de geração automática do identificador .

@OneToOne, @OneToMany, @ManyToOne, @ManyToMany: Definem a cardinalidade dos relacionamentos entre as entidades .

5. >  Spring Data JPA
Definição: Módulo do Spring que provê abstração sobre o JPA (Java Persistence API) e o Hibernate, eliminando a necessidade de escrita manual de código SQL repetitivo .

Interface JpaRepository: Interface central para operações de acesso aos dados .

Métodos Automáticos Integrados:

findAll(): Procura todos os registos .

save(obj): Guarda ou atualiza um objeto .

deleteById(id): Remove um registo pelo seu ID .

existsByCampo(): Verifica a existência de um registo com base num atributo .

Vantagens: Redução de código boilerplate, padronização, legibilidade e rapidez no desenvolvimento .

6. >  Transações e Princípios ACID
Definição de Transação: Sequência de operações de base de dados executada de forma indivisível (atómica) .

Propriedades ACID:

Atomicidade: Princípio "tudo ou nada". Todas as operações são concluídas com sucesso ou nenhuma é aplicada .

Consistência: A base de dados transita de um estado válido para outro estado válido, respeitando todas as restrições e regras .

Isolamento: Transações simultâneas não interferem no estado umas das outras durante a execução .

Durabilidade: Após a confirmação (commit), as alterações persistem permanentemente, mesmo em caso de falha do sistema .

7. >  Estrutura da Aplicação Prática (Demonstração)
Pedido.java (Entidade): Mapeia a tabela de pedidos; usa @Entity, @Id, @GeneratedValue e a anotação @PrePersist para atribuir automaticamente a data antes do salvamento .

PedidoRepository.java: Interface que estende JpaRepository<Pedido, Long> para prover operações CRUD .

PedidoService.java: Camada de negócio anotada com @Service e @Transactional . Se ocorrer uma exceção durante o processamento, é efetuado rollback automático de todas as operações salvas .

DeliveryTransactionsDemoApplication.java: Ponto de entrada anotado com @SpringBootApplication que inicializa o Spring Boot, configurando a base de dados H2 em memória acessível via http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:deliverydb) .

## Encontro 07 – Teórica 4: Sprint e Entrega da Atividade (Metodologias Ágeis)

1. >  Tema Central e Contextualização
Tema Central: Metodologias Ágeis, Frameworks Scrum e Kanban, Extreme Programming (XP) e Aplicação Prática .

Necessidades Atuais: Exigência de ciclos de inovação curtos, adaptação rápida a requisitos de negócio em constante mudança e feedback contínuo .

Limitações do Modelo Tradicional (Cascata / Waterfall): Fases sequenciais rígidas, documentação excessiva e frequentemente obsoleta, entrega de valor apenas no fim do projeto, resistência estrutural a alterações e deteção tardia de falhas com elevado custo de correção .

2. > O Manifesto Ágil (2001)
4 Valores Fundamentais:

Indivíduos e interações mais que processos e ferramentas .

Software em funcionamento mais que documentação abrangente .

Colaboração com o cliente mais que negociação de contratos .

Responder a mudanças mais que seguir um plano .

Princípios-Chave: Prioridade na satisfação do cliente via entregas frequentes (de 2 a 4 semanas), cooperação diária entre gestores e desenvolvedores, equipas auto-organizadas, ritmo sustentável e simplicidade (maximizar a quantidade de trabalho não feito) .

3. >  Vantagens Demonstradas das Metodologias Ágeis
Aumento de até 300% na taxa de sucesso dos projetos .

Redução média de 37% no tempo de chegada ao mercado (time-to-market) .

Elevação da satisfação do cliente para 71% .

Redução de custos ao corrigir falhas nas fases iniciais (até 100 vezes mais barato do que após o lançamento) .

4. >  Os Conceitos de Iteratividade e Incrementalidade
Desenvolvimento Iterativo: Divisão do projeto em ciclos curtos e repetitivos (iterações) que contêm todas as fases do desenvolvimento (planeamento, análise, código, teste e revisão) .

Desenvolvimento Incremental: O produto é construído parte a parte, adicionando novas funcionalidades utilizáveis a cada ciclo .

5. >  O Framework Scrum
Pilares: Transparência, Inspeção e Adaptação .

Papéis Principais:

Product Owner (PO): Maximiza o valor do produto e gere e prioriza o Product Backlog .

Time de Desenvolvimento: Equipa multifuncional e auto-organizada (3 a 9 membros) responsável por construir o incremento "Pronto" .

Scrum Master: Servo-líder que assegura o cumprimento do Scrum, remove impedimentos e protege a equipa .

Artefatos:

Product Backlog: Lista ordenada e dinâmica de todos os requisitos do produto .

Sprint Backlog: Conjunto de itens selecionados para a Sprint com o respetivo plano de execução .

Incremento: Soma de todos os itens concluídos na Sprint que cumprem a Definition of Done .

Eventos Formalizados (Timeboxed):

Sprint: Ciclo de trabalho fixo de 1 a 4 semanas .

Sprint Planning: Definição do objetivo da Sprint e seleção do Sprint Backlog (máx. 8h para Sprint de 1 mês) .

Daily Scrum: Reunião diária de 15 minutos para sincronizar atividades e identificar bloqueios .

Sprint Review: Demonstração do incremento aos intervenientes e adaptação do Product Backlog (máx. 4h) .

Sprint Retrospective: Análise interna da equipa sobre processos, pessoas e ferramentas para definir melhorias (máx. 3h) .

6. >  Estimativas e Qualidade
User Stories: Formato de Requisito: "Como [papel], eu quero [funcionalidade], para que [benefício]" .

Story Points: Unidade abstrata relativa para estimar complexidade, esforço e incerteza, utilizando comummente a sequência de Fibonacci (1, 2, 3, 5, 8, 13, 21...) e a técnica Planning Poker .

Definição de Pronto (Definition of Done - DoD): Critérios claros de qualidade exigidos para que uma história seja considerada concluída (ex.: revisão de código, testes unitários com cobertura mínima de 80%, documentação atualizada e integração efetuada) .

7. >  Kanban, XP e Outras Abordagens
Kanban: Gestão visual de fluxo contínuo . Princípios: Visualizar o fluxo (quadro), Limitar o Trabalho em Progresso (WIP - Work in Progress) e Gerir o fluxo . Métricas: Lead Time, Cycle Time e Throughput .

Extreme Programming (XP): Enfase em práticas de engenharia de software: Programação em Pares (Pair Programming), Desenvolvimento Orientado a Testes (TDD), Integração Contínua, Refatoração e Design Simples .

Outras Metodologias: Lean Software Development (eliminação de desperdícios), Crystal, SAFe (áagil em grande escala) e Scrumban (híbrido Scrum e Kanban) .

8. >  Conexão ao Projeto Prático Delivery
Mapeamento da criação de repositórios JPA e entidades como histórias técnicas e de negócio dentro de uma Sprint . O trabalho é decomposto em tarefas, estimado com Story Points e validado através da execução da Definition of Done .

## Encontro 09 – Teórica 5: API RESTful e Camada de Negócio

1. > Tema Central e Problemas de Mercado
Tema Central: API RESTful, Camada de Negócio e Documentação com Swagger/OpenAPI .

Problemas Reais Enfrentados: Falta de documentação adequada, falta de padronização nos formatos de resposta, APIs de difícil integração e perda de tempo no diagnóstico de erros simples por programadores e equipas de QA .

2. > Conceito de API REST
REST (Representational State Transfer): Estilo arquitetural para serviços web baseado em:

Recursos identificados por URLs .

Verbos HTTP como métodos de ação (GET, POST, PUT, DELETE) .

Transferência de dados via formatos leves (JSON ou XML) .

Protocolo Stateless (sem retenção de estado do cliente entre requisições no servidor) .

3. > Estrutura Arquitetural de uma API REST
Cliente: Aplicação Web, Mobile ou sistemas externos .

API REST (Controllers): Endpoints HTTP que recebem os pedidos e retornam respostas .

Regras de Negócio (Services): Classes de serviço que implementam a lógica de negócio e as validações .

Banco de Dados: Camada de persistência .

4. > Documentação Interativa com Swagger / OpenAPI
OpenAPI: Especificação padrão da indústria para descrever APIs RESTful de forma neutra em relação à linguagem .

Swagger UI: Interface gráfica gerada automaticamente (http://localhost:8080/swagger-ui.html) para explorar e testar endpoints em tempo real .

Vantagens: Redução de bugs, validação expedita por QA, facilidade de integração e rapidez no acolhimento (onboarding) de novos programadores .

Configuração em Spring Boot: Adição de dependência no pom.xml (ex.: springdoc-openapi), parametrização no application.properties e definição de classe de configuração .

5. > Anotações do Swagger/OpenAPI
@Tag: Agrupa endpoints por domínio ou módulo funcional .

@Operation: Descreve o propósito de um endpoint específico .

@Parameter: Documenta parâmetros de rota (path), consulta (query) ou cabeçalho (header) .

@Schema: Documenta atributos de DTOs, definindo descrições, exemplos e obrigatoriedade .

@ApiResponse / @ApiResponses: Documenta os códigos de resposta HTTP possíveis e a estrutura do corpo retornado .

6. > Validação e Padronização de Respostas
Validações de DTOs (Bean Validation): @NotNull, @NotBlank, @NotEmpty, @Size(min, max), @Min/@Max, @Email, @Pattern, @Past/@Future . O Swagger exibe estas restrições automaticamente .

Resposta Padrão (ApiResponse<T>): Estrutura wrapper uniforme contendo dados da resposta, mensagem e timestamp .

Paginação (PagedResponse<T>): Estruturação padronizada para retorno de grandes coleções de dados contendo metadados da página .

Tratamento Global de Erros: Estrutura clara de erro com mensagens amigáveis e detalhes técnicos de diagnóstico .

7. > Padrões de Códigos HTTP
2XX (Sucesso):

200 OK: Requisição bem-sucedida (GET, PUT, PATCH) .

201 Created: Recurso criado com sucesso (POST) .

204 No Content: Sucesso sem conteúdo no corpo (DELETE) .

4XX (Erro do Cliente):

400 Bad Request: Dados inválidos ou erro de validação .

401 Unauthorized: Requer autenticação .

403 Forbidden: Autenticado, mas sem permissão de acesso .

404 Not Found: Recurso não localizado .

409 Conflict: Conflito de estado (ex.: registo duplicado) .

5XX (Erro do Servidor):

500 Internal Server Error: Exceção não tratada no servidor .

502 Bad Gateway: Erro de resposta num serviço a jusante .

503 Service Unavailable: Serviço temporariamente indisponível .

8. > Testes de Integração
Validam o comportamento completo da API do ponto de vista do cliente .

Ferramentas do Spring Boot: MockMvc (simulação de chamadas HTTP), @SpringBootTest (carregamento do contexto), @AutoConfigureMockMvc e TestRestTemplate .

9. > Aplicação no Mundo Real
Utilizado em aplicações de delivery (iFood, Uber Eats), plataformas de pagamento (Mercado Pago), sistemas logísticos e integração de relatórios de Business Intelligence (BI) .

## Encontro 11 – Teórica 6: Transações e Controle de Concorrência

1. > Tema Central e Conceito de Transação
Tema Central: Transações e Controlo de Concorrência em Banco de Dados com Spring Boot .

Definição de Transação: Unidade lógica de trabalho contendo um conjunto de operações . Princípio indivisível: Ou todas as operações são efetuadas com sucesso, ou o sistema regressa ao estado inicial (rollback) .

Exemplo Prático: Num processo de compra, as etapas (atualizar stock, registar pedido e confirmar pagamento) têm de ocorrer juntas . Se o pagamento falhar, o pedido deve ser cancelado e o stock restaurado .

2. > Propriedades ACID em Detalhe
Atomicidade: A transação é tratada como um bloco único e indivisível .

Consistência: Garante que a transação só leva a base de dados de um estado válido a outro, mantendo restrições e chaves .

Isolamento: Garante a execução transparente e isolada de transações concorrentes .

Durabilidade: Garante a persistência dos dados confirmados (commit) mesmo perante falhas de energia ou do sistema .

3. > Problemas Resultantes da Falta de Controlo de Concorrência
Leitura Suja (Dirty Read): Ocorre quando uma transação lê dados modificados por outra transação que ainda não foram confirmados (uncommitted) e que acabam por sofrer rollback .

Leitura Não Repetível (Non-Repeatable Read): Ocorre quando uma transação lê a mesma linha duas vezes e obtém valores diferentes porque outra transação alterou e confirmou esses dados entre as leituras .

Leitura Fantasma (Phantom Read): Ocorre quando uma consulta executada múltiplas vezes numa transação retorna conjuntos de dados com contagens de linhas diferentes devido a inserções ou remoções confirmadas por outra transação .

4. > Tipos de Controlo de Concorrência
Concorrência Otimista:

Parte do princípio de que os conflitos são raros .

Não bloqueia os registos durante a leitura .

Valida se houve alterações através de um campo de versão (@Version) ou timestamp no momento de salvar .

Recomendada para sistemas com elevada leitura e poucas atualizações concorrentes no mesmo registo .

Concorrência Pessimista:

Parte do princípio de que os conflitos são prováveis .

Bloqueia diretamente os registos na base de dados (locks) no início da transação .

Impede o acesso concorrente aos registos até à conclusão da transação .

Recomendada para cenários de alta escrita e concorrência sobre os mesmos dados .

5. > O Spring e a Anotação @Transactional
Mecanismo: Utiliza Programação Orientada a Aspectos (AOP) para gerir transações de forma declarativa .

Comportamento Padrão de Rollback: O Spring efetua rollback automático para exceções não verificadas (RuntimeException e suas subclasses) . Exceções verificadas (checked exceptions) exigem configuração explícita .

Dependência: Incluído na biblioteca spring-boot-starter-data-jpa .

6. > Níveis de Isolamento no Spring / JDBC
READ_UNCOMMITTED: Nível mais baixo. Permite leituras sujas, não repetíveis e fantasmas .

READ_COMMITTED: Impede leituras sujas, mas permite leituras não repetíveis e fantasmas .

REPEATABLE_READ: Impede leituras sujas e não repetíveis, mas permite leituras fantasmas .

SERIALIZABLE: Nível mais elevado e rigoroso. Previne todos os problemas de concorrência através da execução sequencial/bloqueio total, com perda de desempenho .

Configuração: Defina via @Transactional(isolation = Isolation.READ_COMMITTED) .

7. > Boas Práticas na Gestão de Transações
Aplicar a anotação @Transactional na camada de serviço (@Service), não em controllers ou repositórios .

Evitar invocar métodos transacionais a partir da mesma classe, pois o proxy AOP do Spring não é acionado em chamadas internas .

Manter as transações o mais curtas possível para evitar retenção prolongada de conexões, deadlocks e quebras de desempenho .

Forçar rollback manual programaticamente através de TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() quando necessário .

## Encontro 13 – Teórica 7: Segurança e Autenticação

1. > Tema Central e Importância da Segurança
Tema Central: Segurança e Autenticação em APIs Java Spring Boot com Spring Security e JWT (JSON Web Token) .

Importância: Proteção contra acessos não autorizados, prevenção de vazamento de dados, conformidade com regulamentações (LGPD, GDPR) e garantia de integridade .

2. > Conceitos-Chave
Autenticação: Processo de verificação e confirmação da identidade do utilizador (validação do "quem é") .

Autorização: Processo de verificação das permissões do utilizador autenticado (definição do "o que pode fazer") .

3. > Spring Security e Arquitetura JWT
Spring Security: Framework oficial do ecossistema Spring para gestão de autenticação, autorização e proteção contra vulnerabilidades .

JWT (JSON Web Token - RFC 7519): Padrão aberto e compacto para troca segura de informações assinadas digitalmente .

Arquitetura Stateless: O servidor não armazena sessão na memória, permitindo escalar a aplicação horizontalmente de forma simples .

4. > Fluxo de Funcionamento do JWT
Login: O utilizador envia as credenciais (e-mail e palavra-passe) .

Geração do Token: O servidor valida as credenciais via AuthenticationManager e gera um token JWT assinado .

Envio em Requisições: O cliente envia o token no cabeçalho HTTP Authorization usando o prefixo Bearer <token> .

Validação: Um filtro intercepta a requisição, valida a assinatura e expiração do token, e autoriza o acesso .

5. > Estrutura Interna de um JWT
Header: Contém o tipo do token e o algoritmo de assinatura criptográfica (ex.: {"alg": "HS256", "typ": "JWT"}) .

Payload: Contém as declarações ou dados (claims) do utilizador (ex.: sub, userId, role, exp) .

Signature: Assinatura gerada a partir da combinação do Header codificado, Payload codificado e uma chave secreta mantida no servidor .

6. > Componentes e Bibliotecas de Implementação
Dependências (pom.xml): spring-boot-starter-security, jjwt-api, jjwt-impl e jjwt-jackson .

Entidade de Utilizador: A classe Usuario deve implementar a interface UserDetails do Spring Security .

Criptografia de Palavras-passe: Utilização do algoritmo BCrypt (PasswordEncoder), que gera um salt aleatório para cada palavra-passe, prevenindo ataques de força bruta e rainbow tables .

Classe JwtUtil: Responsável pela criação, extração de dados e validação da expiração do token .

Filtro de Autenticação (JwtAuthenticationFilter): Estende OncePerRequestFilter para interceptar cada requisição HTTP, extrair o token Bearer e preencher o contexto de segurança (SecurityContextHolder) .

7. > Autorização por Perfis (Roles) e DTOs
Perfis Típicos: CLIENTE, RESTAURANTE, ENTREGADOR, ADMIN .

Controlo de Acesso Declarativo: Uso da anotação @PreAuthorize("hasRole('ADMIN')") nos métodos dos controllers .

DTOs de Autenticação: LoginRequest, LoginResponse, RegisterRequest e UserResponse .

Classe SecurityUtils: Utilitário para extrair o utilizador atualmente autenticado a partir do contexto de segurança do Spring .

8. > Exceções Frequentes e Boas Práticas
Exceções Comuns: Token Inválido (assinatura alterada), Token Expirado (tempo limite ultrapassado) e Perfil Não Autorizado (ausência de permissão) . Trato global via @ControllerAdvice .

Boas Práticas Recomendadas:

Nunca armazenar palavras-passe em texto limpo .

Utilizar obrigatoriamente HTTPS para prevenir ataques de interceptação (man-in-the-middle) .

Validar a integridade e expiração do token em todas as operações protegidas .

Armazenar o token de forma segura no cliente (preferencialmente em cookies HttpOnly ou localStorage) .

Depuração de tokens durante o desenvolvimento recorrendo ao Postman e à plataforma [https://jwt.io](https://jwt.io) .



## Encontro 15 — Deploy e Ambientes com CI/CD

Tema central: O encontro apresenta implantação de sistemas, ambientes isolados e CI/CD com Spring Boot, passando da codificação à produção com qualidade e velocidade.

Conceitos fundamentais

> Implantação (Deploy)

É o processo de disponibilizar uma aplicação em um ambiente real para que os usuários finais possam acessá-la. O deploy pode ser manual, quando há intervenção humana em cada etapa; automatizado, quando scripts e ferramentas executam etapas previamente definidas; ou contínuo, quando mudanças no código acionam automaticamente o processo.

> Ambientes de software

São versões isoladas da aplicação usadas em diferentes fases do desenvolvimento. O objetivo é evitar que desenvolvimento e testes afetem diretamente o ambiente utilizado pelos usuários.

> CI/CD

É o conjunto de práticas de automação que integra, testa, empacota e entrega software de maneira frequente e confiável. CI significa Continuous Integration (Integração Contínua). CD pode significar Continuous Delivery (Entrega Contínua) ou Continuous Deployment (Deploy Contínuo).

Importância do deploy

> Entrega de valor

O deploy coloca novas funcionalidades à disposição dos usuários e transforma o trabalho de desenvolvimento em valor efetivamente utilizável.

> Validação real

Permite confirmar o comportamento da aplicação em um ambiente próximo daquele em que os usuários realmente a utilizarão.

> Correções eficientes

Um processo de implantação organizado facilita identificar e corrigir problemas rapidamente.

> Atualizações sem impacto

Estratégias de implantação podem permitir disponibilizar novas versões sem interromper a experiência dos usuários.

Tipos de deploy

> Manual

Realizado por pessoas, por exemplo por meio de FTP ou SSH.

> Automatizado

Executado por scripts ou ferramentas que repetem as etapas de implantação de forma padronizada.

> Blue/Green

Mantém dois ambientes de produção: um recebe a nova versão enquanto o outro permanece disponível. A troca direciona o tráfego para a versão desejada.

> Canary

A nova versão é liberada inicialmente para apenas uma parte dos usuários, permitindo observar o comportamento antes de ampliar a distribuição.

> Rolling

A atualização acontece gradualmente, substituindo partes da aplicação sem precisar interromper todo o sistema.

Ciclo de vida do software

> Codificação

Desenvolvimento das funcionalidades pelos programadores.

> Build

Compilação e empacotamento do código-fonte para gerar uma versão executável/distribuível.

> Testes

Verificação da qualidade e do funcionamento da aplicação.

> Deploy

Disponibilização da aplicação para uso.

> Monitoramento

Acompanhamento de desempenho, comportamento e erros depois da implantação.

Ambientes separados

> Desenvolvimento (Dev)

Ambiente utilizado pelo programador para desenvolver funcionalidades e executar testes locais.

> Teste (QA/Test)

Ambiente destinado a testes realizados por analistas de qualidade ou por testes automatizados.

> Homologação (Staging)

Ambiente de pré-produção utilizado para validação final antes de chegar à produção.

> Produção (Prod)

Ambiente real acessado pelos usuários finais.

> Características de um ambiente

Cada ambiente pode ter configurações específicas, banco de dados independente, infraestrutura dedicada e níveis de acesso diferentes.

Perfis no Spring Boot

> Configuração por ambiente

O Spring Boot permite criar arquivos específicos, como application-dev.yaml e application-prod.yaml, para separar configurações. Um exemplo apresentado usa H2 em memória no desenvolvimento e MySQL em produção.

> Ativação de perfil

O perfil pode ser ativado pela linha de comando com --spring.profiles.active=prod, por variável de ambiente como SPRING_PROFILES_ACTIVE=prod ou programaticamente.

> Por que usar perfis

O perfil ativo determina qual conjunto de configurações será carregado, permitindo que a mesma aplicação se adapte automaticamente ao ambiente em que está sendo executada.

Integração Contínua (CI)

> Definição

CI, ou Continuous Integration, é a prática de integrar frequentemente alterações de diferentes desenvolvedores em um repositório compartilhado.

> Build automatizado

A pipeline compila e empacota o código.

> Testes automatizados

A execução automática verifica qualidade e funcionalidade.

> Análise de código

Ferramentas podem verificar padrões, qualidade e boas práticas.

Entrega e Deploy Contínuos (CD)

> Continuous Delivery

Mantém o código sempre pronto para produção, mas a liberação final pode depender de uma aprovação manual.

> Continuous Deployment

Depois que as etapas automatizadas são aprovadas, o código pode ser enviado automaticamente para produção.

> Diferença essencial

Delivery enfatiza estar pronto para entregar; Deployment automatiza também a disponibilização final.

Ciclo CI/CD em cinco passos

> 1. Commit

O desenvolvedor envia alterações para o repositório.

> 2. CI executa build e testes

O sistema compila e verifica automaticamente a qualidade.

> 3. Artefato

É gerada uma versão executável da aplicação.

> 4. CD entrega no ambiente alvo

A aplicação é implantada em staging ou produção.

> 5. Monitoramento e feedback

O sistema é acompanhado para verificar desempenho e comportamento.

Pipeline e ferramentas

> Versionamento

Git registra e controla as mudanças do código.

> Ferramenta de CI/CD

GitHub Actions, Jenkins ou GitLab CI podem orquestrar o processo.

> Build Tool

Maven ou Gradle compilam e empacotam a aplicação.

> Testes

JUnit, Mockito e outras ferramentas verificam a qualidade.

> Deploy

Heroku, AWS, Docker ou Kubernetes aparecem como tecnologias usadas na implantação.

GitHub Actions

> Pipeline de exemplo

O material mostra um workflow YAML acionado por push, configurando JDK 17, executando Maven e testes.

> Localização

O arquivo YAML do workflow é colocado em .github/workflows/ no repositório.

Docker e containerização

> Dockerfile

Define como construir uma imagem contendo a aplicação Spring Boot. O exemplo copia target/api.jar, expõe a porta 8080 e executa o JAR com java -jar.

> Containerização

Empacota a aplicação e suas necessidades de execução de forma consistente, reduzindo diferenças entre ambientes e o problema conhecido como 'funciona na minha máquina'.

> Comandos principais

docker build constrói a imagem; docker run inicia o container; docker logs acompanha os logs; docker stop interrompe o container.

Variáveis de ambiente e segurança

> Variáveis de ambiente

Permitem fornecer configurações diferentes sem gravar diretamente esses valores no código. O material apresenta SPRING_PROFILES_ACTIVE, DB_URL, DB_USER e DB_PASS.

> Segredos

Senhas e tokens não devem ser armazenados diretamente no código ou em arquivos versionados. O slide recomenda GitHub Secrets, Vault ou AWS KMS.

> Autenticação forte

O material recomenda 2FA nas ferramentas e serviços da pipeline.

> Auditoria

Logs detalhados permitem rastrear operações realizadas no CI/CD.

Monitoramento e observabilidade

> Métricas

Prometheus e Grafana são citados para coletar e visualizar métricas em tempo real.

> Logs

ELK Stack (Elasticsearch, Logstash e Kibana) ou Loki são citados para centralização de logs.

> Rastreamento de erros

Sentry é citado para captura e análise de exceções.

> Alertas

Notificações podem ser enviadas por e-mail, Slack ou Telegram.

Caso da aplicação Delivery

> Problema

Deploy manual, configurações repetidas, bugs frequentes em produção e tempo de entrega lento.

> Solução

Separação entre dev, staging e prod; GitHub Actions para CI/CD; deploy automatizado para Heroku; e testes automáticos antes do deploy.

> Resultado apresentado no material

A aula informa redução de 75% no tempo de entrega e de 60% nos incidentes em produção após a adoção do processo.

Comandos Maven

> mvn clean compile

Limpa e compila o projeto.

> mvn test

Executa os testes.

> mvn package -DskipTests

Gera o pacote sem executar os testes.

> mvn clean package

Limpa, compila, testa e empacota.

> mvn clean install

Executa o ciclo e instala o artefato no repositório local Maven.

Fluxo final demonstrado

> Sequência

Commit no Git → acionamento do GitHub Actions → build e testes com Maven → construção da imagem Docker → deploy no ambiente alvo → health check.

> Health check

É uma verificação automatizada para confirmar se a aplicação está funcionando. O exemplo usa /actuator/health.

Revisão

> Ideia central

Deploy é parte essencial do ciclo de desenvolvimento, e não apenas uma etapa final.

> Ambientes

Ambientes separados trazem controle, segurança e previsibilidade.

> CI/CD

A automação aumenta a velocidade sem comprometer a qualidade quando acompanhada por testes e verificações.

> Spring Boot + Docker

A combinação é apresentada como forma de modernizar e otimizar a entrega.

### Fechamento do encontro

> Para estudar este encontro, concentre-se nas definições, diferenças entre os conceitos, finalidade de cada ferramenta e na sequência prática apresentada nos exemplos. Os exemplos de código dos slides servem para relacionar a teoria à implementação em Spring Boot.


## Encontro 17 — Testes Automatizados com Spring Boot

Tema central: O encontro apresenta a finalidade dos testes automatizados, seus principais tipos, ferramentas, boas práticas e exemplos com JUnit, Mockito, Spring Boot Test, MockMvc e JaCoCo.

Por que testar?

> Prevenção de bugs

Testes ajudam a identificar problemas antes que eles cheguem aos usuários finais.

> Proteção contra regressões

Novas funcionalidades podem ser verificadas para garantir que não quebraram comportamentos existentes.

> CI/CD

Testes automatizados facilitam a integração e entrega contínuas.

> Refatoração segura

Uma boa suíte de testes dá segurança para alterar a estrutura do código mantendo o comportamento esperado.

O que são testes automatizados?

> Definição

São rotinas de código escritas especificamente para verificar se outras partes do código funcionam como esperado — em outras palavras, código que testa código.

> Execução

Podem ser executados automaticamente durante builds, pushes e deploys.

> Feedback

Fornecem retorno rápido sobre a qualidade do software.

Tipos de testes

> Unitário

Testa componentes isolados, como um método ou serviço.

> Integração

Testa a interação entre componentes reais.

> Funcional / end-to-end

Testa o sistema de forma mais completa, representando o fluxo do ponto de vista do sistema.

> Controller/API

Simula requisições HTTP e verifica o comportamento dos controllers.

Pirâmide de testes

> Conceito

A pirâmide apresentada indica muitos testes unitários na base, uma quantidade menor de testes de integração no meio e poucos testes end-to-end no topo. A ideia é equilibrar rapidez e abrangência.

Teste unitário

> Isolamento

Testa uma classe isoladamente, sem depender diretamente de banco de dados, rede ou outros sistemas.

> Mocks

Dependências podem ser substituídas por objetos simulados.

> Foco

Normalmente verifica um método, regra ou funcionalidade específica.

> Vantagem

É rápido e facilita localizar exatamente o componente responsável por uma falha.

Teste de integração

> Objetivo

Verifica se componentes funcionam corretamente quando combinados.

> Banco em memória

O material cita H2 para simular persistência durante os testes.

> Abrangência

Pode verificar fluxos de dados que atravessam múltiplas camadas.

> Diferença para unitário

É mais abrangente e normalmente mais lento, pois envolve componentes reais.

Ferramentas

> JUnit 5

Framework principal de testes para Java, com anotações, assertions e execução de testes.

> Mockito

Biblioteca para criar mocks e simular comportamentos de dependências.

> Spring Boot Test

Fornece suporte para testes que utilizam o contexto Spring e suas autoconfigurações.

> MockMvc

Permite testar controllers REST sem iniciar um servidor HTTP completo.

> JaCoCo

Gera análises e relatórios de cobertura de código.

Boas práticas

> Uma coisa por teste

Cada teste deve verificar um único comportamento ou cenário.

> Nomes descritivos

O padrão should_ResultadoEsperado_When_CondiçãoDeEntrada ajuda a documentar o comportamento.

> Given / When / Then

Given prepara o cenário; When executa a ação; Then verifica o resultado.

> Positivos e negativos

É necessário testar tanto sucesso quanto erros e exceções.

> Independência

Os testes não devem depender da ordem em que outros testes foram executados.

> Cenários extremos

Valores-limite, listas vazias, valores nulos e casos especiais podem revelar bugs.

Quando escrever testes?

> Antes de programar — TDD

Test-Driven Development significa escrever o teste antes da implementação para orientar o design do código.

> Após corrigir um bug

Criar um teste que reproduza o problema ajuda a evitar que ele volte a acontecer.

> Antes do deploy

Funcionalidades críticas devem estar cobertas antes da liberação.

> Após refatorar

Os testes verificam se a mudança estrutural preservou o comportamento esperado.

Estrutura de testes

> Organização

Os testes devem ficar em src/test e os pacotes podem espelhar a estrutura de src/main.

> Separação

Testes unitários e de integração devem ser organizados de forma separada.

> Nomenclatura

O material sugere sufixos como Test para unitários e IT para integração.

> Recursos

Recursos específicos dos testes podem ficar em src/test/resources.

Mockito na prática

> @Mock

Marca uma dependência que será simulada.

> @InjectMocks

Indica a classe que receberá as dependências simuladas.

> @ExtendWith(MockitoExtension.class)

Integra o Mockito ao mecanismo de testes do JUnit.

> Benefícios

Isola a classe, evita recursos externos, acelera a execução e permite controlar cenários específicos.

Falhas esperadas

> assertThrows

Verifica se uma determinada ação lança a exceção esperada. No exemplo, o serviço deve lançar IllegalArgumentException quando o e-mail já existe.

> Importância

Testar falhas é tão importante quanto testar sucessos, porque valida as regras de tratamento de erros.

MockMvc e testes de API

> Configuração

@SpringBootTest, @AutoConfigureMockMvc e @ActiveProfiles("test") são usados no exemplo.

> Requisição

mockMvc.perform pode simular POST e outras requisições HTTP.

> Validação

O teste verifica status HTTP e conteúdo retornado.

> Dados inválidos

O exemplo verifica se uma entrada inválida faz a API retornar 400 Bad Request.

Regras de negócio

> Exemplo

Um teste verifica o cálculo do total de um pedido: uma pizza de 29,90 em quantidade 2 deve produzir 59,80.

> Importância

Esse tipo de teste valida uma regra específica do domínio da aplicação.

Cobertura com JaCoCo

> Objetivo

Mede quanto do código é exercitado pelos testes e produz um relatório visual.

> Comando

O material mostra mvn clean test jacoco:report.

> Tipos

LINE mede linhas executadas; BRANCH mede decisões condicionais; METHOD mede métodos cobertos; CLASS mede classes cobertas.

> Meta

O exemplo define 0,80 de cobertura de linhas, ou 80%.

> Relatório

O arquivo target/site/jacoco/index.html apresenta os resultados.

Leitura do relatório

> Verde

Código testado completamente.

> Vermelho

Código não testado.

> Amarelo

Cobertura parcial, especialmente em branches.

> Foco

A aula destaca services, controllers, validações e regras de negócio.

Perfis de teste

> application-test.properties

O exemplo usa configurações específicas para testes, como banco criado e destruído automaticamente e desativação de cache.

> @ActiveProfiles

Ativa o perfil de teste para que as configurações correspondentes sejam carregadas.

> Linha de comando

Também pode ser usado mvn test -Dspring.profiles.active=test.

Automatização e CI/CD

> Script

O material mostra um script que executa mvn clean test jacoco:report jacoco:check.

> Pipeline

Os testes podem rodar a cada commit; falhas podem interromper o build; relatórios podem aparecer em dashboards; e a equipe pode receber notificações.

> Objetivo

Executar testes de forma consistente e descobrir problemas o mais cedo possível.

Casos apresentados

> Nubank

O material apresenta testes para transações, conformidade, simulação de acessos simultâneos, cobertura de microserviços e integração com CI/CD.

> Amazon

São citados testes de frete, checkout, pagamentos, desempenho e experiência de compra, além de alta disponibilidade.

> Tesla

São citadas simulações de cenários de direção autônoma, condições extremas e integração entre testes virtuais e hardware.

Projeto Delivery

> Problemas

Bug no cálculo de descontos, venda de produtos fora de estoque e regressão no cadastro de clientes.

> Com testes automatizados

A aula destaca identificação imediata, redução de retrabalho, maior confiança no deploy e feedback rápido.

Benefícios finais

> Redução de bugs

Problemas são encontrados antes de chegarem à produção.

> Confiança para evoluir

Refatorações e novos recursos podem ser feitos com maior segurança.

> Menor custo de manutenção

Problemas encontrados cedo tendem a exigir menos esforço para correção.

> Produtividade

Feedback rápido facilita iterações e melhorias.

> Deploy contínuo

Testes confiáveis ajudam a sustentar liberações frequentes.

### Fechamento do encontro

> Para estudar este encontro, concentre-se nas definições, diferenças entre os conceitos, finalidade de cada ferramenta e na sequência prática apresentada nos exemplos. Os exemplos de código dos slides servem para relacionar a teoria à implementação em Spring Boot.


## Encontro 19 — Documentação de API REST com Swagger

Tema central: O encontro ensina a documentar APIs REST com Swagger/OpenAPI e Spring Boot, incluindo configuração, anotações, segurança JWT e testes pela interface.

Importância da documentação

> Objetivo

A documentação explica como uma API funciona e como outras pessoas ou sistemas podem integrá-la.

> Integração

Uma documentação clara reduz o tempo necessário para entender endpoints, parâmetros e respostas.

> Problemas sem documentação

A aula cita dúvidas constantes, dificuldade de integração, falhas de comunicação e aumento de erros em produção.

Swagger e OpenAPI

> Swagger

É um conjunto de ferramentas que utiliza a especificação OpenAPI para descrever e documentar APIs REST de forma padronizada e interativa.

> OpenAPI

É um formato de descrição de APIs RESTful que permite que pessoas e computadores entendam as capacidades do serviço sem precisar acessar o código-fonte.

> Swagger UI

Interface gráfica que permite visualizar e testar a API diretamente pelo navegador.

> Swagger Editor

Editor visual para criar e modificar especificações OpenAPI.

> Swagger Codegen

Ferramenta que gera código de cliente e servidor a partir da especificação.

Quando usar Swagger

> Durante o desenvolvimento

Para manter a documentação acompanhando a evolução do código.

> APIs para parceiros

Quando sistemas de terceiros precisam integrar com a API.

> APIs internas

Para facilitar a comunicação entre equipes da mesma organização.

Como o Swagger ajuda

> Catálogo de endpoints

Lista URLs e métodos HTTP disponíveis, como GET, POST, PUT e DELETE.

> Formatos

Mostra dados esperados de entrada e saída e pode fornecer exemplos.

> Interface de testes

Permite enviar requisições diretamente pela interface.

Fluxo de configuração

> 1. Dependência

Adicionar a biblioteca compatível ao projeto Spring Boot.

> 2. Configuração

Definir informações gerais e caminhos da documentação.

> 3. Anotações

Descrever controllers, parâmetros, respostas e modelos.

> 4. Navegador

Acessar o Swagger UI para visualizar e testar.

Configuração no Spring Boot

> Dependência

O material mostra springdoc-openapi para projetos Spring Boot 3.x, com exemplos de versões apresentadas nos slides.

> application.properties

springdoc.api-docs.path define o caminho da especificação JSON; springdoc.swagger-ui.path define o caminho da interface.

> Acesso

Os exemplos apresentados usam /swagger-ui.html para a interface e /api-docs para o JSON.

SwaggerConfig

> Finalidade

Uma classe de configuração opcional pode personalizar nome, versão, descrição, licença, contato e termos de serviço da API.

> OpenAPI

O exemplo cria um objeto OpenAPI e preenche suas informações por meio de Info, Contact e License.

Anotando controllers

> @Tag

Agrupa endpoints em uma categoria lógica, como Produtos.

> @RestController

Identifica a classe como controller REST.

> @RequestMapping

Define o caminho-base dos endpoints.

> @Operation

Descreve uma operação, com resumo e descrição.

> @GetMapping

Mapeia uma requisição HTTP GET para um método.

Parâmetros

> @Parameter

Documenta um parâmetro, incluindo descrição, exemplo e obrigatoriedade.

> @PathVariable

Obtém um valor que faz parte da própria URL, como /produtos/{id}.

Respostas

> @ApiResponses

Agrupa as possíveis respostas de uma operação.

> @ApiResponse

Documenta um código de resposta específico, como 200, 404 ou 500.

> @Content

Descreve o conteúdo retornado, incluindo media type.

> @Schema

Relaciona a resposta ao modelo que representa seus dados.

DTOs e modelos

> DTO

Data Transfer Object é um objeto usado para transportar dados entre partes da aplicação, especialmente na comunicação de APIs.

> @Schema em DTO

Pode documentar descrição, exemplos, tamanho mínimo e máximo, obrigatoriedade e limites de campos.

> Exemplo

O ProdutoDTO apresentado possui id, nome e preco documentados.

Enums

> Enum

Tipo que representa um conjunto fechado de valores possíveis.

> Exemplo

StatusPedido possui PENDENTE, EM_PREPARO, EM_TRANSITO, ENTREGUE e CANCELADO, cada um com descrição.

Organização por tags

> Finalidade

Tags organizam endpoints relacionados em grupos lógicos e facilitam a navegação.

> Boa organização

A aula recomenda categorias claras, descrições concisas, exemplos relevantes e ordenação lógica.

Autenticação JWT

> Swagger e segurança

A interface pode ser configurada para testar endpoints protegidos por tokens JWT.

> SecurityScheme

Define o mecanismo de autenticação utilizado pela documentação.

> Bearer

O esquema apresentado usa HTTP Bearer e formato JWT.

> Authorize

O botão permite inserir o token e fazer com que as requisições seguintes utilizem a credencial configurada.

> JWT

JSON Web Token é um formato de token usado para representar informações e credenciais em mecanismos de autenticação.

Testando pela interface

> Selecionar endpoint

Expandir o endpoint desejado.

> Try it out

Ativar o modo que permite preencher os dados da requisição.

> Parâmetros

Informar os valores necessários.

> Execute

Enviar a requisição e analisar a resposta.

Experiência do desenvolvedor

> Descrição de campos

Explica o propósito de cada campo.

> Exemplos

Modelos de entrada e saída facilitam o entendimento.

> Códigos de erro

A documentação pode indicar erros possíveis e como tratá-los.

> Validações

Informa restrições e regras aplicadas aos dados.

Boas práticas de documentação

> Clareza

Usar linguagem simples e direta, evitando termos técnicos desnecessários quando não forem necessários.

> Exemplos reais

Mostrar casos de uso realistas.

> Completude

Documentar todos os endpoints, inclusive os menos utilizados.

> Atualização constante

Manter a documentação sincronizada com as mudanças da API.

O que documentar

> Endpoints

URLs e métodos HTTP disponíveis.

> Parâmetros

Dados de entrada e seus formatos.

> Respostas

Códigos HTTP e estruturas retornadas.

> Modelos

Estruturas de dados utilizadas.

> Segurança

Métodos de autenticação e autorização.

Ajustes da interface

> operationsSorter

Pode ordenar endpoints por método HTTP.

> tagsSorter

Pode ordenar tags alfabeticamente.

> docExpansion

Controla a forma como os grupos aparecem inicialmente na interface.

> api-docs.resolve-schema-properties

O material mostra essa configuração para habilitar resolução de propriedades de schema.

> swagger-ui.path

Permite definir um caminho personalizado para a interface.

Organização por funcionalidade

> Produtos

Exemplo de grupo com GET para listar, GET por ID, POST para criar, PUT para atualizar e DELETE para remover.

> Clientes

Exemplo de grupo com operações de listagem, busca, criação, atualização e remoção.

> Pedidos

Exemplo de grupo com listagem, busca, criação, atualização de status e cancelamento.

Como validar a documentação

> Verificar acesso

Confirmar que a interface abre no navegador.

> Validar endpoints

Verificar se todos estão visíveis e agrupados corretamente.

> Testar requisições

Enviar requisições de teste para confirmar que o comportamento corresponde ao que foi documentado.

Erros comuns

> Dependência incompatível

Verificar se a biblioteca está configurada corretamente e é compatível com a versão do Spring Boot.

> Endpoint bloqueado

Verificar configurações de segurança, como Spring Security.

> Falta de anotações

Controllers e endpoints precisam estar devidamente anotados para aparecerem na documentação.

Melhoria contínua

> Desenvolvimento

Novas funcionalidades são implementadas.

> Documentação

A documentação é atualizada junto com as mudanças.

> Feedback

Comentários dos usuários da API ajudam a identificar melhorias.


## Encontro 21 — Escalabilidade e Performance

> Introdução
O objetivo geral deste encontro é compreender os fundamentos de escalabilidade e performance no desenvolvimento de arquiteturas de software modernas. Serão analisadas as diferenças entre esses dois conceitos frequentemente confundidos, os indicadores de medição de desempenho, os gargalos mais comuns em sistemas corporativos e os padrões arquiteturais aplicáveis. Além disso, serão demonstradas técnicas práticas para otimização e escalabilidade de aplicações Java utilizando o ecossistema Spring Boot, Spring Actuator, Redis, Docker e ferramentas de monitoramento.

> Escalabilidade
Escalabilidade é a capacidade de um sistema de software de lidar de maneira eficiente com o aumento progressivo da carga de trabalho (seja por volume de requisições, quantidade de dados ou usuários simultâneos) através da adição proporcional de recursos computacionais, mantendo o nível de desempenho e a estabilidade das operações sem colapsar a infraestrutura.

> Performance
Performance (ou desempenho) refere-se à velocidade individual e à eficiência operacional com que uma aplicação processa e responde a uma determinada requisição em condições específicas. Uma API que processa uma chamada HTTP e retorna os dados em 200 milissegundos possui uma performance superior a uma API que executa a mesma tarefa em 1,5 segundo.

Comparação: Performance x Escalabilidade
Embora relacionados, os conceitos tratam de aspectos distintos do comportamento de um sistema:

Performance: Foca na velocidade e na eficiência de processamento de requisições individuais. Responde à pergunta: "Quão rápida é esta operação?"

Escalabilidade: Foca na capacidade do sistema de manter a performance quando a demanda total aumenta consideravelmente. Responde à pergunta: "O sistema continua funcionando bem se o volume de usuários quadruplicar?"
Uma aplicação pode ter uma excelente performance individual para um único usuário, mas falhar miseravelmente em escalabilidade quando cem usuários realizam requisições simultâneas. O objetivo da arquitetura de sistemas é equilibrar ambas as dimensões.

Escalabilidade Vertical x Escalabilidade Horizontal
Existem duas abordagens principais para aumentar a capacidade computacional de um sistema:

Escalabilidade Vertical (Scale Up):
Consiste em adicionar mais recursos de hardware a uma única máquina ou servidor existente (como aumentar a memória RAM, trocar o processador por um com mais núcleos de CPU ou expandir a capacidade do disco SSD).

Vantagens: Simplicidade de implementação, pois não exige alterações na arquitetura da aplicação ou no código-fonte.

Desvantagens: Possui um limite físico rígido do hardware, apresenta um custo financeiro exponencial em grandes escalas e gera um ponto único de falha (se o servidor cair, o sistema inteiro fica fora do ar).

Escalabilidade Horizontal (Scale Out):
Consiste em adicionar mais máquinas ou instâncias da aplicação trabalhando em conjunto para distribuir a carga de trabalho.

Vantagens: Alta disponibilidade, tolerância a falhas, balanceamento de carga e ausência de limites teóricos de expansão.

Desvantagens: Introduz complexidade no gerenciamento, na orquestração de rede e exige que as aplicações sejam desenhadas como stateless (sem guardar estado do usuário na memória da aplicação).

Indicadores e Métricas de Performance
Para avaliar e monitorar a saúde de um sistema, utilizam-se quatro indicadores essenciais:

Latência: O tempo total necessário para processar uma requisição do momento em que ela é enviada pelo cliente até a recepção da resposta. É medida em milissegundos (ms).

Throughput (Vazão): A quantidade total de requisições processadas com sucesso por unidade de tempo, geralmente expressa em requisições por segundo (RPS).

Taxa de Erro: O percentual de requisições que falharam (erros com códigos HTTP 5xx) em comparação ao volume total de requisições recebidas.

Uso de Recursos: O consumo percentual de recursos físicos do servidor, como uso de CPU, ocupação de memória RAM, I/O de disco e largura de banda de rede.

Gargalos de Performance Comuns
Os gargalos ocorrem nos pontos onde o fluxo de dados desacelera ou trava o sistema. Os principais são:

Banco de dados: Consultas pesadas não otimizadas, ausência de índices nas tabelas, problemas de queries N+1 do ORM e conexões insuficientes no pool de banco.

Serialização e Desserialização: Processamento ineficiente para converter objetos da linguagem em JSON ou XML e vice-versa.

Requisições HTTP Lentas: Dependência de APIs de terceiros que demoram para responder sem um limite de tempo (timeout) estabelecido.

Ausência de Caching: Execução repetida de algoritmos ou consultas custosas cujos dados raramente mudam.

Operações Síncronas Bloqueantes: Threads de execução retidas aguardando o término de operações longas (como envio de e-mails ou geração de arquivos) em vez de processá-las de forma assíncrona.

Ferramentas de Teste e Medição

JMeter: Ferramenta gráfica utilizada para simular cargas de trabalho e realizar testes de carga em aplicações web.

Gatling: Ferramenta baseada em código e voltada para testes de estresse pesados e análises detalhadas de capacidade.

Spring Boot Actuator: Módulo que expõe métricas operacionais diretamente da aplicação Java em execução.

Prometheus e Grafana: Solução combinada para coleta contínua de métricas em séries temporais e exibição em painéis gráficos interativos.

Camadas que Afetam a Performance
A otimização deve ocorrer em todas as camadas da aplicação:

Infraestrutura: Servidores, redes e balanceadores de carga.

Banco de Dados: Índices, modelo relacional e estrutura física.

Repositório: Estratégia de busca de dados via Spring Data JPA.

Service: Regras de negócio, algoritmos e uso de cache.

Controller: Tratamento de DTOs, respostas HTTP e validações.

Padrões de Escalabilidade

Load Balancer (Balanceador de Carga): Componente que intercepta todas as requisições e as distribui de forma uniforme entre as instâncias disponíveis da aplicação.

Circuit Breaker (Disjuntor de Circuito): Padrão que interrompe chamadas a um serviço externo que está falhando, evitando que a aplicação trave aguardando retornos.

Retry Pattern (Padrão de Tentativa de Reexecução): Reexecuta automaticamente uma operação que falhou por um motivo temporário de rede.

Bulkhead: Isolamento de componentes em compartimentos independentes para evitar que uma falha em um módulo derrube todo o sistema.

Exemplos Práticos de Aplicação no Mercado

Startups em Crescimento: Empresas que precisam migrar de uma base de 100 para mais de 10.000 usuários ativos e reestruturam a aplicação para crescer sem reescrevê-la do zero.

E-Commerce em Black Friday: Plataformas de comércio eletrônico que enfrentam picos repentinos de acessos e dependem de auto scaling e cache agressivo para não saírem do ar.

APIs Públicas e Plataformas Massivas: Serviços como iFood, Uber e Spotify, que processam milhões de solicitações por segundo e dependem de arquiteturas stateless e alta disponibilidade.

Sistemas Financeiros: Aplicações bancárias onde a alta disponibilidade precisa ser de pelo menos 99,99% e o tempo de resposta precisa ser mantido em milissegundos para evitar prejuízos.

Código e Comandos no Spring Boot

Para incluir o suporte a métricas no Spring Boot com Maven, adiciona-se a dependência do Actuator:

XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
Explicação do XML:

spring-boot-starter-actuator: Dependência do ecossistema Spring que habilita endpoints de diagnóstico, monitoramento e métricas de desempenho na aplicação.

No arquivo de configuração application.yml ou application.properties, habilitam-se os endpoints de métricas:

Properties
management.endpoints.web.exposure.include=*
management.metrics.export.prometheus.enabled=true
Explicação das configurações:

management.endpoints.web.exposure.include=*: Expõe todos os endpoints operacionais do Actuator via HTTP (como /actuator/metrics e /actuator/health).

management.metrics.export.prometheus.enabled=true: Formata e disponibiliza as métricas no padrão aceito pelo Prometheus.

Para medir a performance de um endpoint de produtos e aplicar caching com paginação no Controller Spring Boot:

Java
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Timed(value = "produtos.buscar", histogram = true)
    @GetMapping
    public Page<ProdutoDTO> buscar(Pageable pageable) {
        return produtoService.listarProdutos(pageable);
    }
}
Explicação do código Java:

@Timed: Anotação do Micrometer que mede o tempo exato de execução do método e envia a métrica para o sistema de monitoramento com dados estatísticos.

Pageable e Page: Recursos do Spring Data JPA que evitam buscar todo o banco de dados de uma só vez, retornando apenas uma página delimitada de registros (ex: 20 produtos por página).

Aplicação de Cache no Service:

Java
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Cacheable("produtos")
    public Page<ProdutoDTO> listarProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(ProdutoDTO::new);
    }
}
Explicação do código:

@Cacheable("produtos"): Instrui o Spring a verificar se o resultado dessa consulta já está armazenado em memória (no Redis ou Caffeine). Se estiver, o resultado é retornado instantaneamente sem consultar o banco de dados relacional.

Dockerfile para conteinerizar e escalar a aplicação:

Dockerfile
FROM openjdk:21
COPY target/api.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
Explicação do Dockerfile:

FROM openjdk:21: Define a imagem base do Java 21.

COPY target/api.jar app.jar: Copia o arquivo executável gerado pelo Maven para dentro do container.

ENTRYPOINT: Define o comando padrão para inicializar a aplicação Java.

Comando para escalonar a aplicação usando Docker Compose:

Bash
docker-compose up --scale app=3
Explicação do comando:

--scale app=3: Cria e executa simultaneamente 3 instâncias idênticas da mesma aplicação no Docker, permitindo que um balanceador de carga distribua as requisições entre elas.

Boas Práticas

Torne a aplicação totalmente stateless, armazenando dados de sessão em bancos como Redis e não na memória do servidor.

Implemente paginação obrigatória em todos os endpoints que retornam listas.

Adicione cache para dados com taxa elevada de leitura e baixa frequência de alteração.

Habilite a compressão HTTP (GZIP) para reduzir o tamanho dos payloads transferidos pela rede.

Pontos Importantes para Estudar

A diferença fundamental entre velocidade (performance) e capacidade volumétrica (escalabilidade).

As vantagens e desvantagens da escalabilidade vertical em relação à horizontal.

Como identificar gargalos comuns em bancos de dados e chamadas externas.

O funcionamento do Spring Boot Actuator no fornecimento de métricas em tempo real.

Fechamento do encontro
Neste encontro, aprendemos que projetar sistemas de alta performance exige otimizar desde as rotinas de código e consultas ao banco de dados até a infraestrutura em container. Vimos como a escalabilidade horizontal aliada a aplicações stateless e ao uso de caching permite suportar aumentos drásticos de acessos sem comprometer o tempo de resposta, consolidando o ecossistema Spring Boot como uma solução robusta para o ambiente corporativo.

Encontro 23 — Entrega Final de Projeto com Spring Boot
Introdução
Esta aula orienta a estruturação, organização, documentação e empacotamento completo do projeto prático final de um sistema de delivery desenvolvido em Spring Boot. Serão abordados a separação rigorosa entre requisitos funcionais e não funcionais, a aplicação da arquitetura em camadas, as boas práticas de desenvolvimento e a elaboração de todos os documentos obrigatórios para a entrega, incluindo o Guia do Desenvolvedor, o Manual do Usuário e o Relatório Técnico.

Requisitos Funcionais
Requisitos funcionais determinam exatamente o que o sistema deve fazer em termos de regras de negócio e funcionalidades oferecidas aos usuários finais. No projeto de delivery, os requisitos funcionais essenciais incluem:

Cadastro e autenticação de usuários (clientes, restaurantes e entregadores).

Criação e gerenciamento do carrinho de compras e realização de pedidos.

Processamento de pagamentos online.

Acompanhamento do status e rastreamento de entregas em tempo real.

Boas Práticas Funcionais

Cobertura de testes em todas as rotas principais para garantir o funcionamento correto.

Fluxos operacionais sem erros críticos ou comportamentos inconsistentes.

Interface gráfica e rotas de API objetivas e de fácil utilização.

Definição rigorosa de permissões de acesso por perfil de usuário.

Requisitos Não Funcionais
Requisitos não funcionais determinam os critérios de qualidade, restrições e características operacionais sob as quais o sistema deve rodar. Eles impactam diretamente a experiência e a confiabilidade da plataforma.

Categorias de Requisitos Não Funcionais

Confiabilidade: Capacidade do sistema de se recuperar automaticamente de falhas operacionais sem perda de dados.

Portabilidade: Facilidade de execução em diferentes ambientes, navegadores e sistemas operacionais.

Manutenibilidade: Estrutura de código limpa, modular, desacoplada e adequadamente documentada.

Segurança: Proteção de dados sensíveis com criptografia, autenticação segura e controle de acesso.

Performance: Garantia de tempo de resposta dos endpoints inferior a 2 segundos em condições normais de uso.

Estrutura de Pacotes no Spring Boot
A aplicação deve adotar a arquitetura em camadas tradicional para garantir a separação clara de responsabilidades:

controller: Contém os endpoints da API REST, tratando requisições HTTP e retornando respostas.

service: Centraliza todas as regras de negócio e validações do sistema.

repository: Interfaces que herdam do Spring Data JPA para comunicação com o banco de dados.

model: Classes de entidades que mapeiam as tabelas do banco de dados relacional.

dto (Data Transfer Object): Objetos para transporte de dados entre camadas, protegendo as entidades originais.

config: Classes de configuração de beans, segurança, CORS e documentação.

Documentação Técnica Obrigatória
Uma entrega profissional exige três documentos fundamentais:

Guia do Desenvolvedor:
Documento técnico direcionado à equipe de engenharia. Deve conter instruções detalhadas de como instalar, configurar e executar o projeto localmente, mapa de dependências do arquivo pom.xml, explicação sobre a estrutura de pastas, descrição da arquitetura, mapeamento dos perfis de execução (dev e prod) e instruções para execução de testes unitários e de integração.

Manual do Usuário:
Guia prático e ilustrado com capturas de tela reais do sistema, voltado aos clientes e operadores. Deve utilizar linguagem acessível (sem jargões técnicos) e apresentar o passo a passo completo para criação de conta, navegação pelo cardápio, montagem do pedido, pagamento e acompanhamento da entrega.

Relatório Técnico:
Documento que registra o histórico de desenvolvimento da aplicação. Deve conter a visão geral da arquitetura utilizada, os diagramas de classe e fluxo, a lista de tecnologias implementadas, as evidências dos testes realizados, os desafios técnicos enfrentados, as soluções aplicadas e sugestões de melhorias futuras.

Ferramentas de Apoio

GitHub: Plataforma de hospedagem de código e controle de versão.

Postman: Ferramenta para executar e validar as rotas da API REST.

Swagger / OpenAPI: Módulo integrado para geração automática e interativa da documentação da API.

Docker: Tecnologia de containerização para garantir que a aplicação rode identicamente em qualquer ambiente.

GitHub Actions: Ferramenta de integração e entrega contínuas (CI/CD).

Onde Usamos no Contexto Profissional

Empresas de Tecnologia: Exigem projetos entregues com versionamento organizado, esteiras de CI/CD ativas e documentação robusta para facilitar o processo de entrada (onboarding) de novos programadores.

Trabalho em Equipe: Requer padronização de código, comunicação transparente através da documentação e divisão clara de responsabilidades.

Demonstração para Clientes: Utiliza apresentações visuais, testes ao vivo e manuais simplificados para comprovar o atendimento aos requisitos contratados.

Código e Estrutura do Projetos Final

Implementação do Endpoint de Criação de Pedidos no Controller:

Java
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoDTO pedidoCriado = pedidoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }
}
Explicação do código:

@Valid: Dispara automaticamente as validações do Bean Validation no DTO de entrada antes que o método seja executado.

@RequestBody: Converte o payload JSON enviado no corpo da requisição HTTP em um objeto Java PedidoRequestDTO.

ResponseEntity.status(HttpStatus.CREATED): Retorna o código HTTP 201 (Created) indicando que o recurso foi criado com sucesso.

Dependência do Swagger OpenAPI no pom.xml:

XML
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.9</version>
</dependency>
Explicação do XML:

springdoc-openapi-ui: Adiciona a biblioteca que gera automaticamente a documentação OpenAPI 3 e disponibiliza a interface gráfica visual interativa no endereço /swagger-ui.html.

Dockerfile de Produção:

Dockerfile
FROM openjdk:17
WORKDIR /app
COPY target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
Explicação do Dockerfile:

WORKDIR /app: Define o diretório de trabalho padrão dentro do container.

EXPOSE 8080: Informa que o container escutará as conexões na porta 8080.

Exemplo de arquivo README.md na raiz do projeto:

Markdown
# Sistema de Delivery API

## Como Executar
1. Certifique-se de ter o Java 21 e o Docker instalados.
2. Execute o comando de compilação: `mvn clean package`
3. Construa a imagem Docker: `docker build -t delivery-api .`
4. Execute o container: `docker run -p 8080:8080 delivery-api`

## Documentação
Acesse a documentação interativa da API REST em:
http://localhost:8080/swagger-ui.html
Estrutura física completa do pacote de entrega em arquivo ZIP (delivery-final.zip):

Plaintext
delivery-final.zip
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── docs/
│   ├── manual-usuario.pdf
│   ├── relatorio-tecnico.pdf
│   └── guia-dev.pdf
├── scripts/
│   ├── setup.sh
│   └── test.sh
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
Critérios de Avaliação
O projeto entregue será avaliado com base nos seguintes itens:

Requisitos funcionais operando sem erros (cadastros, pedidos e pagamentos).

Tratamento adequado de exceções e ausência de falhas com retorno 500 na API.

Swagger ativo e acessível com todos os endpoints mapeados.

Aplicação conteinerizada com deploy funcional via Docker.

Presença e qualidade de toda a documentação solicitada.

Boas Práticas

Mantenha comentários úteis no código apenas onde a lógica do negócio for complexa.

Nunca inclua senhas ou chaves privadas nos arquivos do repositório.

Mantenha o arquivo README.md atualizado com todas as instruções necessárias.

Pontos Importantes para Estudar

A separação clara entre os papéis de requisitos funcionais e não funcionais.

A responsabilidade de cada camada na arquitetura Spring Boot (Controller, Service, Repository, DTO).

Os elementos que compõem uma documentação técnica completa para desenvolvedores e usuários.

A organização física de pastas no empacotamento do arquivo de entrega.

Fechamento do encontro
Neste encontro, abordamos todas as etapas necessárias para estruturar e entregar um projeto de software profissional. Compreendemos que um sistema completo não se limita ao código-fonte compilável, mas exige o cumprimento rigoroso de requisitos não funcionais, a padronização das camadas do Spring Boot, a criação de documentações claras e a facilidade de implantação via Docker.

## Encontro 25 — Integração do Sistema de Delivery com APIs de Terceiros

Introdução
Esta aula apresenta o conceito e a prática de integração entre o sistema de delivery e serviços externos através de APIs RESTful de terceiros. Serão estudados os padrões de comunicação em formato JSON, métodos de autenticação, tratamento de erros, estratégias de contingência (fallback) e a utilização de bibliotecas HTTP do ecossistema Spring Boot, como RestTemplate, WebClient e FeignClient.

APIs Externas
APIs externas são interfaces de programação fornecidas por terceiros que permitem a comunicação e a troca segura de dados entre aplicações distintas. Elas atuam como pontes, permitindo que o sistema de delivery incorpore serviços especializados de mercado sem a necessidade de desenvolvê-los internamente.

Padrão RESTful e JSON
A comunicação entre sistemas modernos utiliza prioritariamente o estilo arquitetural RESTful sobre o protocolo HTTP.

Protocolo HTTP: Utiliza verbos semânticos para definir a ação desejada: GET (buscar dados), POST (enviar/criar dados), PUT (atualizar dados) e DELETE (remover dados).

JSON (JavaScript Object Notation): Formato estruturado, leve e de fácil leitura por humanos e máquinas, utilizado de forma universal para o transporte de dados entre requisições e respostas HTTP.

Exemplo de payload em formato JSON recebido de uma API externa de CEP:

JSON
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}
Tipos de Serviços Integrados em Sistemas de Delivery

Gateways de Pagamento: Processamento de transações de cartão de crédito, PIX e boleto bancário (Mercado Pago, Stripe, PayPal).

Geolocalização e Mapas: Cálculo de distância, tempo estimado de entrega e traçado de rotas (Google Maps, Mapbox).

Comunicação e Notificações: Envio de mensagens de confirmação e códigos de rastreio via SMS ou mensagens automáticas (WhatsApp Cloud API, Twilio, Firebase).

Endereçamento e Logística: Validação automatizada de endereços a partir do CEP e rastreamento de remessas (ViaCEP, Correios).

Autenticação e Autorização: Validação de identidade via provedores externos (OAuth2, Firebase Auth).

Padrões de Autenticação em APIs
Para garantir que apenas clientes autorizados utilizem os serviços externos, aplicam-se três mecanismos de autenticação:

API Key: Uma chave única alfanumérica enviada nos cabeçalhos (Headers) ou parâmetros da requisição HTTP para identificar a aplicação cliente.

Bearer Token (JWT): Token estruturado e assinado digitalmente enviado no cabeçalho Authorization, contendo as permissões e dados do usuário.

OAuth2: Protocolo avançado de autorização delegada que permite conceder acesso a recursos sem expor as credenciais do usuário.

Benefícios e Desafios da Integração

Vantagens:

Agilidade no desenvolvimento (Time-to-market), reutilizando soluções de mercado maduras.

Redução de custos com infraestrutura e manutenção de software especializado.

Acesso imediato a tecnologias avançadas de fraude, pagamento e inteligência geográfica.

Desafios e Riscos:

Instabilidade ou indisponibilidade temporária no serviço do provedor externo.

Alterações imprevisíveis nas rotas ou nos formatos das respostas JSON (quebra de contrato de API).

Ocorrência de timeouts devido à alta latência na rede.

Acoplamento excessivo com fornecedores externos.

Boas Práticas de Arquitetura em Integrações

Isolar chamadas externas: Criar uma camada de cliente isolada (Service ou Client Layer) para que mudanças na API externa não afetem a regra de negócio interna.

Tratar erros com Fallback: Definir respostas ou fluxos alternativos padrão para quando o serviço externo estiver fora do ar.

Configurar Timeouts e Retries: Estabelecer tempos máximos de espera (ex: 2 segundos) para evitar que a aplicação fique travada aguardando uma resposta externa.

Bibliotecas do Spring Boot para Chamadas HTTP

RestTemplate:
Cliente HTTP síncrono e tradicional do Spring Framework. É simples e fácil de configurar, indicado para cenários básicos e aprendizado inicial.

WebClient:
Cliente HTTP moderno, reativo e não-bloqueante pertencente ao módulo Spring WebFlux. Oferece alta performance e suporte a chamadas assíncronas concorrentes.

FeignClient (Spring Cloud OpenFeign):
Cliente HTTP declarativo que permite criar integrações criando apenas interfaces Java anotadas, sem necessidade de escrever o código de requisição HTTP manualmente.

Comparação: RestTemplate x WebClient x FeignClient

RestTemplate: Síncrono, tradicional/legado, de implementação simples e ideal para aplicações imperativas tradicionais.

WebClient: Assíncrono/Reativo, moderno, de alta performance e recomendado para sistemas reativos não-bloqueantes.

FeignClient: Declarativo (baseado em interfaces), totalmente integrado ao Spring Cloud e ideal para arquiteturas de microsserviços.

Código e Integração Prática com ViaCEP

Para criar a integração em uma aplicação Spring Boot, inclui-se a dependência web no pom.xml:

XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
Criação do Modelo DTO para mapear o endereço retornado pelo JSON:

Java
@Data
public class EnderecoDTO {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
Explicação do código:

@Data: Anotação do Lombok que gera automaticamente em tempo de compilação os métodos Getters, Setters, toString, equals e hashCode.

Os nomes dos atributos na classe Java correspondem exatamente às chaves do JSON retornado pela API externa do ViaCEP.

Configuração do Bean do RestTemplate no Spring:

Java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
Explicação do código:

@Configuration: Indica que a classe possui definições de configuração do Spring.

@Bean: Registra a instância de RestTemplate no container de injeção de dependência do Spring, permitindo que ela seja injetada via @Autowired em qualquer classe do projeto.

Implementação da Chamada HTTP no Controller com Tratamento de Erros:

Java
@RestController
@RequestMapping("/cep")
public class EnderecoController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{cep}")
    public ResponseEntity<?> buscarEndereco(@PathVariable String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            EnderecoDTO endereco = restTemplate.getForObject(url, EnderecoDTO.class);
            return ResponseEntity.ok(endereco);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Erro na requisição externa: " + e.getMessage());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Serviço de CEP temporariamente indisponível. Tente mais tarde.");
        }
    }
}
Explicação detalhada do código:

getForObject(url, EnderecoDTO.class): Realiza uma requisição HTTP GET para a URL informada, desserializando automaticamente o JSON de resposta para um objeto Java EnderecoDTO.

HttpClientErrorException: Captura erros HTTP do lado do cliente ou servidor externo (ex: erros 400 ou 404).

ResourceAccessException: Captura falhas de conexão de rede ou estouro do tempo limite de resposta (timeout), retornando o código HTTP 503 (Service Unavailable) como tratamento de resiliência.

Boas Práticas

Sempre mapeie os modelos de dados da API externa em DTOs específicos.

Trate exceções HTTP para evitar que erros de APIs de terceiros resultem em falhas 500 para os clientes do seu sistema.

Utilize ferramentas como o Postman para testar os endpoints da API externa antes de codificar a integração no Java.

Pontos Importantes para Estudar

O funcionamento das integrações REST com o formato JSON.

A diferença entre autenticação por API Key, Bearer Token e OAuth2.

As características distintivas de RestTemplate, WebClient e FeignClient.

A importância do tratamento de exceções com blocos try-catch específicos e estratégias de fallback.

Fechamento do encontro
Neste encontro, compreendemos como integrar aplicações Spring Boot com serviços de terceiros utilizando chamadas HTTP e o formato JSON. Aprendemos a importância de isolar integrações na arquitetura, aplicar métodos de autenticação adequados e implementar tratamento de erros para construir aplicações tolerantes a falhas e prontas para o mercado.

## Encontro 27 — Monitoramento, Logging e Observabilidade


Este encontro aborda os fundamentos e a implementação prática de observabilidade em arquiteturas de software modernas. Serão detalhados os três pilares da observabilidade (Logs, Métricas e Traces), a diferença essencial entre monitorar e ser observável, os níveis de logging com SLF4J/Logback, e o uso de ferramentas de monitoramento em tempo real com Spring Boot Actuator, Micrometer, Prometheus e Grafana.

Observabilidade
Observabilidade é a capacidade de inferir o estado interno de um sistema complexo e responder o motivo de comportamentos anômalos apenas analisando os dados gerados em suas saídas externas (logs, métricas e rastreamentos), sem a necessidade de alterar a aplicação ou inserir novos blocos de código para depuração.

Comparação: Monitoramento x Observabilidade

Monitoramento: Coleta dados conhecidos sobre métricas predefinidas para responder à pergunta: "O que está acontecendo?" (Exemplo: "O servidor está com 90% de uso de CPU").

Observabilidade: Fornece o contexto de dados detalhados para responder à pergunta: "Por que isso está acontecendo?" (Exemplo: "O uso de CPU subiu para 90% porque o endpoint /pedidos acionou uma consulta sem índice após a requisição do usuário X").

Os 3 Pilares da Observabilidade

Logs: Registros textuais detalhados de eventos ocorridos no sistema em um determinado instante do tempo, contendo informações sobre a execução da aplicação.

Métricas: Dados numéricos e estatísticos agregados que medem o comportamento e a saúde do sistema em intervalos de tempo regulares.

Traces (Rastreamento Distribuído): O acompanhamento do caminho exato percorrido por uma requisição individual à medida que ela cruza diversos microsserviços e componentes de rede.

Níveis de Logging no Spring Boot
O registro de eventos (logging) utiliza níveis de severidade para filtrar e categorizar a importância das mensagens:

INFO: Registra eventos informativos normais do fluxo da aplicação (ex: "Aplicação inicializada com sucesso", "Pedido #1234 criado").

DEBUG: Fornece informações altamente detalhadas para diagnóstico e depuração em ambiente de desenvolvimento.

WARN: Indica situações de alerta que não impedem a execução do sistema, mas exigem atenção (ex: "Tentativa de acesso com token prestes a expirar").

ERROR: Registra falhas graves que interrompem o processamento de uma funcionalidade ou operação (ex: "Falha de conexão com o banco de dados").

Ferramentas do Ecossistema de Observabilidade

SLF4J (Simple Logging Facade for Java): Fachada de abstração que padroniza o uso de loggers em código Java.

Logback: Framework de implementação nativo do Spring Boot que processa e grava os logs em arquivos ou no console.

ELK Stack (Elasticsearch, Logstash, Kibana): Conjunto de ferramentas utilizado para coletar, indexar, armazenar e visualizar milhões de logs centralizados.

Spring Boot Actuator: Módulo do Spring que disponibiliza endpoints operacionais contendo o status de saúde da aplicação e métricas de desempenho.

Micrometer: Interface de instrumentação que coleta métricas da JVM e as converte no formato aceito por diferentes coletores.

Prometheus: Banco de dados especializado em séries temporais que coleta métricas da aplicação realizando varreduras periódicas (scraping).

Grafana: Ferramenta gráfica de visualização que consome dados do Prometheus para montar painéis informativos (dashboards) e disparar alertas.

Rastreamento Distribuído (Distributed Tracing)
Em arquiteturas de microsserviços, uma única ação do usuário pode acionar chamadas encadeadas em múltiplos serviços. O rastreamento atribuindo um identificador único à requisição (Trace ID) e aos seus subpassos (Span ID) permite acompanhar a jornada completa do pedido e localizar exatamente em qual serviço ocorreu uma falha ou gargalo de latência. Ferramentas comuns incluem Zipkin, Jaeger e Spring Cloud Sleuth.

Segurança e Privacidade em Logs
É expressamente proibido registrar informações confidenciais nos arquivos de log.

Nunca logar: Senhas em texto plano, dados bancários, números de cartão de crédito, tokens de acesso ou informações pessoais (PII) sujeitas à LGPD.

Boas Práticas: Aplique mascaramento de dados sensíveis e restrinja o acesso aos diretórios e servidores de armazenamento de logs.

Código e Configurações Práticas

Para incluir o suporte ao Actuator e ao Prometheus no pom.xml:

XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
Explicação do XML:

micrometer-registry-prometheus: Converte as métricas coletadas na JVM do Spring para o formato exigido pela coleta do Prometheus.

Configuração de Exposição dos Endpoints no application.yml:

YAML
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
spring:
  application:
    name: observabilidade-api
Explicação do YAML:

exposure.include: "*": Habilita a visualização pública de todos os endpoints operacionais do Actuator (como /actuator/metrics e /actuator/prometheus).

show-details: always: Exibe informações detalhadas sobre a saúde de cada componente (banco de dados, disco, etc.) no endpoint /actuator/health.

Uso de Logs Estruturados no Controller Java com SLF4J:

Java
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody String pedido) {
        log.info("Processando a criação do pedido: {}", pedido);
        return ResponseEntity.ok("Pedido processado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscarPedido(@PathVariable Long id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID do pedido inválido.");
            }
            return ResponseEntity.ok("Dados do Pedido: " + id);
        } catch (Exception e) {
            log.error("Erro ao buscar o pedido com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
        }
    }
}
Explicação do código:

LoggerFactory.getLogger: Inicializa a instância do Logger vinculado à classe PedidoController.

log.info("...", pedido): Grava uma mensagem de informação usando parâmetros interpolados ({}), evitando concatenação ineficiente de Strings em memória.

log.error("...", id, e.getMessage()): Grava a falha com nível de severidade ERROR e insere a mensagem da exceção no log para posterior auditoria.

Exemplo de Configuração de Alertas no Grafana:

Disparar alerta via Slack/E-mail quando a taxa de respostas HTTP 500 ultrapassar 5% do tráfego total por mais de 2 minutos.

Disparar alerta quando o consumo de memória RAM da JVM ultrapassar 85% por 5 minutos consecutivos.

Boas Práticas

Escolha adequadamente o nível do log (INFO para eventos normais, ERROR para falhas críticas).

Utilize identificadores únicos de rastreio (Trace ID) para correlacionar chamadas entre microsserviços.

Adicione tags explicativas às métricas personalizadas para facilitar a filtragem no Grafana.

Pontos Importantes para Estudar

Os papéis complementares dos três pilares: Logs, Métricas e Traces.

A diferença operacional entre Monitoramento (coleta) e Observabilidade (inferência).

A aplicação dos níveis de log INFO, DEBUG, WARN e ERROR.

O fluxo de coleta de métricas unindo Actuator, Micrometer, Prometheus e Grafana.

Fechamento do encontro
Neste encontro, aprendemos que manter sistemas em produção requer visibilidade em tempo real. Entendemos como logs explicam o histórico dos eventos, como métricas descrevem a saúde do ambiente e como a observabilidade integrada com Prometheus e Grafana nos permite diagnosticar a causa-raiz de falhas antes que elas impactem gravemente os usuários.

## Encontro 29 — Segurança Avançada e Revisão Final do Projeto de Delivery

Introdução
Esta aula encerra o ciclo de aprendizagem abordando a arquitetura de segurança avançada em aplicações web corporativas e realizando a revisão geral dos requisitos para a entrega do projeto final de delivery. Serão analisadas as ameaças web mais comuns, a implementação de autenticação stateless com JWT, autorização baseada em papéis (RBAC), criptografia de senhas com BCrypt, validação rigorosa de entradas de dados, conformidade com a LGPD e a criação de pipelines seguros de CI/CD.

Segurança Avançada
A segurança avançada em desenvolvimento de software adota o princípio da "Defesa em Profundidade" (Defense in Depth), aplicando múltiplas camadas sobrepostas de proteção em todo o sistema. Em vez de depender apenas de uma tela de login simples, a segurança avançada engloba controle de acesso refinado, criptografia de dados em trânsito e em repouso, sanitização de requisições, auditoria de eventos e proteção na esteira de deploy.

Segurança como Requisito Não Funcional
A segurança deve ser tratada como um elemento estrutural desde a concepção do projeto ("Security by Design"). A ausência de segurança expõe a empresa a vazamentos de dados, sanções legais rigorosas e perda irreversível de reputação corporativa. Ela precisa ser equilibrada com a usabilidade e a performance do sistema.

Principais Ameaças Web

SQL Injection: Ataque em que um atacante injeta comandos SQL maliciosos em campos de entrada não sanitizados para ler, alterar ou apagar dados do banco relacional.

XSS (Cross-Site Scripting): Injeção de scripts JavaScript maliciosos em páginas web para roubar cookies de sessão ou credenciais de outros usuários.

CSRF (Cross-Site Request Forgery): Técnica que induz o navegador de uma vítima autenticada a enviar requisições indesejadas e não autorizadas para a aplicação.

Brute Force (Força Bruta): Tentativas automatizadas e repetitivas de adivinhar combinações de usuário e senha até obter acesso.

Autenticação Stateless com JWT (JSON Web Token)
O JWT é o padrão de mercado para autenticação em APIs RESTful. O token é gerado pelo servidor após o login e enviado ao cliente. O cliente insere o token no cabeçalho HTTP Authorization: Bearer <token> em todas as requisições subsequentes.

Vantagem Stateless: O servidor não precisa armazenar a sessão em memória, permitindo escalabilidade horizontal imediata.

Estrutura: É composto por três partes separadas por pontos (Header.Payload.Signature). O Payload contém os dados do usuário e suas permissões.

Controle de Acesso Baseado em Perfis (RBAC)
No sistema de delivery, o acesso aos endpoints deve ser delimitado por papéis (Roles) específicos:

ADMIN: Possui acesso irrestrito a todas as funcionalidades, gerenciamento de usuários, restaurantes e relatórios globais.

RESTAURANTE: Gerencia o cardápio, produtos, preços e atualiza o status dos pedidos da sua loja.

CLIENTE: Pode consultar produtos, criar pedidos, efetuar pagamentos e acompanhar suas entregas.

Criptografia de Senhas com BCrypt
As senhas nunca podem ser salvas em texto limpo no banco de dados. Utiliza-se a classe BCryptPasswordEncoder do Spring Security, que aplica o algoritmo BCrypt.

Salt e Hash: O BCrypt adiciona automaticamente uma string aleatória (Salt) à senha antes de aplicar uma função hash iterativa. Isso garante que senhas iguais gerem hashes completamente diferentes no banco, protegendo o sistema contra ataques de Rainbow Tables.

Proteção CSRF e CORS

CSRF: É desativado (http.csrf().disable()) em APIs REST stateless que utilizam autenticação por tokens JWT, pois a ausência de cookies de sessão elimina essa vulnerabilidade.

CORS (Cross-Origin Resource Sharing): Mecanismo de segurança que bloqueia requisições vindas de domínios ou portas diferentes do backend. Deve ser configurado para permitir chamadas exclusivamente a partir da URL oficial do frontend.

Conformidade Legal e LGPD
O tratamento de dados no sistema de delivery deve atender aos princípios da Lei Geral de Proteção de Dados (LGPD):

Proteção a Dados Sensíveis: Criptografia obrigatória de dados financeiros, de geolocalização e histórico de compras.

Transparência e Consentimento: Termos de uso claros e opção para exclusão da conta a pedido do titular.

Logs de Auditoria: Registro das ações de alteração de senha, atualização de dados pessoais e permissões para fins de rastreabilidade legal.

Segurança na Esteira CI/CD (DevSecOps)
A validação de segurança deve ser automatizada dentro do pipeline de integração contínua antes do deploy em produção:

Análise Estática de Código (SAST): Ferramentas como SonarQube analisam o código-fonte em busca de falhas de segurança e más práticas.

Análise de Dependências: Ferramentas como OWASP Dependency Check e Snyk inspecionam o arquivo pom.xml identificando bibliotecas desatualizadas com vulnerabilidades conhecidas (CVEs).

Código e Implementação de Segurança Prática

Configuração Central do Spring Security (SecurityConfig):

Java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/restaurante/**").hasRole("RESTAURANTE")
                .requestMatchers("/api/cliente/**").hasAnyRole("CLIENTE", "ADMIN")
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
Explicação do código:

csrf.disable(): Desativa a proteção contra CSRF, permitindo chamadas REST via token.

SessionCreationPolicy.STATELESS: Configura o Spring Security para não criar nem manter sessões HTTP no servidor.

hasRole("ADMIN"): Restringe o acesso dos endpoints com padrão /api/admin/** exclusivamente a usuários que possuem o papel de Administrador.

BCryptPasswordEncoder: Registra o Bean de criptografia de senhas para ser injetado nos serviços de cadastro.

Criptografia de Senha no Serviço de Usuário:

Java
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(UsuarioDTO dto) {
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(senhaCriptografada);
        usuario.setPerfil(dto.getPerfil());

        return usuarioRepository.save(usuario);
    }
}
Explicação do código:

passwordEncoder.encode(dto.getSenha()): Converte a senha em texto plano digitada pelo usuário em um hash irreversível do BCrypt antes de persistir no banco de dados.

Validação de Entradas com Bean Validation no DTO:

Java
public class UsuarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Email(message = "Formato de e-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", 
             message = "A senha deve conter letras maiúsculas, minúsculas e números.")
    private String senha;

    @NotNull(message = "O perfil de acesso é obrigatório.")
    private PerfilEnum perfil;
}
Explicação do código:

@NotBlank / @NotNull: Impede o envio de campos nulos ou vazios.

@Pattern: Exige uma expressão regular que obriga a criação de senhas fortes com números e caracteres maiúsculos e minúsculos, evitando ataques de força bruta.

Exemplo de Pipeline CI/CD Seguro no GitHub Actions (.github/workflows/ci-cd.yml):

YAML
name: Delivery App CI/CD

on:
  push:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2

      - name: Configurar JDK 21
        uses: actions/setup-java@v2
        with:
          java-version: '21'
          distribution: 'adopt'

      - name: Executar Testes Automatizados
        run: mvn test

      - name: Build da Imagem Docker
        run: docker build -t delivery-api .
Explicação do YAML:

Define o fluxo de automação que baixa o código, configura o ambiente Java 21, executa todos os testes automatizados da aplicação e gera a imagem do container Docker de forma segura a cada commit na branch principal.

Boas Práticas

Nunca armazene senhas ou tokens de acesso em texto limpo.

Aplique validações rigorosas em todos os DTOs para impedir ataques de injeção de código.

Mantenha as dependências do Maven atualizadas para prevenir vulnerabilidades de segurança conhecidas.

Pontos Importantes para Estudar

O conceito de Defesa em Profundidade e segurança por design.

As diferenças entre as ameaças SQL Injection, XSS e CSRF.

O fluxo de autenticação stateless utilizando tokens JWT e filtros no Spring Security.

A aplicação do algoritmo BCrypt para geração de hash de senhas com Salt.

Fechamento do encontro
Neste encontro final, consolidamos os conhecimentos essenciais para construir e entregar uma aplicação segura e pronta para produção. Vimos como o Spring Security, aliado ao uso de tokens JWT, controle de acesso refinado (RBAC), criptografia BCrypt e pipelines automatizados de CI/CD, permite proteger o sistema de delivery contra as principais ameaças web, garantindo o alinhamento com os padrões de qualidade e exigências da LGPD.