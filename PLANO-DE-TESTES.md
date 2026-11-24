# PLANO-DE-TESTES.md
 
Plano de testes de caixa-preta da classe `RegrasBiblioteca` (fase 1). Todas as classes de equivalência e todos os valores-limite saem da especificação (Anexo A do enunciado). Os resultados esperados vêm da especificação, nunca do que o sistema devolve.
 
## Convenções
 
- **CE** = classe de equivalência (`V` válida, `I` inválida). **VL** = valor-limite.
- Cada identificador tem o prefixo do método: `PD` (`prazoDias`), `DD` (`dataDevolucao`), `LE` (`limiteEmprestimos`), `MC` (`multaCentavos`), `PE` (`podeEmprestar`), `PR` (`podeRenovar`), `MT` (`multaTotalCentavos`).
- "Teste" indica a classe de teste (pacote `br.edu.biblioteca`), o método e, quando o teste é parametrizado, a linha de dados que cobre o caso.
- Em todos os métodos, `tipo` nulo lança `IllegalArgumentException` (IAE).
- Valores em centavos: R$ 10,00 = 1000.
- Para `TipoUsuario` (enum finito), cada valor é uma classe de equivalência com um único elemento. Por isso não há valor-limite numérico para o tipo.
| Método | Classe de teste |
|---|---|
| `prazoDias` | `PrazoDiasTest` |
| `dataDevolucao` | `DataDevolucaoTest` |
| `limiteEmprestimos` | `LimiteEmprestimosTest` |
| `multaCentavos` | `MultaCentavosTest` |
| `podeEmprestar` | `PodeEmprestarTest` |
| `podeRenovar` | `PodeRenovarTest` |
| `multaTotalCentavos` | `MultaTotalCentavosTest` |
 
---
 
## 1. `prazoDias(TipoUsuario tipo)`
 
**Entrada:** `tipo`.
 
### Classes de equivalência
 
| ID | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|
| PD-V1 | Válida: aluno | `ALUNO` | 14 | `PrazoDiasTest.prazoPorTipo` (linha `ALUNO,14`) |
| PD-V2 | Válida: professor | `PROFESSOR` | 30 | `PrazoDiasTest.prazoPorTipo` (linha `PROFESSOR,30`) |
| PD-V3 | Válida: visitante | `VISITANTE` | 7 | `PrazoDiasTest.prazoPorTipo` (linha `VISITANTE,7`) |
| PD-I1 | Inválida: tipo nulo | `null` | IAE | `PrazoDiasTest.tipoNuloLancaExcecao` |
 
### Valores-limite
 
Não se aplica: a única entrada é um enum, sem fronteira numérica.
 
---
 
## 2. `dataDevolucao(LocalDate retirada, TipoUsuario tipo)`
 
**Regra:** devolução = retirada + prazo do tipo (14, 30 ou 7 dias). Se cair num **domingo**, passa para a **segunda-feira seguinte**. O sábado é dia normal.
 
### Classes de equivalência
 
