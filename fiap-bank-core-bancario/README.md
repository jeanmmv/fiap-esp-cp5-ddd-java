# Core Bancário — FIAP Bank

Serviço REST em Java 21 / Spring Boot, organizado em quatro módulos Maven: `domain`, `infrastructure`, `application` e `presentation`.

## Executar
Na pasta `fiap-bank-core-bancario`, execute `mvn clean install` e depois `mvn -pl fiap-bank-core-bancario-presentation spring-boot:run`. Serviço: `http://localhost:8080`. Console H2: `http://localhost:8080/h2-console`, JDBC `jdbc:h2:mem:corebancariodb`, usuário `sa`, senha vazia.

## Endpoints
- `GET/POST /api/v1/clientes`
- `GET/PUT/DELETE /api/v1/clientes/{id}`
- `GET /api/v1/clientes/cpf/{cpf}`
- `GET/POST /api/v1/contas`
- `GET /api/v1/contas/{numero}`
- `GET /api/v1/contas/cliente/{cpf}`
- `PUT /api/v1/contas/{numero}/saldo` com `{"operacao":"DEPOSITO|SAQUE","valor":100.00}`
- `DELETE /api/v1/contas/{numero}` encerra a conta (soft close).

O domínio não usa Spring nem anotações JPA. O mapeamento de persistência fica na infraestrutura e a apresentação trabalha com DTOs.
