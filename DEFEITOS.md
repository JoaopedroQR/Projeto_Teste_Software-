# DEFEITOS.md
 
Registro das falhas encontradas pela suíte de testes contra o sistema da biblioteca.
O resultado esperado vem sempre da especificação (Anexo A). Nenhum valor esperado foi ajustado ao que o sistema devolve, e `RegrasBiblioteca` não foi corrigida.
 
- **Fase** é a fase em que um teste revelou a falha: **1** (caixa-preta, contra `lib/biblioteca-1.0.jar`) ou **2** (caixa-branca, contra o código-fonte).
- **Causa** é o defeito distinto no código (K1 a K13, seção D), identificado na fase 2 lendo `RegrasBiblioteca.java`. Uma causa pode gerar várias falhas.
## Resumo
 
Suíte da fase 1: **120 testes, 47 falhas** (execução com o código-fonte e o JaCoCo integrados, antes dos testes novos da fase 2).
 
| Situação | Falhas | Fase |
|---|---|---|
| A. Identificadas (entrada e valores conferidos) | 26 | 1 |
| B. Entrada inferida, agora confirmada pelo código | 5 | 1 |
| C. Ainda sem a linha de dados (causa já confirmada pelo código) | 16 | 1 |
| E. Novas, reveladas só na fase 2 (`multaTotalCentavos`) | 4 | 2 |
 
Defeitos distintos: **13 causas** (K1 a K13). K1 a K11 foram reveladas na fase 1. **K12 e K13 foram reveladas só na fase 2**, porque a suíte da fase 1 não tinha nenhum teste de `multaTotalCentavos` (cobertura de 0%).
 
> Depois de rodar `mvn test` com `MultaTotalCentavosTest`, atualizar a linha "Última execução" acima e conferir a coluna "Obtido" da seção E.
 
---
 
## A. Falhas identificadas
 
