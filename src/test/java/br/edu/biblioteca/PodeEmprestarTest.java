package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PodeEmprestarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Limite de empréstimos ativos de cada tipo (aluno 3, professor 8, visitante 1).
    // Multa 0 e sem atraso, para que só o número de empréstimos decida.
    // Formato: tipo, emprestimosAtivos, esperado
    @ParameterizedTest(name = "{0}, {1} ativos -> {2}")
    @CsvSource({
        "ALUNO,0,true",
        "ALUNO,2,true",
        "ALUNO,3,false",
        "ALUNO,4,false",
        "PROFESSOR,0,true",
        "PROFESSOR,3,true",
        "PROFESSOR,7,true",
        "PROFESSOR,8,false",
        "PROFESSOR,9,false",
        "VISITANTE,0,true",
        "VISITANTE,1,false",
        "VISITANTE,2,false"
    })
    void limiteDeEmprestimosAtivos(TipoUsuario tipo, int ativos, boolean esperado) {
        assertEquals(esperado, regras.podeEmprestar(tipo, ativos, 0, false));
    }

    // Limite da multa pendente: no máximo R$ 10,00 (1000 centavos) é permitido.
    // Zero empréstimos ativos e sem atraso, para que só a multa decida.
    // Formato: tipo, multaPendenteCentavos, esperado
    @ParameterizedTest(name = "{0}, multa {1} -> {2}")
    @CsvSource({
        "ALUNO,0,true",
        "ALUNO,1000,true",
        "ALUNO,1001,false",
        "ALUNO,1002,false",
        "ALUNO,1202,false",
        "PROFESSOR,0,true",
        "PROFESSOR,1000,true",
        "PROFESSOR,1001,false",
        "PROFESSOR,1002,false",
        "PROFESSOR,1201,false",
        "VISITANTE,0,true",
        "VISITANTE,1000,true",
        "VISITANTE,1001,false",
        "VISITANTE,1002,false",
        "VISITANTE,1201,false"
    })
    void limiteDaMultaPendente(TipoUsuario tipo, int multa, boolean esperado) {
        assertEquals(esperado, regras.podeEmprestar(tipo, 0, multa, false));
    }

    // Tabela de decisão das três condições (aluno: limite 3).
    // Só a combinação "ativos abaixo do limite + multa até R$ 10 + sem atraso" dá true.
    // Formato: tipo, ativos, multa, possuiAtraso, esperado
    @ParameterizedTest(name = "{0}, ativos={1}, multa={2}, atraso={3} -> {4}")
    @CsvSource({
        "ALUNO,1,500,false,true",    // todas ok
        "ALUNO,3,500,false,false",   // só os ativos falham
        "ALUNO,1,1500,false,false",  // só a multa falha
        "ALUNO,1,500,true,false",    // só o atraso falha
        "ALUNO,3,1500,false,false",  // ativos + multa
        "ALUNO,3,500,true,false",    // ativos + atraso
        "ALUNO,1,1500,true,false",   // multa + atraso
        "ALUNO,3,1500,true,false"    // as três falham
    })
    void tabelaDeDecisao(TipoUsuario tipo, int ativos, int multa, boolean atraso, boolean esperado) {
        assertEquals(esperado, regras.podeEmprestar(tipo, ativos, multa, atraso));
    }

    // Classes inválidas: empréstimos ativos negativos ou multa pendente negativa
    // Formato: tipo, ativos, multa
    @ParameterizedTest(name = "{0}, ativos={1}, multa={2} -> exceção")
    @CsvSource({
        "ALUNO,-1,0",
        "PROFESSOR,-1,0",
        "VISITANTE,-1,0",
        "ALUNO,0,-1", 
        "PROFESSOR,0,-1",
        "VISITANTE,0,-1",
        "ALUNO,-1,-1"
    })
    void valoresNegativosLancamExcecao(TipoUsuario tipo, int ativos, int multa) {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeEmprestar(tipo, ativos, multa, false));
    }

    // Classe inválida: tipo nulo
    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeEmprestar(null, 0, 0, false));
    }
}
