# A analogia: DeliveryTech Express — uma empresa de logística internacional

Imagina um prédio de uma empresa que recebe encomendas, processa, guarda e entrega — com portaria, 
atendimento, gerência, armazém e regras bem definidas.

1. ## O PRÉDIO tem andares/departamentos (as pastas do projeto)

Cada pasta do seu projeto é um departamento dentro do prédio:

controller → Andar de Atendimento (balcões)
service → Andar da Gerência (quem decide)
model → Andar de Modelagem (os moldes de caixa)
repository → Andar do Armazém (quem guarda/busca)
dto/request e dto/response → Sala de Formulários (entrada e saída)
exceptions → Sala de Avisos e Placas
validation → Posto de Fiscalização
enums → Sala de Catálogos Fixos
config → Sala de Regras Gerais da Empresa
security → Portaria (novo!)

2. ## A PORTARIA — quem entra no prédio primeiro (security)

Ninguém entra direto nos balcões. Antes, passa pela portaria.

AuthController = o balcão de "Emissão de Crachá" — é aqui que a pessoa mostra documento (email + senha) 
e pede pra entrar
AuthRequest = o formulário que ela preenche pra pedir o crachá (email + senha)
JwtUtil = a máquina que fabrica crachás — ela grava no crachá quem é a pessoa e até quando ele vale, 
e coloca um "selo" impossível de falsificar
AuthResponse = o crachá pronto que ela recebe de volta
UserDetailsServiceImpl = a recepcionista que vai no arquivo de funcionários cadastrados (Usuario) 
conferir se aquela pessoa realmente existe e se a senha bate
JwtAuthenticationFilter = o segurança parado na porta, que confere o crachá de TODO MUNDO antes de 
deixar passar pra qualquer outro andar do prédio
SecurityConfig = o regulamento da portaria: diz quais salas são de acesso livre (ex: pedir crachá) e 
quais exigem crachá válido (ex: ver pedidos)
SecurityUtils = uma ficha rápida que qualquer departamento pode consultar: "quem é a pessoa que está 
aqui agora, com esse crachá?"
SecurityExceptionHandler = a placa específica de "Acesso Negado" que aparece quando o crachá é inválido 
ou expirou

3. ## O ANDAR DE ATENDIMENTO — os balcões (controller)

Cada balcão atende um tipo de assunto: ClienteController, PedidoController, ProdutoController, 
RestauranteController, AuthController.

O atendente do balcão:

Recebe o formulário preenchido (DTO)
Confere só o formato básico (isso é papel, não é o balde de lixo — o filtro fino de conteúdo é outro departamento)
Encaminha pra dentro, pro gerente decidir
Devolve a resposta pra quem chegou, sempre dentro de um envelope padrão

4. ## A SALA DE FORMULÁRIOS — os DTOs

Tem dois tipos de papel, guardados em gavetas diferentes:

Gaveta de ENTRADA (dto/request): formulários que a pessoa de fora preenche — ClienteDTO, PedidoDTO, 
RestauranteReqDTO, AuthRequest, StatusPedidoDTO, CalculoPedidoDTO. Só tem os campos que a pessoa PODE decidir.
Gaveta de SAÍDA (dto/response): comprovantes que a empresa devolve — ClienteResponseDTO, PedidoResponseDTO,
RestauranteResponseDTO, AuthResponse, etc. Tem campos extras que só a empresa sabe (tipo o número de 
identidade, se está ativo).

E tem dois tipos de envelope especial, que embrulham qualquer comprovante:

ApiResponseWrapper = o envelope padrão de toda resposta simples: "deu certo? aqui os dados, aqui uma mensagem"
PagedResponseWrapper = o mesmo envelope, mas quando a resposta é uma lista grande, entregue em 
fascículos numerados (páginas), não tudo de uma vez

5. ## O POSTO DE FISCALIZAÇÃO — as validações

Antes do formulário virar processo de verdade, um fiscal confere cada campo:

Fiscais prontos de fábrica: @NotBlank (não pode vir vazio), @Email (formato de email), @Size 
(tamanho certo), @Min/@Max (número dentro do limite)
Fiscais que você mesma treinou (pasta validation): @ValidCEP, @ValidTelefone, @ValidCategoria — cada um 
tem uma "ficha de regras" (CEPValidator, TelefoneValidator, CategoriaValidator) que diz exatamente o 
que aceitar ou recusar

> Importante: o fiscal barra o formulário errado. Diferente da "placa de vidro" do Swagger, que só 
> mostra a regra, sem fiscalizar de verdade.

6. ## O ANDAR DA GERÊNCIA — os Services

Cada assunto tem um gerente com uma lista de tarefas prometidas (a interface) e o gerente de verdade 
que cumpre essas tarefas (a Impl):

ClienteService (promessas) + ClienteServiceImpl (quem cumpre)
PedidoService + PedidoServiceImpl
ProdutoService + (ainda sem Impl visível na sua lista — pode ser que ainda seja uma classe única)
RestauranteService + RestauranteServiceImpl

O gerente é quem decide de verdade: "esse cliente está ativo? Esse produto pertence a esse restaurante? 
Pode mudar esse status pra aquele outro?"

