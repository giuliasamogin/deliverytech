#  Fases do projeto

> Fase 1 — Instalação de ferramentas
Instalamos as bases pra conseguir programar em Java:

Java → a linguagem de programação em si
Maven → organiza e monta o projeto, baixa as bibliotecas que o projeto precisa automaticamente
Git → guarda o histórico de todas as mudanças no código, como um "controle de versões"
GitHub → a "nuvem" onde o código fica guardado e acessível de qualquer lugar

> Fase 2 — Construir o esqueleto
Criamos a estrutura inicial do projeto usando o Spring Boot, que é um framework — um "kit de peças prontas" que já vem com muita coisa configurada, pra não precisar montar um servidor web do zero. Também adicionamos o Lombok, uma biblioteca que gera automaticamente código repetitivo (como os get/set), economizando trabalho.

> Fase 3 — Guardar histórico
Conectamos o projeto no Git e subimos pro GitHub pela primeira vez. Cada "salvamento oficial" se chama commit — é como tirar uma foto do estado do código naquele momento, pra poder voltar nele depois se precisar.

> Fase 4 — Primeiros testes vivos
Criamos o HealthController.java, o controller mais simples possível. Ele "escuta" quando alguém acessa localhost:8080/health e devolve uma resposta em JSON (texto estruturado, sem visual) confirmando que o sistema está no ar. Foi o primeiro teste de que tudo estava funcionando.

> Fase 5 — Documentar
Escrevemos o README.md, um arquivo de texto que explica do que se trata o projeto — "a placa com informações na frente da obra", pra qualquer pessoa entender rapidamente o que é aquele repositório.

> Fase 6 — "A fundação": as Entidades (@Entity)
Criamos as classes Cliente, Produto, Restaurante e Pedido. Cada uma vira uma tabela no banco de dados, com atributos (nome, email, preço...) que viram colunas, e métodos get/set pra acessar esses dados. Também definimos relações entre elas — por exemplo, um Produto pertence a um Restaurante, e um Pedido pertence a um Cliente.

> Fase 7 — "O coração": os Services
Criamos a camada de regras de negócio. Diferente do Repository (que só salva/busca no banco sem pensar), o Service decide o que pode ou não ser feito antes de salvar ou consultar algo. Por exemplo: o ClienteService decide se pode cadastrar (email já existe?), o PedidoService decide se pode criar um pedido (cliente ativo? produto disponível?).

> Fase 8 — Controllers REST
Criamos os Controllers, que são os "atendentes" do sistema — recebem as requisições de fora (de um app, navegador, ou ferramenta de teste), repassam pro Service decidir o que fazer, e devolvem a resposta pra quem pediu.

> Fase 9 — DataLoader
Criamos uma classe que, assim que o sistema liga, popula o banco de dados automaticamente com alguns dados de teste (clientes, produtos, pedidos fictícios) — assim você não precisa cadastrar tudo manualmente toda vez que for testar o projeto.

>  Fase 10 — Projeções e DTOs
Começamos a usar DTOs (Data Transfer Object) — classes separadas que servem só pra receber dados de fora ou mostrar o que o usuário pode ver, sem expor a entidade do banco diretamente. "Projeções" são parecidas, mas mais simples: um jeito de pedir ao banco só alguns campos específicos de uma consulta, sem trazer o objeto inteiro.

> Fase 11 — Organização do application.properties
Esse arquivo não tem código, só configurações da aplicação — "é lá que se coloca as informações que o projeto precisa para rodar": porta do servidor (8080), configuração do banco de dados H2, nome do banco, etc. É tipo o painel de controle da aplicação.

> Fase 12 — ModelMapper (o "tradutor automático")
Criamos o ModelMapperConfig.java pra configurar uma ferramenta que copia dados automaticamente entre duas classes parecidas (ex: Cliente e ClienteDTO), sem precisar escrever cliente.setNome(dto.getNome()) campo por campo na mão.