| # | Fase | Método | Entrada | Esperado | Obtido | Classe de equivalência / limite que levou ao teste | Causa |
|---|------|--------|---------|----------|--------|-----------------------------------------------------|-------|
| 1 | 1 | `prazoDias` | `VISITANTE` | 7 | 10 | PD-V3: classe válida, visitante | K1 |
| 2 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-21 (sábado) | 2026-11-28 | 2026-12-01 | DD-VL7: devolução num sábado, sem ajuste | K1 |
| 3 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-23 (segunda) | 2026-11-30 | 2026-12-03 | DD-VL9: devolução numa segunda, sem ajuste | K1 |
| 4 | 1 | `dataDevolucao` | `PROFESSOR`, retirada 2026-11-20 (sexta; prazo 30 cai em domingo 2026-12-20) | 2026-12-21 | 2026-12-20 | DD-VL5: devolução num domingo passa para a segunda | K2 |
| 5 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-22 (domingo) | 2026-11-30 | 2026-12-03 | DD-VL8: devolução num domingo passa para a segunda | K1 (e K2) |
| 6 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-12-27 (domingo) | 2027-01-04 | 2027-01-07 | DD-VL11: domingo na virada de ano | K1 (e K2) |
| 7 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2027-01-24 (domingo) | 2027-02-01 | 2027-02-04 | DD-VL10: domingo no fim de mês | K1 (e K2) |
| 8 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo | K3 |
| 9 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo | K3 |
| 10 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo | K3 |
| 11 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 11` | 1200 | 1100 | MC-VL5: 11º dia cobrado, primeiro da faixa de R$ 2,00 | K4 |
| 12 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 11` | 1200 | 1100 | MC-VL5: 11º dia cobrado, primeiro da faixa de R$ 2,00 | K4 |
| 13 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 31` | 5000 | 5200 | MC-VL8: primeiro valor acima do teto de R$ 50,00 | K6 |
| 14 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 31` | 5000 | 5200 | MC-VL8: primeiro valor acima do teto de R$ 50,00 | K6 |
| 15 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 3` | 0 | 100 | MC-VL11: último dia de carência | K5 |
| 16 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 4` | 100 | 200 | MC-VL12: 1º dia cobrado do professor | K5 |
| 17 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 13` | 1000 | 1100 | MC-VL13: 10º dia cobrado, último da faixa de R$ 1,00 | K4 e K5 |
| 18 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 14` | 1200 | 1400 | MC-VL14: 11º dia cobrado, primeiro da faixa de R$ 2,00 | K5 |
| 19 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 33` | 5000 | 5200 | MC-VL15: 30º dia cobrado, multa chega a R$ 50,00 | K5 e K6 |
| 20 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 34` | 5000 | 5400 | MC-VL16: 31º dia cobrado, primeiro acima do teto | K5 e K6 |
| 21 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 100` | 5000 | 19000 | MC-VL9: atraso muito grande | K6 |
| 22 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 100` | 5000 | 18600 | MC-VL17: atraso muito grande | K5 e K6 |
| 23 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 100` | 5000 | 19000 | MC-VL9: atraso muito grande | K6 |
| 24 | 1 | `podeEmprestar` | `ALUNO`, ativos = 3, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo | K7 |
| 25 | 1 | `podeEmprestar` | `PROFESSOR`, ativos = 8, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo | K7 |
| 26 | 1 | `podeEmprestar` | `VISITANTE`, ativos = 1, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo | K7 |
 
---
 
## B. Falhas com entrada inferida, confirmadas pelo código
 
Teste `PodeEmprestarTest.tabelaDeDecisao`, 5 falhas (esperado `false`, obtido `true`). O console não mostra a linha. Todas usam `ALUNO` (limite 3). Na fase 2, o cálculo manual sobre o código (`ativos <= limite && (multa < 1000 || !atraso)`) confirmou que estas cinco entradas devolvem `true` e que as regras R7 e R8 devolvem `false`, o que bate com o resultado observado (5 falhas). Conferir só se a linha da IDE é a mesma.
 
| # | Fase | Método | Entrada (ativos, multa, atraso) | Esperado | Obtido | Regra da tabela de decisão | Causa |
|---|------|--------|---------------------------------|----------|--------|----------------------------|-------|
| 27 | 1 | `podeEmprestar` | 3, 500, `false` | `false` | `true` | R2: só os ativos violam a regra | K7 |
| 28 | 1 | `podeEmprestar` | 1, 1500, `false` | `false` | `true` | R3: só a multa viola a regra | K8 |
| 29 | 1 | `podeEmprestar` | 1, 500, `true` | `false` | `true` | R4: só o atraso viola a regra | K8 |
| 30 | 1 | `podeEmprestar` | 3, 1500, `false` | `false` | `true` | R5: ativos e multa violam | K7 e K8 |
| 31 | 1 | `podeEmprestar` | 3, 500, `true` | `false` | `true` | R6: ativos e atraso violam | K7 e K8 |
 
Nas regras R7 (1, 1500, `true`) e R8 (3, 1500, `true`) o sistema respondeu corretamente `false`.
 
---
 
## C. Falhas sem a linha de dados (causa confirmada pelo código)
 
O console não permite deduzir a linha de dados. Cada uma deve virar uma linha na tabela A depois de copiada a entrada da árvore de testes da IDE (clicar no teste parametrizado e ler os argumentos de cada linha vermelha).
 
| Teste | Nº de falhas | Resultado | Causa pelo código | O que falta |
|---|---|---|---|---|
| `PodeEmprestarTest.limiteDaMultaPendente` | 9 | esperado `false`, obtido `true` | **K8.** Com 0 ativos e sem atraso, `!possuiAtraso` é `true` e a multa acima de R$ 10,00 nunca bloqueia. | Os 9 valores de multa (todos acima de 1000) |
| `PodeEmprestarTest.valoresNegativosLancamExcecao` | 3 | esperado `IllegalArgumentException`, nenhuma exceção | **K9.** O código valida só `emprestimosAtivos < 0`. As 3 linhas são as de `multaPendenteCentavos` negativa com ativos maiores ou iguais a 0. | Os 3 valores |
| `PodeRenovarTest.limiteDeRenovacoesPorTipo` | 3 | esperado `false`, obtido `true` | **K10 e K11.** As únicas entradas com esperado `false` e obtido `true` são `PROFESSOR` com 3 renovações, `VISITANTE` com 0 e `VISITANTE` com 1. | Confirmar quais 3 linhas |
| `PodeRenovarTest.reservaOuAtrasoImpedemRenovacao` | 1 | esperado `false`, obtido `true` | **Provável K11** (visitante que renova sem reserva e sem atraso) ou K10 (professor com 3). O `&& !possuiReserva && !emAtraso` está correto. | Confirmar a linha |
 
---
 
## D. Causas no código (confirmadas na fase 2)
 
Antes da fase 2, estas causas eram as hipóteses D1 a D8, feitas só pelo comportamento. A leitura de `RegrasBiblioteca.java` confirmou todas (D1 = K3, D2 = K4, D3 = K6, D4 = K5, D5 = K1, D6 = K2, D7 = K7, D8 = K8).
 
| Causa | Método | Código | Deveria ser (spec) | Fase em que foi revelada | Falhas |
|---|---|---|---|---|---|
| K1 | `prazoDias` | `default: return 10` | visitante = 7 | 1 | 1 a 3, 5 a 7 |
| K2 | `dataDevolucao` | testa o domingo em `retirada.getDayOfWeek()` | testar o domingo na **data de devolução** | 1 | 4 (e parte de 5 a 7) |
| K3 | `multaCentavos` | não valida `diasAtraso < 0` | lançar `IllegalArgumentException` | 1 | 8 a 10 (e 35) |
| K4 | `multaCentavos` | `if (dias <= 11)` | `dias <= 10` (o 11º dia já é cobrado a R$ 2,00) | 1 | 11, 12, 17 |
| K5 | `multaCentavos` | `diasAtraso - 2` | carência do professor = 3 dias (`diasAtraso - 3`) | 1 | 15 a 20, 22 |
| K6 | `multaCentavos` | `TETO_MULTA_CENTAVOS` declarada e nunca usada | multa limitada a 5000 | 1 | 13, 14, 19 a 23 |
| K7 | `podeEmprestar` | `emprestimosAtivos <= limiteEmprestimos(tipo)` | `<` (ativos menores que o limite) | 1 | 24 a 27, 30, 31 |
| K8 | `podeEmprestar` | `(multa < 1000 \|\| !possuiAtraso)` | multa e atraso valem juntos (`&&`) | 1 | 28 a 31 e as 9 da seção C |
| K9 | `podeEmprestar` | valida só `emprestimosAtivos < 0` | validar também `multaPendenteCentavos < 0` | 1 | 3 da seção C |
| K10 | `podeRenovar` | `(tipo == PROFESSOR) ? 4 : 2` | professor até 3 renovações | 1 | seção C |
| K11 | `podeRenovar` | visitante cai no `: 2` do ternário | visitante nunca renova | 1 | seção C |
| K12 | `multaTotalCentavos` | `for (int i = 1; ...)` | começar em `i = 0` (o 1º empréstimo é ignorado) | **2** | 32, 34 (e 35 para o índice 0) |
| K13 | `multaTotalCentavos` | `total = TETO_MULTA_CENTAVOS` (5000) | o total é limitado a 10000 (`TETO_MULTA_TOTAL_CENTAVOS`) | **2** | 33 |
 
### Observação sem falha visível
 
- **L1, `podeEmprestar`:** `multa < TOLERANCIA_MULTA_CENTAVOS` deveria ser `<=` (multa de no máximo R$ 10,00). Hoje esse desvio está **mascarado pelo K8** (`||`) e não produz nenhuma saída errada. Quando o K8 for corrigido para `&&`, a entrada multa = 1000, ativos < limite e sem atraso passará a devolver `false` em vez de `true`. Foi encontrado só por leitura do código, e um teste de caixa-preta não o distingue do comportamento correto.
---
 
## E. Falhas novas da fase 2 (`multaTotalCentavos`)
 
A suíte da fase 1 não testava `multaTotalCentavos`. Os testes abaixo são os caminhos do conjunto-base (CAIXA-BRANCA.md) e dois extras, em `MultaTotalCentavosTest`. A coluna "Obtido" foi calculada manualmente sobre o código e **deve ser conferida** ao rodar `mvn test`.
 
| # | Fase | Método | Entrada | Esperado | Obtido (previsto) | Teste que levou à falha | Causa |
|---|------|--------|---------|----------|-------------------|--------------------------|-------|
| 32 | 2 | `multaTotalCentavos` | `ALUNO`, `{5, 12}` | 1900 | 1400 | `caminho3_somaDosEmprestimos`: o laço executa, total abaixo do teto | K12 |
| 33 | 2 | `multaTotalCentavos` | `ALUNO`, `{25, 25, 25, 25}` | 10000 | 5000 | `caminho4_totalLimitadoAoTeto`: total acima de R$ 100,00 | K13 |
| 34 | 2 | `multaTotalCentavos` | `ALUNO`, `{7}` | 700 | 0 | `listaComUmElemento`: lista de 1 elemento | K12 |
| 35 | 2 | `multaTotalCentavos` | `ALUNO`, `{5, -1}` | `IllegalArgumentException` | nenhuma exceção (devolve -100) | `elementoNegativoLancaExcecao`: elemento negativo | K3 (e K12 para o índice 0) |
 
Passam, como esperado: `caminho1_listaVazia`, `caminho2_listaNulaLancaExcecao` e `tipoNuloLancaExcecao`.
 
### Notas
 
- **Testes de caminho de `multaCentavos` e `podeRenovar`:** falham pelas mesmas causas já listadas, e não são defeitos novos. Exemplo: PROFESSOR com 5 dias dá 300 em vez de 200 (K5), e PROFESSOR com 3 renovações dá `true` em vez de `false` (K10).
- **Interação entre defeitos:** mesmo depois de corrigir K3, a entrada `{-1, 5}` não lançaria exceção, porque K12 ignora o índice 0.
- **Cobertura:** a seção E fecha a lacuna de cobertura de `multaTotalCentavos` (de 0% para 100% de instruções e de ramos), mas defeitos como K6 (teto ausente) e K9 (validação ausente) existem em métodos que já tinham 100% de ramos. Só a comparação com a especificação os revela.
 