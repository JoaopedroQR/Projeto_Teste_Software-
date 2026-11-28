package br.edu.biblioteca; // mesmo pacote do sistema: acessa RegrasBiblioteca sem import

import static org.junit.jupiter.api.Assertions.*; // assertEquals, assertThrows
import org.junit.jupiter.api.Test;  // teste simples (um caso)
import org.junit.jupiter.params.ParameterizedTest; // mesmo teste, vários dados
import org.junit.jupiter.params.provider.CsvSource; // dados: uma linha = uma execução

class MultaCentavosTest {

    // Objeto do sistema sob teste, criado como manda a especificação
    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Aluno e visitante: não têm carência. Cada linha: tipo, diasAtraso, multa esperada.
    // Os esperados vêm da ESPECIFICAÇÃO, nunca da saída do sistema.
    //Com @ParameterizedTest, você escreve o corpo uma vez e entrega uma tabela de dados. O JUnit executa o método uma vez por linha, e cada execução é reportada como um teste independente (passa ou falha sozinha).
    @ParameterizedTest(name = "{0}, {1} dias -> {2}")
    // @CsvSource: cada linha da tabela vira uma execução do método, com os valores convertidos para os tipos dos parâmetros
    @CsvSource({
        "ALUNO,0,0", // limite: sem atraso, sem multa
        "ALUNO,1,100", // limite: 1º dia cobrado
        "ALUNO,5,500", // limite: último dia da faixa de R$ 1
        "ALUNO,10,1000", // limite: último dia da faixa de R$ 1
        "ALUNO,11,1200", // limite: 1º dia da faixa de R$ 2  (pegou o defeito B)
        "ALUNO,12,1400", // exemplo do enunciado: 10x100 + 2x200
        "ALUNO,30,5000", // limite: multa chega a R$ 50 exatos
        "ALUNO,31,5000", // limite: teto passa a agir (pegou o defeito C)
        "VISITANTE,0,0",
        "VISITANTE,1,100",
        "VISITANTE,10,1000",
        "VISITANTE,11,1200",
        "VISITANTE,30,5000",
        "VISITANTE,31,5000"
    })
    void semCarencia(TipoUsuario tipo, int dias, int esperado) {
        // o JUnit converte "ALUNO" em TipoUsuario.ALUNO e "11" em int sozinho
        assertEquals(esperado, regras.multaCentavos(tipo, dias));
    }

    // Professor: 3 dias de carência (classes V3/V8, limites deslocados)
    @ParameterizedTest(name = "PROFESSOR, {0} dias -> {1}")
    @CsvSource({
        "0,0", // sem atraso
        "3,0", // último dia de carência (pegou o defeito D)
        "4,100", // 1º dia cobrado
        "5,200", // 1º dia cobrado
        "13,1000", // 10º dia cobrado
        "14,1200", // 11º dia cobrado (faixa de R$ 2)
        "33,5000", // 30º dia cobrado: R$ 50 exatos
        "34,5000"   // 31º dia cobrado: teto age
    })
    void professorComCarencia(int dias, int esperado) {
        // JUnit repete o método uma vez para cada linha do CsvSource, convertendo os valores para os tipos dos parâmetros
        assertEquals(esperado, regras.multaCentavos(TipoUsuario.PROFESSOR, dias));
        // a cada repetição, o método é chamado com os valores da linha atual
        //Retorna o resultado de regras.multaCentavos com os parâmetros TipoUsuario.PROFESSOR e dias.
    }

    // NOVO: distingue "teto ausente" de "teto aplicado tarde".
    // Com 100 dias, qualquer teto razoável teria de agir.
    @ParameterizedTest(name = "{0}, 100 dias -> 5000")
    @CsvSource({ 
        "ALUNO", 
        "PROFESSOR", 
        "VISITANTE" 
    })
    void tetoComAtrasoGrande(TipoUsuario tipo) {
        assertEquals(5000, regras.multaCentavos(tipo, 100));
    }


    // Classes inválidas I1 tipo nulo
    // @Test: um caso, valores fixos dentro do método
    @Test
    void tipoNuloLancaExcecao() {
        // assertThrows passa se a exceção do tipo certo for lançada, e falha se nada for lançado
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(null, 5));
    }

    // Classe inválida I2: dias negativos, para cada tipo (pegou o defeito A)
    @ParameterizedTest(name = "{0} com -1 dia lança exceção")
    @CsvSource({
        "ALUNO", 
        "PROFESSOR", 
        "VISITANTE" 
    })
    void diasNegativosLancamExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(tipo, -1));
    }
}