| ID | Classe | Esperado | Teste |
|---|---|---|---|
| DD-V1 | Tipo `ALUNO` (prazo 14) | retirada + 14 | linhas de `ALUNO` nos dois testes de data |
| DD-V2 | Tipo `PROFESSOR` (prazo 30) | retirada + 30 | linhas de `PROFESSOR` nos dois testes de data |
| DD-V3 | Tipo `VISITANTE` (prazo 7) | retirada + 7 | linhas de `VISITANTE` nos dois testes de data |
| DD-V4 | Resultado em dia que não é domingo | a própria data | `DataDevolucaoTest.devolucaoSemAjuste` |
| DD-V5 | Resultado num domingo | segunda seguinte (+1 dia) | `DataDevolucaoTest.devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-I1 | Inválida: retirada nula | IAE | `DataDevolucaoTest.retiradaNulaLancaExcecao` |
| DD-I2 | Inválida: tipo nulo | IAE | `DataDevolucaoTest.tipoNuloLancaExcecao` |
 
### Valores-limite (fronteira do domingo e do calendário)
 
O limite aqui é o dia da semana do resultado: sábado (dia anterior ao domingo), domingo, e segunda (dia posterior). Para cada tipo:
 
| ID | Limite | Tipo | Retirada | Esperado | Teste (linha) |
|---|---|---|---|---|---|
| DD-VL1 | Sábado: sem ajuste | ALUNO | 2026-11-21 | 2026-12-05 | `devolucaoSemAjuste` |
| DD-VL2 | Domingo: ajuste para segunda | ALUNO | 2026-11-22 | 2026-12-07 | `devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-VL3 | Segunda: sem ajuste | ALUNO | 2026-11-23 | 2026-12-07 | `devolucaoSemAjuste` |
| DD-VL4 | Sábado: sem ajuste | PROFESSOR | 2026-11-19 | 2026-12-19 | `devolucaoSemAjuste` |
| DD-VL5 | Domingo: ajuste para segunda | PROFESSOR | 2026-11-20 | 2026-12-21 | `devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-VL6 | Segunda: sem ajuste | PROFESSOR | 2026-11-21 | 2026-12-21 | `devolucaoSemAjuste` |
| DD-VL7 | Sábado: sem ajuste | VISITANTE | 2026-11-21 | 2026-11-28 | `devolucaoSemAjuste` |
| DD-VL8 | Domingo: ajuste para segunda | VISITANTE | 2026-11-22 | 2026-11-30 | `devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-VL9 | Segunda: sem ajuste | VISITANTE | 2026-11-23 | 2026-11-30 | `devolucaoSemAjuste` |
| DD-VL10 | Domingo no fim de mês (31/01 -> 01/02) | VISITANTE | 2027-01-24 | 2027-02-01 | `devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-VL11 | Domingo na virada de ano (03/01 -> 04/01) | VISITANTE | 2026-12-27 | 2027-01-04 | `devolucaoQueCaiNoDomingoVaiParaSegunda` |
| DD-VL12 | Dias comuns (sexta, terça, quinta) com virada de mês | ALUNO / PROFESSOR | 2026-11-20 / 2026-11-24 | 2026-12-04 / 2026-12-08 / 2026-12-24 | `devolucaoSemAjuste` |
 
---
 
## 3. `limiteEmprestimos(TipoUsuario tipo)`
 
**Entrada:** `tipo`.
 
### Classes de equivalência
 
| ID | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|
| LE-V1 | Válida: aluno | `ALUNO` | 3 | `LimiteEmprestimosTest.limitePorTipo` (linha `ALUNO,3`) |
| LE-V2 | Válida: professor | `PROFESSOR` | 8 | `LimiteEmprestimosTest.limitePorTipo` (linha `PROFESSOR,8`) |
| LE-V3 | Válida: visitante | `VISITANTE` | 1 | `LimiteEmprestimosTest.limitePorTipo` (linha `VISITANTE,1`) |
| LE-I1 | Inválida: tipo nulo | `null` | IAE | `LimiteEmprestimosTest.tipoNuloLancaExcecao` |
 
### Valores-limite
 
Não se aplica: a única entrada é um enum.
 
---
 
## 4. `multaCentavos(TipoUsuario tipo, int diasAtraso)`
 
**Regras:** professor tem 3 dias de carência (não cobrados); os demais pagam todos os dias. Dias cobrados 1 a 10: 100 por dia. Do 11º dia cobrado em diante: 200 por dia. Teto de 5000 por empréstimo. `diasAtraso` negativo e `tipo` nulo lançam IAE.
 
**Dias cobrados:** aluno e visitante = `diasAtraso`; professor = `max(0, diasAtraso - 3)`.
 
### Classes de equivalência
 
| ID | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|
| MC-V1 | Tipo sem carência: aluno | `ALUNO` | sem carência | `MultaCentavosTest.semCarencia` (linhas `ALUNO,...`) |
| MC-V2 | Tipo sem carência: visitante | `VISITANTE` | sem carência | `MultaCentavosTest.semCarencia` (linhas `VISITANTE,...`) |
| MC-V3 | Tipo com carência: professor | `PROFESSOR` | carência de 3 dias | `MultaCentavosTest.professorComCarencia` |
| MC-V4 | 0 dias cobrados | 0 dias | 0 | `semCarencia` (`ALUNO,0,0`, `VISITANTE,0,0`), `professorComCarencia` (`0,0`) |
| MC-V5 | Professor dentro da carência (1 a 3 dias) | 3 dias | 0 | `professorComCarencia` (linha `3,0`) |
| MC-V6 | Faixa de R$ 1,00 (1 a 10 dias cobrados) | 1 e 10 dias | 100 e 1000 | `semCarencia`, `professorComCarencia` |
| MC-V7 | Faixa de R$ 2,00 (11 a 30 dias cobrados) | 11, 12 e 30 dias | 1200, 1400 e 5000 | `semCarencia`, `professorComCarencia` |
| MC-V8 | Acima do teto (31 ou mais dias cobrados) | 31 e 100 dias | 5000 | `semCarencia`, `professorComCarencia`, `tetoComAtrasoGrande` |
| MC-I1 | Inválida: tipo nulo | `null` | IAE | `MultaCentavosTest.tipoNuloLancaExcecao` |
| MC-I2 | Inválida: `diasAtraso` negativo | -1 | IAE | `MultaCentavosTest.diasNegativosLancamExcecao` (os 3 tipos) |
 
### Valores-limite
 
**Aluno e visitante (sem carência)** — cada valor é testado para os dois tipos:
 
| ID | Limite | `diasAtraso` | Esperado | Teste (linha) |
|---|---|---|---|---|
| MC-VL1 | Logo abaixo do válido | -1 | IAE | `diasNegativosLancamExcecao` |
| MC-VL2 | Fronteira inválida/válida (sem atraso) | 0 | 0 | `semCarencia` |
| MC-VL3 | Primeiro dia cobrado | 1 | 100 | `semCarencia` |
| MC-VL4 | Último dia da faixa de R$ 1,00 | 10 | 1000 | `semCarencia` |
| MC-VL5 | Primeiro dia da faixa de R$ 2,00 | 11 | 1200 | `semCarencia` |
| MC-VL6 | Exemplo do enunciado (10 × 100 + 2 × 200) | 12 | 1400 | `semCarencia` (só aluno) |
| MC-VL7 | Multa chega a R$ 50,00 exatos | 30 | 5000 | `semCarencia` |
| MC-VL8 | Primeiro valor em que o teto atua (sem teto seria 5200) | 31 | 5000 | `semCarencia` |
| MC-VL9 | Atraso muito grande (teto ausente ou aplicado tarde) | 100 | 5000 | `tetoComAtrasoGrande` |
 
**Professor (carência de 3 dias)** — os mesmos limites deslocados em 3 dias:
 
| ID | Limite | `diasAtraso` | Dias cobrados | Esperado | Teste (linha) |
|---|---|---|---|---|---|
| MC-VL10 | Sem atraso | 0 | 0 | 0 | `professorComCarencia` |
| MC-VL11 | Último dia de carência | 3 | 0 | 0 | `professorComCarencia` |
| MC-VL12 | Primeiro dia cobrado | 4 | 1 | 100 | `professorComCarencia` |
| MC-VL13 | Último dia da faixa de R$ 1,00 | 13 | 10 | 1000 | `professorComCarencia` |
| MC-VL14 | Primeiro dia da faixa de R$ 2,00 | 14 | 11 | 1200 | `professorComCarencia` |
| MC-VL15 | Multa chega a R$ 50,00 exatos | 33 | 30 | 5000 | `professorComCarencia` |
| MC-VL16 | Primeiro valor em que o teto atua | 34 | 31 | 5000 | `professorComCarencia` |
| MC-VL17 | Atraso muito grande | 100 | 97 | 5000 | `tetoComAtrasoGrande` |
 
---
 
## 5. `podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)`
 
**Regra:** devolve `true` somente se as **três condições valem juntas**: (a) `emprestimosAtivos` **menor que** o limite do tipo (aluno 3, professor 8, visitante 1); (b) `multaPendenteCentavos` **no máximo** 1000; (c) `possuiAtraso` é `false`. Ativos ou multa negativos e `tipo` nulo lançam IAE.
 
### Classes de equivalência
 
| ID | Entrada | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|---|
| PE-V1 | `tipo` | Válida: aluno | `ALUNO` | limite 3 | `limiteDeEmprestimosAtivos` (linhas `ALUNO,...`) |
| PE-V2 | `tipo` | Válida: professor | `PROFESSOR` | limite 8 | `limiteDeEmprestimosAtivos` (linhas `PROFESSOR,...`) |
| PE-V3 | `tipo` | Válida: visitante | `VISITANTE` | limite 1 | `limiteDeEmprestimosAtivos` (linhas `VISITANTE,...`) |
| PE-V4 | `emprestimosAtivos` | Válida: de 0 até limite - 1 | 0, 2 (aluno) | condição (a) satisfeita | `limiteDeEmprestimosAtivos` |
| PE-V5 | `emprestimosAtivos` | Válida: igual ou acima do limite | 3, 4 (aluno) | `false` | `limiteDeEmprestimosAtivos` |
| PE-V6 | `multaPendenteCentavos` | Válida: de 0 a 1000 | 0, 1000 | condição (b) satisfeita | `limiteDaMultaPendente` |
| PE-V7 | `multaPendenteCentavos` | Válida: acima de 1000 | 1001 | `false` | `limiteDaMultaPendente` |
| PE-V8 | `possuiAtraso` | `false` | `false` | condição (c) satisfeita | `tabelaDeDecisao` (linha 1) |
| PE-V9 | `possuiAtraso` | `true` | `true` | `false` | `tabelaDeDecisao` (linha 4) |
| PE-I1 | `tipo` | Inválida: nulo | `null` | IAE | `tipoNuloLancaExcecao` |
| PE-I2 | `emprestimosAtivos` | Inválida: negativo | -1 | IAE | `valoresNegativosLancamExcecao` |
| PE-I3 | `multaPendenteCentavos` | Inválida: negativa | -1 | IAE | `valoresNegativosLancamExcecao` |
 
### Valores-limite
 
**`emprestimosAtivos`** (com multa 0 e sem atraso; o limite do tipo é L):
 
| ID | Limite | ALUNO (L = 3) | PROFESSOR (L = 8) | VISITANTE (L = 1) | Teste |
|---|---|---|---|---|---|
| PE-VL1 | Logo abaixo do válido: -1 | IAE | IAE | IAE | `valoresNegativosLancamExcecao` |
| PE-VL2 | Mínimo válido: 0 | `true` | `true` | `true` | `limiteDeEmprestimosAtivos` |
| PE-VL3 | L - 1 (último que permite) | 2: `true` | 7: `true` | 0: `true` | `limiteDeEmprestimosAtivos` |
| PE-VL4 | L (primeiro que nega) | 3: `false` | 8: `false` | 1: `false` | `limiteDeEmprestimosAtivos` |
| PE-VL5 | L + 1 | 4: `false` | 9: `false` | 2: `false` | `limiteDeEmprestimosAtivos` |
| PE-VL6 | Limite de outro tipo (não pode ser usado) | — | 3: `true` | — | `limiteDeEmprestimosAtivos` (linha `PROFESSOR,3,true`) |
 
**`multaPendenteCentavos`** (com 0 ativos e sem atraso), para os três tipos:
 
| ID | Limite | Valor | Esperado | Teste |
|---|---|---|---|---|
| PE-VL7 | Logo abaixo do válido | -1 | IAE | `valoresNegativosLancamExcecao` |
| PE-VL8 | Mínimo válido | 0 | `true` | `limiteDaMultaPendente` |
| PE-VL9 | Último valor que permite (R$ 10,00, "no máximo") | 1000 | `true` | `limiteDaMultaPendente` |
| PE-VL10 | Primeiro valor que nega | 1001 | `false` | `limiteDaMultaPendente` |
 
### Tabela de decisão (as três condições, aluno)
 
Representantes: ativos ok = 1, ativos no limite = 3; multa ok = 500, multa alta = 1500.
 
| Regra | (a) ativos < limite | (b) multa <= 1000 | (c) sem atraso | Esperado | Teste (linha de `tabelaDeDecisao`) |
|---|---|---|---|---|---|
| R1 | sim | sim | sim | `true` | `ALUNO,1,500,false,true` |
| R2 | não | sim | sim | `false` | `ALUNO,3,500,false,false` |
| R3 | sim | não | sim | `false` | `ALUNO,1,1500,false,false` |
| R4 | sim | sim | não | `false` | `ALUNO,1,500,true,false` |
| R5 | não | não | sim | `false` | `ALUNO,3,1500,false,false` |
| R6 | não | sim | não | `false` | `ALUNO,3,500,true,false` |
| R7 | sim | não | não | `false` | `ALUNO,1,1500,true,false` |
| R8 | não | não | não | `false` | `ALUNO,3,1500,true,false` |
 
---
 
## 6. `podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)`
 
**Regra:** visitante nunca renova. Aluno renova até 2 vezes (com 0 ou 1 renovação feita pode; com 2 não). Professor até 3 (com 0, 1 ou 2 pode; com 3 não). Em qualquer caso, só renova se **não há reserva** e **não há atraso**. `renovacoesFeitas` negativo lança IAE **para qualquer tipo**, inclusive visitante. `tipo` nulo lança IAE.
 
### Classes de equivalência
 
| ID | Entrada | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|---|
| PR-V1 | `tipo` | Válida: aluno (máx. 2) | `ALUNO` | renova se < 2 | `limiteDeRenovacoesPorTipo` |
| PR-V2 | `tipo` | Válida: professor (máx. 3) | `PROFESSOR` | renova se < 3 | `limiteDeRenovacoesPorTipo` |
| PR-V3 | `tipo` | Válida: visitante (nunca) | `VISITANTE` | `false` | `limiteDeRenovacoesPorTipo` |
| PR-V4 | `renovacoesFeitas` | Válida: abaixo do máximo do tipo | 0, 1 (aluno) | depende de reserva e atraso | `limiteDeRenovacoesPorTipo` |
| PR-V5 | `renovacoesFeitas` | Válida: no máximo ou acima | 2, 3 (aluno) | `false` | `limiteDeRenovacoesPorTipo` |
| PR-V6 | `possuiReserva` | `true` (obra reservada) | `true` | `false` | `reservaOuAtrasoImpedemRenovacao` |
| PR-V7 | `emAtraso` | `true` (empréstimo em atraso) | `true` | `false` | `reservaOuAtrasoImpedemRenovacao` |
| PR-V8 | reserva e atraso | Ambos `false` | `false`, `false` | pode renovar (aluno e professor) | `reservaOuAtrasoImpedemRenovacao` |
| PR-I1 | `tipo` | Inválida: nulo | `null` | IAE | `tipoNuloLancaExcecao` |
| PR-I2 | `renovacoesFeitas` | Inválida: negativo | -1 (os 3 tipos) | IAE | `renovacoesNegativasLancamExcecao` |
 
### Valores-limite de `renovacoesFeitas` (sem reserva e sem atraso)
 
| ID | Limite | ALUNO (máx. 2) | PROFESSOR (máx. 3) | VISITANTE (nunca) | Teste |
|---|---|---|---|---|---|
| PR-VL1 | Logo abaixo do válido: -1 | IAE | IAE | IAE | `renovacoesNegativasLancamExcecao` |
| PR-VL2 | Mínimo válido: 0 | `true` | `true` | `false` | `limiteDeRenovacoesPorTipo` |
| PR-VL3 | Máx. - 1 (último que permite) | 1: `true` | 2: `true` | — | `limiteDeRenovacoesPorTipo` |
| PR-VL4 | Máx. (primeiro que nega) | 2: `false` | 3: `false` | — | `limiteDeRenovacoesPorTipo` |
| PR-VL5 | Máx. + 1 | 3: `false` | 4: `false` | 1 e 3: `false` | `limiteDeRenovacoesPorTipo` |
 
### Combinações de reserva e atraso (0 renovações feitas)
 
| ID | Reserva | Atraso | ALUNO | PROFESSOR | VISITANTE | Teste |
|---|---|---|---|---|---|---|
| PR-C1 | não | não | `true` | `true` | `false` | `reservaOuAtrasoImpedemRenovacao` |
| PR-C2 | sim | não | `false` | `false` | — | `reservaOuAtrasoImpedemRenovacao` |
| PR-C3 | não | sim | `false` | `false` | — | `reservaOuAtrasoImpedemRenovacao` |
| PR-C4 | sim | sim | `false` | `false` | `false` | `reservaOuAtrasoImpedemRenovacao` |
 
---
 
## 7. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)`
 
