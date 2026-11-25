# Projeto de Teste de Software — Biblioteca
 
Trabalho da disciplina de **Engenharia de Software**, curso de **Engenharia de Computação**.
 
## Grupo
 
- João Pedro Queiroz
- Pablo Santiago
- Gustavo Monteiro
## Sobre o projeto
 
Suíte de testes em JUnit 5 para um sistema de biblioteca em Java que contém defeitos. O objetivo é testar o sistema, e não construí-lo. O projeto tem três fases:
 
1. **Caixa-preta:** testes por partição de equivalência e análise de valor-limite, a partir da especificação.
2. **Caixa-branca:** cobertura de código e complexidade ciclomática.
3. **TDD:** implementação da classe `FilaReservas`.
Situação atual: **fase 1 (caixa-preta)**.
 
## Estrutura
 
| Caminho | Conteúdo |
|---|---|
| `src/test/java/br/edu/biblioteca/` | Suíte de testes |
| `lib/biblioteca-1.0.jar` | Sistema sob teste (compilado) |
| `PLANO-DE-TESTES.md` | Classes de equivalência, valores-limite e o teste de cada um |
| `DEFEITOS.md` | Falhas encontradas |
| `USO-DE-IA.md` | Uso de IA no trabalho |
 
## Como rodar
 
Requisitos: JDK 17 ou superior e Maven (ou uma IDE com Maven embutido).
 
```
mvn test
```
 
Os resultados ficam em `target/surefire-reports`. O `pom.xml` não interrompe o build quando um teste falha, porque as falhas indicam possíveis defeitos do sistema.
 