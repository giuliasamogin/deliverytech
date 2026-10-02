# Arquitetura do projeto — explicação técnica

1. ## Estrutura de pastas (arquitetura em camadas)

O projeto segue o padrão de arquitetura em camadas, onde cada pasta representa uma responsabilidade específica:

controller → camada de apresentação (recebe requisições HTTP)
service → camada de negócio (regras e validações)
model → camada de domínio (entidades JPA)
repository → camada de persistência (acesso ao banco)
dto/request e dto/response → objetos de transferência de dados
exceptions → tratamento centralizado de erros
validation → validações customizadas (Bean Validation)
enums → tipos enumerados fixos
config → configurações de beans e infraestrutura
security → autenticação e autorização

2. ## Segurança (Spring Security + JWT)

O projeto implementa autenticação stateless baseada em JWT (JSON Web Token):

AuthController — expõe o endpoint POST /api/auth/login
AuthRequest — DTO com email e senha
JwtUtil — componente responsável por gerar e validar tokens JWT, usando uma chave secreta (jwt.secret) e assinatura HMAC-SHA256
AuthResponse — DTO que retorna o token gerado
UserDetailsServiceImpl (ou AuthService) — implementa a interface UserDetailsService do Spring Security, buscando o usuário pelo email via UsuarioRepository
JwtAuthenticationFilter — filtro que intercepta toda requisição HTTP, extrai o token do header Authorization: Bearer <token>, valida-o e popula o contexto de segurança (SecurityContextHolder)
SecurityConfig — classe de configuração anotada com @EnableWebSecurity, que define a SecurityFilterChain: quais rotas são públicas (permitAll()) e quais exigem autenticação (anyRequest().authenticated()), além de registrar o PasswordEncoder (BCrypt) e o AuthenticationProvider
SecurityUtils — classe utilitária para obter dados do usuário autenticado a partir do SecurityContextHolder
SecurityExceptionHandler — handler específico para exceções de autenticação/autorização, retornando HTTP 401 ou 403

3. ## Camada de apresentação (Controllers)

Cada @RestController expõe endpoints REST via anotações @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping. Responsabilidades:

Receber o @RequestBody (DTO de entrada)
Delegar a lógica para a camada de serviço injetada via @Autowired
Encapsular a resposta em um wrapper padronizado
Retornar o ResponseEntity com o status HTTP apropriado

4. ## DTOs (Data Transfer Objects)

Separação entre objetos de entrada (dto/request) e saída (dto/response), evitando expor diretamente as entidades JPA na API. Isso garante:

Controle de quais campos são aceitos na entrada
Controle de quais campos são expostos na saída
Desacoplamento entre o modelo de persistência e o contrato da API

Dois wrappers padronizam as respostas:

ApiResponseWrapper<T> — encapsula qualquer resposta simples com success, data, message, timestamp
PagedResponseWrapper<T> — encapsula respostas paginadas, contendo content (lista da página atual) e page (metadados de paginação: número, tamanho, total de elementos, total de páginas)

5. ## Validação (Bean Validation / JSR-380)

Validação declarativa via anotações do jakarta.validation.constraints:

Anotações padrão: @NotBlank, @NotNull, @NotEmpty, @Size, @Email, @Min, @Max, @DecimalMin, @DecimalMax
Validações customizadas: implementadas via @Constraint(validatedBy = ...), onde cada anotação (@ValidCEP, @ValidTelefone, @ValidCategoria) delega a lógica de validação para uma classe que implementa ConstraintValidator<A, T>

A ativação ocorre via @Valid no parâmetro do Controller. Se a validação falhar, o Spring lança MethodArgumentNotValidException, capturada pelo GlobalExceptionHandler.

6. ## Camada de serviço (Service Layer)

Segue o padrão interface + implementação:

A interface define o contrato (assinaturas dos métodos)
A classe *Impl, anotada com @Service, contém a lógica de negócio

Responsabilidades típicas:

Validação de regras de negócio (ex: verificar se uma entidade está ativa antes de processar)
Orquestração de múltiplos repositórios
Conversão entre entidade e DTO via ModelMapper (modelMapper.map(origem, Classe.class))
Controle transacional via @Transactional (garantindo atomicidade — se uma operação falhar no meio, todas as alterações são revertidas)