**Regra:** soma a multa de cada empréstimo, cada uma calculada como em `multaCentavos` (inclusive carência por empréstimo e teto individual de 5000). O total nunca passa de 10000. Lista vazia dá 0. Lista nula, elemento negativo e `tipo` nulo lançam IAE.
 
### Classes de equivalência
 
| ID | Classe | Representante | Esperado | Teste |
|---|---|---|---|---|
| MT-V1 | Lista vazia | `{}` | 0 | `somaDasMultas` (linha 1) |
| MT-V2 | Lista só com empréstimos sem atraso | `{0, 0}` | 0 | `somaDasMultas` (linha 2) |
| MT-V3 | Lista com 1 elemento | `{12}` aluno | 1400 | `somaDasMultas` (linha 3) |
| MT-V4 | Lista com vários elementos, total abaixo do teto | `{5, 10, 12}` aluno | 2900 | `somaDasMultas` (linha 4) |
| MT-V5 | Carência do professor aplicada em cada empréstimo | `{3, 3}` e `{3, 3, 4}` professor | 0 e 100 | `somaDasMultas` (linhas 5 e 6) |
| MT-V6 | Teto individual aplicado antes da soma | `{100, 1}` aluno | 5100 | `somaDasMultas` (linha 7) |
| MT-V7 | Total acima do teto de R$ 100,00 | `{31, 31, 31}` aluno | 10000 | `somaDasMultas` (linha 11) |
| MT-I1 | Inválida: lista nula | `null` | IAE | `listaNulaLancaExcecao` (os 3 tipos) |
| MT-I2 | Inválida: elemento negativo | `{-1}`, `{-1, 5}`, `{5, -1, 3}`, `{5, -1}` | IAE | `elementoNegativoLancaExcecao` |
| MT-I3 | Inválida: tipo nulo | `null` | IAE | `tipoNuloLancaExcecao` |
 