> Fase 13 — Services viram interface + Impl
Transformamos o Service numa dupla: a interface (ClienteService) é só a "lista de promessas" — diz o que vai existir, sem dizer como funciona. A Impl (ClienteServiceImpl) é quem cumpre essas promessas de verdade, com o código escrito dentro. Isso separa "o contrato" de "a implementação".

> Fase 14 — Exceptions (as placas de aviso)
Criamos três tipos de erro customizados: EntityNotFoundException (quando algo buscado não existe → erro 404), BusinessException (quando uma regra de negócio é quebrada → erro 400), e o GlobalExceptionHandler, que funciona como um "fiscal geral" — intercepta qualquer erro que aconteça no sistema e devolve uma resposta organizada em JSON, em vez de deixar o programa quebrar feio.

> Fase 15 — Pedido: criarPedido()
Implementamos o método mais complexo até então. Antes de criar um pedido de verdade, o sistema confere em sequência: o cliente existe e está ativo? O restaurante existe e está ativo? Cada produto pedido existe, pertence a esse restaurante, e está disponível? Só depois de passar por todas essas checagens é que o pedido é montado e salvo.

> Fase 16 — Calcular o total do pedido
Usamos BigDecimal (em vez de double) pra trabalhar com dinheiro, porque ele evita erros de arredondamento. E usamos stream().map().reduce() — uma forma "moderna" do Java de processar uma lista: pega cada item, extrai o valor dele, e soma tudo numa única operação encadeada, em vez de um for tradicional.

> Fase 17 — @Transactional no Pedido
Como criar um pedido envolve várias operações no banco ao mesmo tempo (salvar o pedido + salvar os itens), usamos @Transactional pra garantir que, se algo der errado no meio do caminho, tudo que foi feito até ali seja desfeito — nunca fica "pela metade" salvo no banco.

> Fase 18 — Controllers REST: anotações principais
Revisamos o significado de cada anotação usada nos Controllers: @RestController (responde em JSON), @RequestMapping (caminho base), @GetMapping/@PostMapping/@PutMapping/@PatchMapping/@DeleteMapping (cada verbo HTTP representa uma ação diferente: buscar, criar, atualizar tudo, atualizar parte, remover), @PathVariable (pega valor da URL), @RequestBody (pega o JSON enviado).

> Fase 19 — DTOs: separação clara
Reforçamos o conceito: Entidade (vai pro banco) ≠ DTO de entrada (o que a API recebe) ≠ DTO de saída (o que a API devolve). Isso evita expor informação demais ou receber dados inventados por quem está usando a API.

> Fase 20 — Validações nos DTOs (Bean Validation)
Aplicamos anotações prontas do Java pra bloquear dados inválidos antes mesmo de chegar no Service: @NotBlank (não pode vir vazio), @Email (precisa ter formato de email), @Pattern (precisa seguir um molde específico, como telefone com certo número de dígitos).

> Fase 21 — Swagger na prática (documentação, não validação!)
Implementamos @Schema(description = "...", example = "...") em cada campo dos DTOs. É importante entender: o Swagger só documenta/exibe essas informações numa página visual (/swagger-ui.html) — ele não bloqueia nenhum dado errado. Quem realmente valida continua sendo @NotBlank, @Email, etc. É como uma placa explicando a regra de uma catraca — quem trava de verdade é o mecanismo por dentro, não a placa.

> Fase 22 — RestauranteReqDTO e RestauranteResponseDTO
O professor separou os nomes dos DTOs do Restaurante: Req (de "Requisição") é o que a API recebe no cadastro/atualização; Response é o que a API devolve. Mesma ideia do ClienteDTO/ClienteResponseDTO, só com nomenclatura diferente. O Req tem: nome, categoria, endereço, telefone, taxaEntrega, tempoEntrega, horarioFuncionamento. O Response tem os mesmos campos, mais id (identidade única gerada pelo banco) e ativo (se o restaurante está disponível no sistema).

