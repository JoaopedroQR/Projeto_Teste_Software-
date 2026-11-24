package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class PodeRenovarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Limite de renovações: aluno até 2, professor até 3, visitante nunca.
    // Sem reserva e sem atraso, para que só o número de renovações decida.
    // Formato: tipo, renovacoesFeitas, esperado
    @ParameterizedTest(name = "{0}, {1} renovações -> {2}")
    @CsvSource({
        "ALUNO,0,true",
        "ALUNO,1,true",
        "ALUNO,2,false",
        "ALUNO,3,false",
        "PROFESSOR,0,true",
        "PROFESSOR,2,true",
        "PROFESSOR,3,false",
        "PROFESSOR,4,false",
        "VISITANTE,0,false",
        "VISITANTE,1,false",
        "VISITANTE,3,false"
    })
    void limiteDeRenovacoesPorTipo(TipoUsuario tipo, int renovacoes, boolean esperado) {
        assertEquals(esperado, regras.podeRenovar(tipo, renovacoes, false, false));
    }

    // Reserva e atraso impedem a renovação, mesmo abaixo do limite (0 renovações).
    // Formato: tipo, possuiReserva, emAtraso, esperado
    @ParameterizedTest(name = "{0}, reserva={1}, atraso={2} -> {3}")
    @CsvSource({
        "ALUNO,false,false,true",
        "ALUNO,true,false,false",
        "ALUNO,false,true,false",
        "ALUNO,true,true,false",
        "PROFESSOR,false,false,true",
        "PROFESSOR,true,false,false",
        "PROFESSOR,false,true,false",
        "PROFESSOR,true,true,false",
        "VISITANTE,false,false,false",
        "VISITANTE,true,true,false"
    })
    void reservaOuAtrasoImpedemRenovacao(TipoUsuario tipo, boolean reserva, boolean atraso, boolean esperado) {
        assertEquals(esperado, regras.podeRenovar(tipo, 0, reserva, atraso));
    }

    // Classe inválida: renovações negativas lançam exceção para QUALQUER tipo (inclusive visitante)
    @ParameterizedTest
    @EnumSource(TipoUsuario.class)
    void renovacoesNegativasLancamExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeRenovar(tipo, -1, false, false));
    }

    // Classe inválida: tipo nulo
    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.podeRenovar(null, 0, false, false));
    }
}
