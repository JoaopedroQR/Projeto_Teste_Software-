# DEFEITOS.md
 
Registro das falhas encontradas na suíte de testes contra `lib/biblioteca-1.0.jar` (fase 1, caixa-preta).
O resultado esperado vem sempre da especificação (Anexo A). Nenhum valor esperado foi ajustado ao que o sistema devolve.
 
## Resumo
 
Última execução: **120 testes, 47 falhas**.
 
| Situação | Falhas |
|---|---|
| A. Identificadas (entrada e valores conferidos) | 26 |
| B. Com entrada inferida (a confirmar na árvore de testes da IDE) | 5 |
| C. Ainda sem entrada identificada | 16 |
 
---
 
## A. Falhas identificadas
 
| # | Fase | Método | Entrada | Esperado | Obtido | Classe de equivalência / limite que levou ao teste |
|---|------|--------|---------|----------|--------|-----------------------------------------------------|
| 1 | 1 | `prazoDias` | `VISITANTE` | 7 | 10 | PD-V3: classe válida, visitante |
| 2 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-21 (sábado) | 2026-11-28 | 2026-12-01 | DD-VL7: devolução num sábado, sem ajuste |
| 3 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-23 (segunda) | 2026-11-30 | 2026-12-03 | DD-VL9: devolução numa segunda, sem ajuste |
| 4 | 1 | `dataDevolucao` | `PROFESSOR`, retirada 2026-11-20 (sexta; prazo 30 cai em domingo 2026-12-20) | 2026-12-21 | 2026-12-20 | DD-VL5: devolução num domingo passa para a segunda |
| 5 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-11-22 (domingo) | 2026-11-30 | 2026-12-03 | DD-VL8: devolução num domingo passa para a segunda |
| 6 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2026-12-27 (domingo) | 2027-01-04 | 2027-01-07 | DD-VL11: domingo na virada de ano |
| 7 | 1 | `dataDevolucao` | `VISITANTE`, retirada 2027-01-24 (domingo) | 2027-02-01 | 2027-02-04 | DD-VL10: domingo no fim de mês |
| 8 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo |
| 9 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo |
| 10 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | MC-I2: `diasAtraso` negativo |
| 11 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 11` | 1200 | 1100 | MC-VL5: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 12 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 11` | 1200 | 1100 | MC-VL5: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 13 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 31` | 5000 | 5200 | MC-VL8: primeiro valor acima do teto de R$ 50,00 |
| 14 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 31` | 5000 | 5200 | MC-VL8: primeiro valor acima do teto de R$ 50,00 |
| 15 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 3` | 0 | 100 | MC-VL11: último dia de carência |
| 16 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 4` | 100 | 200 | MC-VL12: 1º dia cobrado do professor |
| 17 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 13` | 1000 | 1100 | MC-VL13: 10º dia cobrado, último da faixa de R$ 1,00 |
| 18 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 14` | 1200 | 1400 | MC-VL14: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 19 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 33` | 5000 | 5200 | MC-VL15: 30º dia cobrado, multa chega a R$ 50,00 |
| 20 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 34` | 5000 | 5400 | MC-VL16: 31º dia cobrado, primeiro acima do teto |
| 21 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 100` | 5000 | 19000 | MC-VL9: atraso muito grande |
| 22 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 100` | 5000 | 18600 | MC-VL17: atraso muito grande |
| 23 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 100` | 5000 | 19000 | MC-VL9: atraso muito grande |
| 24 | 1 | `podeEmprestar` | `ALUNO`, ativos = 3, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo |
| 25 | 1 | `podeEmprestar` | `PROFESSOR`, ativos = 8, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo |
| 26 | 1 | `podeEmprestar` | `VISITANTE`, ativos = 1, multa = 0, atraso = `false` | `false` | `true` | PE-VL4: ativos igual ao limite do tipo |
 
---
 
## B. Falhas com entrada inferida (confirmar na árvore de testes da IDE)
 
Teste `PodeEmprestarTest.tabelaDeDecisao`, 5 falhas (esperado `false`, obtido `true`). O console não mostra a linha. As linhas abaixo são as únicas que se explicam em conjunto pelas hipóteses D7 e D8 (seção D). Todas usam `ALUNO` (limite 3).
 
| # | Fase | Método | Entrada (ativos, multa, atraso) | Esperado | Obtido | Regra da tabela de decisão |
|---|------|--------|---------------------------------|----------|--------|----------------------------|
| 27 | 1 | `podeEmprestar` | 3, 500, `false` | `false` | `true` | R2: só os ativos violam a regra |
| 28 | 1 | `podeEmprestar` | 1, 1500, `false` | `false` | `true` | R3: só a multa viola a regra |
| 29 | 1 | `podeEmprestar` | 1, 500, `true` | `false` | `true` | R4: só o atraso viola a regra |
| 30 | 1 | `podeEmprestar` | 3, 1500, `false` | `false` | `true` | R5: ativos e multa violam |
| 31 | 1 | `podeEmprestar` | 3, 500, `true` | `false` | `true` | R6: ativos e atraso violam |
 
Nas regras R7 (1, 1500, `true`) e R8 (3, 1500, `true`) o sistema respondeu corretamente `false`.
 
---
 
## C. Falhas ainda sem entrada identificada
 
Falhas cujo console não permite deduzir a linha de dados. Cada uma deve virar uma linha na tabela A depois de conferida a árvore de testes da IDE.
 
| Teste | Nº de falhas | Resultado | O que se sabe |
|---|---|---|---|
| `PodeEmprestarTest.limiteDaMultaPendente` | 9 | esperado `false`, obtido `true` | Entram aqui as entradas com multa acima de R$ 10,00 (1001 e acima), com 0 ativos e sem atraso. |
| `PodeEmprestarTest.valoresNegativosLancamExcecao` | 3 | esperado `IllegalArgumentException`, nenhuma exceção | Três das sete linhas: ou os ativos negativos, ou a multa negativa, não são rejeitados. |
| `PodeRenovarTest.limiteDeRenovacoesPorTipo` | 3 | esperado `false`, obtido `true` | O sistema renova quando não deveria, em três das sete linhas com esperado `false`. |
| `PodeRenovarTest.reservaOuAtrasoImpedemRenovacao` | 1 | esperado `false`, obtido `true` | O sistema renova em uma das oito combinações de reserva e atraso que deveriam resultar em `false`. |
 
---
 
## D. Hipóteses de causa
 
Inferidas só pelo comportamento observado, sem consultar o código-fonte. Serão confirmadas ou descartadas na fase 2.
 
- **D1, falta de validação de `diasAtraso` negativo** em `multaCentavos` (falhas 8 a 10). A validação de `tipo` nulo funciona.
- **D2, fronteira da faixa de R$ 2,00** em `multaCentavos` (falhas 11, 12 e 17). Com exatamente 11 dias cobrados o sistema devolve 11 × R$ 1,00. Com 12 dias cobrados o resultado já está correto (1400).
- **D3, teto de R$ 50,00 não aplicado** em `multaCentavos` (falhas 13, 14, 19, 20 e 21 a 23). Com 100 dias, o aluno paga 19000.
- **D4, carência do professor menor que 3 dias** em `multaCentavos` (falhas 15 a 20 e 22). Os resultados batem com uma carência de 2 dias.
- **D5, prazo do visitante de 10 dias em vez de 7.** Aparece direto em `prazoDias` (falha 1) e em `dataDevolucao` (falhas 2 e 3: obtido = retirada + 10 dias).
- **D6, ajuste do domingo em `dataDevolucao`.** Na falha 4, o prazo do professor leva a um domingo e a data não é ajustada. Nas falhas 5 a 7, a retirada é num domingo e o resultado vem 1 dia depois dos 10 dias do prazo do visitante, embora a devolução caia numa quarta-feira. Isso sugere que o sistema verifica o domingo na data de **retirada**, e não na data de **devolução**. Para aluno (14 dias) e visitante (7 dias) as duas datas caem no mesmo dia da semana, e por isso o aluno passou nos testes de domingo.
- **D7, limite de empréstimos ativos com comparação frouxa** em `podeEmprestar` (falhas 24 a 27, 30 e 31). O sistema permite quando os ativos são **iguais** ao limite do tipo.
- **D8, multa pendente e atraso tratados como alternativas** em `podeEmprestar` (falhas 28 a 31, e parte das falhas da seção C). As falhas da tabela de decisão se explicam se o sistema só bloquear quando há multa acima de R$ 10,00 **e** atraso ao mesmo tempo, em vez de bloquear por qualquer um dos dois.
 