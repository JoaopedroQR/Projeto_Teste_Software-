# Hipóteses dos erros

## Classe PrazoDias:

- Visitante não possui prazo de 7 dias para devolver, e sim de 10.

## Classe dataDevolução:

- Quando VISITANTES sempre atrasa 3 dias

- Professor não está verificando o domingo

## Classse multaCentavos:

- Quando diasAtraso = -1 nenhuma exceção é chamada para VISITANTE, ALUNO E PROFESSOR [Falta de verificação if no código]

- ALUNO e VISITANTE, quando diasAtraso = 11, o resultado se iguala a 1100, porém nos dias posteriores se resolve. Logo é um erro pontual de igualdade no código

- Quando passa dos 30 dias de dias atrasados não há o limite de 5000 para ALUNOS VISITANTES E PROFESSOR [Falta de verificação if no código]

- PROFESSOR paga 100 no dia 3 de atraso, No if está <3 não <=3

## podeEmprestar

- ALUNO, ativos = 3 é voltado True, No if está <3 não <=3