Dentro da gerência, tem um tradutor automático (ModelMapper) que pega o formulário (DTO) e copia os 
dados pro molde de caixa oficial (Entidade) — e vice-versa, na hora de devolver a resposta.

7. ## ANDAR DE MODELAGEM — as Entidades (model, com @Entity)

Aqui ficam os moldes oficiais das caixas: Cliente, Pedido, ItemPedido, Produto, Restaurante, Usuario. 
Cada molde diz: quais informações a caixa carrega, o tamanho de cada uma, e como ela se conecta com 
outras caixas (ex: um Pedido está ligado a UM Cliente, mas TEM VÁRIOS itens).

O molde, sozinho, não guarda nada — ele só define a "forma". Quem guarda de verdade é o próximo andar.

8. ## O ARMAZÉM — os Repositories

Cada molde de caixa tem um funcionário do armazém responsável: ClienteRepository, PedidoRepository,
 ProdutoRepository, RestauranteRepository, UsuarioRepository.

Esse funcionário sabe guardar uma caixa nova na prateleira, buscar uma caixa específica, listar todas 
as caixas de um tipo, ou até fazer buscas customizadas (tipo "me dá só as caixas de status CONFIRMADO 
entre essas duas datas").

A prateleira em si é o banco de dados — o lugar físico (bem, digital) onde tudo realmente fica salvo, para sempre.

9. ## A SALA DE CATÁLOGOS FIXOS — os enums
StatusPedido = o catálogo de estágios possíveis de um pedido (RECEBIDO → CONFIRMADO → EM_PREPARO →
 SAIU_PARA_ENTREGA → ENTREGUE, ou CANCELADO)
StatusStock = um catálogo de situações de estoque (DISPONIVEL, INDISPONIVEL, ENTRADA, SAIDA)

Um catálogo fixo trava as opções — ninguém pode inventar um status novo que não esteja na lista.

10. ## A SALA DE AVISOS — as exceptions

Quando algo dá errado, uma placa específica é levantada:

EntityNotFoundException = "essa caixa que você procura não existe" (404)
BusinessException = "isso quebra uma regra da empresa" (400)
ConflictException = "já existe algo assim, não posso duplicar" (409)
GlobalExceptionHandler = o fiscal geral, escutando o prédio inteiro, decidindo qual placa mostrar e 
formatando a resposta de erro direitinho
SecurityExceptionHandler = a versão do fiscal geral, mas específica pra problemas de crachá/portaria

11. ## A SALA DE REGRAS GERAIS — config
ModelMapperConfig = ajusta como o tradutor automático deve se comportar
SwaggerConfig / OpenAPIConfig = montam a "placa de vidro" — a vitrine pública explicando o prédio pra quem visita
DataInitializer = no primeiro dia, já deixa alguns funcionários de teste cadastrados (usuários de login)
DataLoader (em component) = no primeiro dia, já deixa alguns clientes e pedidos de teste prontos
Juntando tudo — o fluxo completo, com os termos técnicos de verdade

# Aqui está o mesmo fluxo, agora na linguagem da API

1. Cliente externo faz POST /auth/login com { email, senha }
   → chega no AuthController
   → o corpo da requisição é um AuthRequest (DTO)

2. AuthController chama o Service de autenticação
   → UserDetailsServiceImpl busca o Usuario pelo email (UsuarioRepository)
   → confere a senha (criptografada) 
   → se bater, JwtUtil gera um token JWT assinado

3. AuthController devolve 200 OK com um AuthResponse { token }

4. Cliente externo agora faz, por exemplo, POST /pedidos
   → manda o header: Authorization: Bearer <token>

5. Antes de chegar no PedidoController, o JwtAuthenticationFilter intercepta:
   → valida o token com JwtUtil
   → se inválido/expirado → SecurityExceptionHandler devolve 401 Unauthorized
   → se válido → deixa a requisição continuar

6. PedidoController recebe o corpo da requisição como um PedidoDTO (@RequestBody @Valid)
   → o @Valid dispara as validações: @NotNull, @NotEmpty, @ValidCEP, etc.
   → se algum campo for inválido → GlobalExceptionHandler pega a MethodArgumentNotValidException
     e devolve 400 Bad Request com um ErrorResponse listando os campos com erro

7. Se passou na validação, o Controller chama pedidoService.criarPedido(dto)
   → PedidoServiceImpl busca o Cliente e o Restaurante (via seus Repositories)
   → confere regras de negócio (cliente ativo? restaurante ativo? produto pertence ao restaurante?)
   → se alguma regra quebrar → lança BusinessException ou EntityNotFoundException
     → GlobalExceptionHandler transforma isso em 400 ou 404, com ErrorResponse

8. Se tudo OK, o Service monta um objeto Pedido (a Entidade, @Entity)
   → pedidoRepository.save(pedido) grava no banco de dados
   → o banco gera o id automaticamente (@Id @GeneratedValue)

9. O Service converte o Pedido salvo de volta num PedidoResponseDTO
   → devolve pro Controller

10. O Controller embrulha esse PedidoResponseDTO dentro de um
    ApiResponseWrapper<PedidoResponseDTO> { success: true, data: {...}, message: "..." }
    → devolve HTTP 201 Created pro cliente externo