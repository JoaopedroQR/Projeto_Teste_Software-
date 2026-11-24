# DEFEITOS.md
 
Registro das falhas encontradas na suíte de testes contra `lib/biblioteca-1.0.jar`.
O resultado esperado vem sempre da especificação (Anexo A). Nenhum valor esperado foi ajustado ao que o sistema devolve.
 
## Falhas
 
| # | Fase | Método | Entrada | Esperado | Obtido | Classe de equivalência / limite que levou ao teste |
|---|------|--------|---------|----------|--------|-----------------------------------------------------|
| 1 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | Classe inválida: `diasAtraso` negativo |
| 2 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | Classe inválida: `diasAtraso` negativo |
| 3 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = -1` | `IllegalArgumentException` | nenhuma exceção | Classe inválida: `diasAtraso` negativo |
| 4 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 11` | 1200 | 1100 | Limite: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 5 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 11` | 1200 | 1100 | Limite: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 6 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 31` | 5000 | 5200 | Limite: primeiro valor acima do teto de R$ 50,00 |
| 7 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 31` | 5000 | 5200 | Limite: primeiro valor acima do teto de R$ 50,00 |
| 8 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 3` | 0 | 100 | Limite: último dia de carência do professor |
| 9 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 4` | 100 | 200 | Limite: 1º dia cobrado do professor (logo após a carência) |
| 10 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 13` | 1000 | 1100 | Limite: 10º dia cobrado, último da faixa de R$ 1,00 |
| 11 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 14` | 1200 | 1400 | Limite: 11º dia cobrado, primeiro da faixa de R$ 2,00 |
| 12 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 33` | 5000 | 5200 | Limite: 30º dia cobrado, multa chega a R$ 50,00 |
| 13 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 34` | 5000 | 5400 | Limite: 31º dia cobrado, primeiro acima do teto |
| 14 | 1 | `multaCentavos` | `ALUNO`, `diasAtraso = 100` | 5000 | 19000 | Classe válida: atraso muito acima do teto |
| 15 | 1 | `multaCentavos` | `PROFESSOR`, `diasAtraso = 100` | 5000 | 18600 | Classe válida: atraso muito acima do teto |
| 16 | 1 | `multaCentavos` | `VISITANTE`, `diasAtraso = 100` | 5000 | 19000 | Classe válida: atraso muito acima do teto |
 
## Hipóteses de causa (inferidas só pelo comportamento observado)
 
Estas hipóteses vêm da comparação entre esperado e obtido. O código-fonte não foi consultado.
 
- **D1, falta de validação de dias negativos** (falhas 1 a 3): o método não rejeita `diasAtraso < 0`, embora a validação de `tipo` nulo funcione.
- **D2, fronteira da faixa de R$ 2,00** (falhas 4, 5 e 10): com 11 dias cobrados o sistema devolve o valor de 11 × R$ 1,00. Com 12 dias cobrados o resultado já está correto (1400), então o defeito aparece apenas exatamente no 11º dia cobrado. Suspeita de comparação com limite errado (por exemplo `> 11` no lugar de `> 10`).
- **D3, teto de R$ 50,00 não aplicado** (falhas 6, 7, 12, 13, 14, 15 e 16): a multa passa de 5000 e cresce sem limite (19000 com 100 dias para aluno).
- **D4, carência do professor menor que 3 dias** (falhas 8, 9, 11 e 15): os resultados do professor batem com uma carência de 2 dias. Com 3 dias de atraso já se cobra 1 dia.
As falhas 10, 11, 12, 13 e 15 do professor também sofrem influência de D2 e D3, combinadas com D4.
 