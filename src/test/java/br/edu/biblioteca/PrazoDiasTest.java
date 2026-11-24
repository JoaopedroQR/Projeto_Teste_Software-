package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PrazoDiasTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Classes válidas: um representante por tipo de usuário
    @ParameterizedTest(name = "{0} -> {1} dias")
    @CsvSource({ 
        "ALUNO,14",
        "PROFESSOR,30",
        "VISITANTE,7"
    })
    void prazoPorTipo(TipoUsuario tipo, int esperado) {
        assertEquals(esperado, regras.prazoDias(tipo));
    }

    // Classe inválida: tipo nulo
    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.prazoDias(null));
    }
}
