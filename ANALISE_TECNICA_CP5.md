# Análise técnica — Checkpoint 5

## Principais falhas iniciais identificadas
1. Entidade JPA representava diretamente o modelo de domínio.
2. Serviço de crédito misturava motor de regras, repositório, comunicação externa e notificação.
3. Consultas usavam `POST` e URIs de ação.
4. DTOs de entrada não validavam campos obrigatórios, CPF, valor nem quantidade de parcelas.
5. Exceções de recurso ausente não tinham mapeamento HTTP global apropriado.
6. A chamada ao Core usava fallback com saldo e conta fictícios, que poderiam ser apresentados como reais.
7. As decisões da Mesa não validavam transições de estado.

## Alterações feitas nesta versão
- Plataforma de Crédito dividida em quatro módulos Maven.
- Domínio de solicitação separado de sua entidade JPA.
- Contrato de repositório no domínio e adaptador de persistência na infraestrutura.
- Gateways para SCR, Bureau, Core e notificações.
- DTOs de entrada com Bean Validation e handler global.
- API REST `/api/v1` e adaptador de compatibilidade `/api/credito/**`.
- Core Bancário permanece dividido em quatro módulos; revise a hierarquia de dependências e execute o build localmente antes da entrega.

## Limitações conhecidas
A compilação Maven e os testes de integração não puderam ser executados neste ambiente por falta de Maven e indisponibilidade de rede para instalá-lo. Portanto, esta versão não deve ser considerada validada até que `mvn clean install` seja executado nos dois projetos backend e os quatro serviços sejam testados juntos.
