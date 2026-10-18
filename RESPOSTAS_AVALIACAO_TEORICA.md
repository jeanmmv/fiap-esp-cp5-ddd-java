# Avaliação teórica — Checkpoint 5

## 1. Componentes que violavam o isolamento do domínio
No estado inicial da Plataforma de Crédito, `model.SolicitacaoCredito` concentrava os dados de negócio e anotações JPA (`@Entity`, `@Table`, `@Column`, `@Id`). Assim, o modelo de domínio ficava acoplado à persistência. O serviço `PlataformaCreditoService` acessava diretamente `SolicitacaoCreditoRepository`, que estendia `JpaRepository`, e também concentrava regras de crédito, persistência, integrações e notificações. O DTO de resposta era construído diretamente a partir da entidade JPA. Essas decisões dificultavam testes isolados e mudanças de infraestrutura.

No Core Bancário, o objetivo da refatoração é manter entidades de domínio e contratos de repositório no módulo `domain`, deixando mapeamentos JPA, Spring Data e adaptadores no módulo `infrastructure`. A camada `application` coordena os casos de uso e os DTOs; `presentation` expõe os contratos HTTP.

## 2. Endpoints que violavam HTTP/REST
A API inicial da Plataforma usava `POST` para todas as operações, inclusive consultas, com verbos de ação na URI: `/solicitarCredito`, `/consultarStatusCredito`, `/buscarSolicitacaoPorId`, `/listarTodasSolicitacoes`, `/aprovarSolicitacao` e `/reprovarSolicitacao`. Consultas deveriam usar `GET`; criação, `POST`; atualização de decisão, `PUT` ou `PATCH`; e a URI deve representar o recurso.

## 3. Estratégia de refatoração sem quebrar clientes legados
Foi criada uma API REST sob `/api/v1/solicitacoes-credito`, com `POST` para criar, `GET` para listar/buscar e `PUT` para registrar decisão. Um controller adaptador mantém os paths históricos `/api/credito/**` e converte as requisições antigas para os mesmos casos de uso da aplicação. Dessa forma, Mesa de Operações e Internet Banking continuam chamando os contratos antigos enquanto novas integrações podem usar a API REST. O adaptador é temporário e deve ser coberto por testes de contrato.