> Fase 23 — Taxa e tempo de entrega "de verdade"
Antes, usávamos uma taxa de entrega fixa (R$5,00 pra todo mundo) só pra simplificar. Nessa fase, passamos a usar o valor real de cada restaurante (restaurante.getTaxaEntrega()), tornando o cálculo do pedido mais realista.

> Fase 24 — Lista de categorias oficial
Atualizamos o CategoriaValidator com a lista de categorias que o professor realmente usa: Italiana, Brasileira, Japonesa, Mexicana, Árabe — substituindo a lista provisória que tínhamos criado antes.

> Fase 25 — RestauranteService padronizado
O RestauranteService, que antes era uma classe única (sem interface), foi reorganizado pra seguir o mesmo padrão do Cliente e Pedido: RestauranteService virou a interface, e RestauranteServiceImpl passou a ser quem cumpre o trabalho de verdade, usando ModelMapper.

> Fase 26 — Pastas renomeadas
As pastas de DTOs mudaram de nome: dto/requisicao e dto/resposta viraram dto/request e dto/response. A função continua a mesma (entrada e saída), só mudou o idioma do nome das pastas.

> Fase 27 — PedidoController completo
O Controller de Pedido ganhou endpoints novos: listar pedidos com filtro de status/data e paginação, buscar pedidos de um cliente específico (histórico), buscar pedidos de um restaurante específico, e calcular o total de um pedido sem salvá-lo (uma simulação).

> Fase 28 — CalculoPedidoDTO e CalculoPedidoResponseDTO
Criamos esses dois DTOs específicos pro novo endpoint de "calcular sem salvar": o CalculoPedidoDTO recebe a lista de itens, e o CalculoPedidoResponseDTO devolve só o valor total calculado.

> Fase 29 — StatusPedidoDTO
Criamos um DTO bem simples, só com um campo status, que serve como "envelope" pra receber o novo status quando alguém quer atualizar um pedido via PATCH /pedidos/{id}/status.

> Fase 30 — Transições de status válidas
Implementamos uma regra que impede "pular" status aleatoriamente: o pedido só pode seguir a sequência RECEBIDO → CONFIRMADO → EM_PREPARO → SAIU_PARA_ENTREGA → ENTREGUE. E só pode ser cancelado enquanto estiver em RECEBIDO ou CONFIRMADO — depois disso, não dá mais pra desistir.

> Fase 31 — Introdução ao login/JWT
Apareceram, pela primeira vez, arquivos relacionados a autenticação: Usuario, JwtUtil, SecurityUtils, UserDetailsServiceImpl, DataInitializer. Entendemos o conceito de JWT (JSON Web Token) como um "crachá digital": a pessoa faz login uma vez, recebe um token, e usa esse token (em vez de login/senha) em todas as requisições seguintes, até ele expirar.

> Fase 32 — Usuario completo
Terminamos de configurar a entidade Usuario: adicionamos @Builder, @NoArgsConstructor, @AllArgsConstructor (pra criar objetos de formas diferentes), e os campos role (papel/permissão do usuário), ativo, dataCriacao e restauranteId. Essa classe também implementa UserDetails, uma interface do Spring Security que exige métodos como getUsername(), getPassword(), getAuthorities() — isso faz o Usuario "se encaixar" no sistema de login do Spring.

> Fase 33 — AuthController: o endpoint de login de verdade
Implementamos o POST /api/auth/login: a pessoa manda email+senha (AuthRequest), o sistema confere as credenciais usando o AuthenticationManager, e se estiver certo, o JwtUtil gera um token que é devolvido dentro de um AuthResponse.

> Fase 34 — SecurityConfig completo
Configuramos as regras gerais de segurança da API inteira: quais rotas são públicas (login, Swagger, H2 console) e quais exigem o token válido para serem acessadas. Também registramos o PasswordEncoder (usando BCrypt, que criptografa as senhas de forma irreversível) e o DaoAuthenticationProvider, que une a busca do usuário com a conferência da senha.