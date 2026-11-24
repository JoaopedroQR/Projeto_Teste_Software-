package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LimiteEmprestimosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Classes válidas: um representante por tipo de usuário
    @ParameterizedTest(name = "{0} -> limite {1}")
    @CsvSource({
        "ALUNO,3",
        "PROFESSOR,8",
        "VISITANTE,1"
    })
    void limitePorTipo(TipoUsuario tipo, int esperado) {
        assertEquals(esperado, regras.limiteEmprestimos(tipo));
    }

    // Classe inválida: tipo nulo
    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.limiteEmprestimos(null));
    }
}