### Valores-limite
 
| ID | Limite | Entrada (aluno) | Total sem teto | Esperado | Teste |
|---|---|---|---|---|---|
| MT-VL1 | Lista vazia (tamanho mínimo) | `{}` | 0 | 0 | `somaDasMultas` (linha 1) |
| MT-VL2 | Um elemento (tamanho 1) | `{12}` | 1400 | 1400 | `somaDasMultas` (linha 3) |
| MT-VL3 | Elemento zero (mínimo válido) | `{0, 0}` | 0 | 0 | `somaDasMultas` (linha 2) |
| MT-VL4 | Elemento negativo: logo abaixo do válido | `{-1}` | — | IAE | `elementoNegativoLancaExcecao` |
| MT-VL5 | Negativo em cada posição (início, meio, fim) | `{-1, 5}`, `{5, -1, 3}`, `{5, -1}` | — | IAE | `elementoNegativoLancaExcecao` |
| MT-VL6 | Logo abaixo do teto do total | `{30, 29}` | 9800 | 9800 | `somaDasMultas` (linha 8) |
| MT-VL7 | Exatamente no teto do total (R$ 100,00) | `{30, 30}` | 10000 | 10000 | `somaDasMultas` (linha 9) |
| MT-VL8 | Logo acima do teto do total | `{30, 30, 1}` | 10100 | 10000 | `somaDasMultas` (linha 10) |
 
---
 
## Observações
 
- Um teste que falha é um sucesso: revelou um defeito. As falhas são registradas em `DEFEITOS.md`, com o ID da classe ou do limite que levou ao teste.
- Os testes de `multaCentavos` já foram executados contra o `.jar` e suas falhas estão em `DEFEITOS.md`. Os demais métodos serão executados e registrados da mesma forma.
 