# Plataforma de Crédito — FIAP Bank

Serviço responsável pelo ciclo de vida das solicitações de crédito: criação, consulta e registro da decisão operacional.

## Responsabilidades

- Receber e validar dados de uma solicitação.
- Orquestrar o caso de uso na camada de aplicação.
- Persistir as solicitações por meio de um adaptador de repositório.
- Encaminhar integrações por gateways, mantendo os contratos no domínio.
- Expor endpoints REST e respostas de erro consistentes.
- Manter rotas legadas de compatibilidade usadas pelas interfaces existentes.

## Organização

| Módulo | Responsabilidade |
|---|---|
| `fiap-bank-plataforma-credito-domain` | Entidade de domínio, status, contratos de repositório e gateways |
| `fiap-bank-plataforma-credito-application` | Casos de uso e DTOs de aplicação |
| `fiap-bank-plataforma-credito-infrastructure` | Persistência, clientes HTTP/Feign e adaptadores |
| `fiap-bank-plataforma-credito-presentation` | Controllers REST, validação HTTP e inicialização da aplicação |

## Executar

Na pasta deste módulo:

```bash
mvn clean install
cd fiap-bank-plataforma-credito-presentation
mvn spring-boot:run
```

Por padrão, a API fica disponível em `http://localhost:8081`.

## Endpoints REST

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/api/v1/solicitacoes-credito` | Criar solicitação |
| `GET` | `/api/v1/solicitacoes-credito` | Listar solicitações; aceita filtro opcional `cpf` |
| `GET` | `/api/v1/solicitacoes-credito/{id}` | Consultar por identificador |
| `PUT` | `/api/v1/solicitacoes-credito/{id}/decisao` | Registrar decisão e parecer |

### Exemplo de corpo para criar solicitação

```json
{
  "cpfCliente": "12345678901",
  "nomeCliente": "Cliente de Exemplo",
  "tipoCredito": "PESSOAL",
  "valorSolicitado": 5000.00,
  "quantidadeParcelas": 24,
  "rendaMensal": 3500.00
}
```

Use dados fictícios ao testar. O exemplo ilustra os campos do contrato; a resposta e o comportamento devem ser confirmados executando a aplicação.

## Observações

- Os gateways de SCR, Bureau e notificação fazem parte do cenário de integração da aplicação e não devem ser descritos como acesso real a bases oficiais sem configuração e credenciais apropriadas.
- A integração com o Core Bancário depende de o serviço correspondente estar disponível e configurado.
- Consulte [`../COMO_EXECUTAR_E_VALIDAR.md`](../COMO_EXECUTAR_E_VALIDAR.md) para o roteiro integrado.