7. ## Camada de domínio (Entidades JPA)

Classes anotadas com @Entity, mapeadas para tabelas do banco via JPA/Hibernate:

@Id + @GeneratedValue(strategy = GenerationType.IDENTITY) define a chave primária autoincrementada
@Column configura atributos da coluna (nullable, unique)
@ManyToOne / @OneToMany mapeiam relacionamentos entre tabelas
@Enumerated(EnumType.STRING) persiste enums como texto, não como índice numérico

A entidade define a estrutura da tabela, mas não realiza operações de persistência — isso é responsabilidade do Repository.

8. ## Camada de persistência (Repositories)

Interfaces que estendem JpaRepository<Entidade, TipoDaChave>, herdando automaticamente métodos CRUD (save, findById, findAll, delete, etc.). Métodos adicionais são declarados por convenção de nome (query methods, ex: findByClienteId) ou via @Query com JPQL para consultas mais complexas.

9. ## Enums

Tipos fixos que restringem valores possíveis, garantindo integridade em tempo de compilação:

StatusPedido — ciclo de vida do pedido
StatusStock — controle de estoque
Role — perfis de usuário (usado no controle de autorização)

10. ## Tratamento de exceções

Hierarquia de exceções customizadas, todas estendendo RuntimeException (ou uma classe base comum, BusinessException):

EntityNotFoundException → mapeada para HTTP 404
BusinessException → mapeada para HTTP 400
ConflictException → mapeada para HTTP 409

O GlobalExceptionHandler, anotado com @ControllerAdvice, centraliza o tratamento via métodos @ExceptionHandler, convertendo cada exceção em uma resposta padronizada (ErrorResponse) com o código HTTP apropriado — evitando que stack traces sejam expostos ao cliente.

11. ## Configuração (Beans)

Classes anotadas com @Configuration, contendo métodos @Bean que registram objetos gerenciados pelo Spring:

ModelMapperConfig — configura a estratégia de mapeamento (MatchingStrategies.STRICT, nível de acesso a campos privados)
SwaggerConfig/OpenAPIConfig — configuram metadados da documentação OpenAPI (título, versão, descrição)
DataInitializer/DataLoader — implementam CommandLineRunner, populando o banco com dados de teste na inicialização da aplicação

# Fluxo técnico completo de uma requisição autenticada

1. POST /api/auth/login { email, senha }
   → AuthController.login()
   → AuthenticationManager.authenticate(UsernamePasswordAuthenticationToken)
   → DaoAuthenticationProvider consulta AuthService (UserDetailsService)
   → PasswordEncoder.matches() confere a senha (hash BCrypt)

2. Se autenticado: JwtUtil.generateToken(userDetails)
   → retorna AuthResponse { token }

3. Requisições subsequentes incluem: Authorization: Bearer <token>

4. JwtAuthenticationFilter (executado antes do DispatcherServlet processar o Controller):
   → extrai o token do header
   → JwtUtil.validateToken() confere assinatura e expiração
   → popula SecurityContextHolder com a Authentication
   → se inválido: SecurityExceptionHandler retorna 401

5. Requisição chega ao Controller (ex: POST /api/pedidos):
   → @Valid @RequestBody PedidoDTO dto
   → Bean Validation dispara: @NotNull, @NotEmpty, @ValidCEP...
   → se falhar: GlobalExceptionHandler captura MethodArgumentNotValidException → 400

6. PedidoController delega para PedidoService.criarPedido(dto)
   → busca Cliente e Restaurante via seus Repositories
   → valida regras de negócio (ativo, disponibilidade, pertencimento)
   → se falhar: lança BusinessException/EntityNotFoundException → GlobalExceptionHandler → 400/404

7. Monta a entidade Pedido, persiste via pedidoRepository.save()
   → @GeneratedValue gera o id automaticamente

8. Converte a entidade salva em PedidoResponseDTO (via ModelMapper ou mapeamento manual)

9. Controller encapsula em ApiResponseWrapper<PedidoResponseDTO>
   → retorna HTTP 201 Created

