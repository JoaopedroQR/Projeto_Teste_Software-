# USO-DE-IA.md — Fase 1 (caixa-preta)
 
Assistente usado: **Claude (Anthropic)**, em conversa no claude.ai, com o enunciado, o `pom.xml`, o `LEIAME.md` e o `ExemploTest.java` do kit como contexto.
 
O código-fonte do sistema **não** foi consultado nem pedido à IA, e o `.jar` não foi descompilado. A IA trabalhou só com a especificação (Anexo A).
 
## 1. O que pedimos à IA
 
| # | Pedido |
|---|---|
| 1 | Explicar a proposta do projeto e o que fazer na fase 1. |
| 2 | Mostrar, para um método (`multaCentavos`), como derivar classes de equivalência e valores-limite a partir da especificação. |
| 3 | Orientar onde colocar a classe de teste no projeto Maven e como rodá-la. |
| 4 | Explicar a saída do `mvn test` e o que cada falha de `multaCentavos` significava. |
| 5 | Explicar conceitos do JUnit 5 que não entendemos: `assertEquals`, `@Test`, `@ParameterizedTest`, `@CsvSource`, o parâmetro `name =` e a origem de `TipoUsuario`. |
| 6 | Redigir o `DEFEITOS.md` a partir das falhas de `multaCentavos`. |
| 7 | Redigir o `PLANO-DE-TESTES.md` dos sete métodos.
| 8 | Mostrar como descobrir qual linha de um teste parametrizado falhou e interpretar as falhas de `podeEmprestar`. |
| 9 | Redigir este arquivo. |
 
## 2. O que aproveitamos
 
- **Método de trabalho**: separar entradas em classes de equivalência válidas e inválidas, escolher valores-limite nas fronteiras (abaixo, no limite e acima) e usar uma tabela de decisão para `podeEmprestar`.
- **`MultaCentavosTest`**: a estrutura com dados em `@CsvSource`, em que cada linha é um caso derivado de uma classe ou limite.
- **As classes de teste dos outros seis métodos**, com as datas e os totais esperados calculados a partir da regra da especificação.
- **`PLANO-DE-TESTES.md`** e **`DEFEITOS.md`**, com a rastreabilidade entre cada classe ou limite e o teste que o cobre.
- **A mensagem nos `assertEquals`** (`() -> "tipo=" + tipo + ...`) para identificar a entrada de cada falha no console.
## 3. O que tivemos de corrigir ou conferir
 
| Ponto | O que aconteceu |
|---|---|
| Defeito do 11º dia em `multaCentavos` | A primeira explicação da IA dizia que a faixa de R$ 2,00 "começava um dia tarde". Os resultados seguintes mostraram que o 12º dia estava correto e que só o 11º dia cobrado devolvia 1100. A descrição em `DEFEITOS.md` foi ajustada para isso. |
| Linhas que falharam | O console do Maven não mostra qual linha do CSV falhou. A IA deduziu o mapeamento comparando esperado e obtido, e nós conferimos pela árvore de testes da IDE (por exemplo, `ALUNO, 3 ativos -> false` em `podeEmprestar`). |
| Causa dos defeitos | A IA propôs hipóteses de causa (por exemplo, `<=` no lugar de `<` em `podeEmprestar`). Elas estão registradas como **hipóteses inferidas do comportamento**, e não como fatos sobre o código, que só será visto na fase 2.
 
## 4. Declaração
 
Os valores esperados de todos os testes vêm da especificação (Anexo A) e nenhum foi ajustado ao que o sistema devolve. Todos os integrantes devem saber justificar cada teste e cada defeito listado, na arguição.
 