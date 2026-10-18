# Como executar e validar o Checkpoint 5 no Windows

## 1. Instalar ferramentas
1. Instale o JDK 21 e confirme em um novo terminal: `java -version`.
2. Instale Apache Maven 3.9+ e confirme: `mvn -version`.
3. Instale Node.js LTS para executar o Angular e confirme: `node -v` e `npm -v`.

O projeto usa dependências Maven e npm, então a primeira compilação precisa de internet para baixá-las.

## 2. Compilar os backends
Abra o PowerShell na pasta raiz do projeto extraído. Execute cada comando em sequência:

```powershell
cd .\fiap-bank-core-bancario
mvn clean install
cd ..\fiap-bank-plataforma-credito
mvn clean install
cd ..\fiap-bank-mesa-operacoes-credito
mvn clean package
cd ..
```

Se qualquer comando falhar, pare e guarde a primeira mensagem de erro completa. Não tente iniciar o sistema enquanto a compilação não terminar com `BUILD SUCCESS`.

## 3. Iniciar os serviços
Abra quatro terminais PowerShell, todos na pasta raiz do projeto, e execute um serviço por terminal. Deixe os terminais abertos.

**Terminal 1 — Core Bancário (porta 8080):**
```powershell
cd .\fiap-bank-core-bancario\fiap-bank-core-bancario-presentation
mvn spring-boot:run
```

**Terminal 2 — Plataforma de Crédito (porta 8081):**
```powershell
cd .\fiap-bank-plataforma-credito\fiap-bank-plataforma-credito-presentation
mvn spring-boot:run
```

**Terminal 3 — Mesa de Operações (porta 8082):**
```powershell
cd .\fiap-bank-mesa-operacoes-credito
mvn spring-boot:run
```

**Terminal 4 — Internet Banking Mobile (porta 4200):**
```powershell
cd .\fiap-bank-internet-banking-mobile
npm install
npm start
```

Acesse `http://localhost:4200` para o Internet Banking e `http://localhost:8082` para a Mesa.

## 4. Testar os endpoints principais
Com os serviços iniciados, use PowerShell ou Postman.

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/clientes
Invoke-RestMethod http://localhost:8080/api/v1/contas
Invoke-RestMethod http://localhost:8081/api/v1/solicitacoes-credito
```

A API REST da Plataforma de Crédito usa `POST /api/v1/solicitacoes-credito` para criar, `GET /api/v1/solicitacoes-credito` para listar, `GET /api/v1/solicitacoes-credito/{id}` para buscar e `PUT /api/v1/solicitacoes-credito/{id}/decisao` para registrar decisão. O controller legado continua oferecendo as rotas `/api/credito/**` para manter a comunicação dos front-ends existentes.

## 5. Se houver erro
- `mvn` não reconhecido: Maven não está instalado ou não está no `PATH`; feche e reabra o terminal após configurar.
- `java` não reconhecido ou versão diferente: configure o JDK 21 no `PATH` e `JAVA_HOME`.
- Falha ao baixar dependências: confira a conexão e tente novamente.
- Erro de compilação: copie a primeira mensagem de erro e o nome do arquivo/linha.
- Front-end não conecta: confirme que as portas 8080, 8081, 8082 e 4200 estão livres e que Core e Plataforma iniciaram sem falha.

## Estado da validação
O XML dos 11 arquivos `pom.xml` foi validado e os dois módulos `domain` foram compilados diretamente com `javac`. A compilação Maven completa, os testes Spring/JPA e o teste integrado dos quatro serviços ainda precisam ser executados em um ambiente com Maven e acesso às dependências. Não considere a entrega pronta até verificar esses passos.
