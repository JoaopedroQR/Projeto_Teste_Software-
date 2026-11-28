package br.edu.biblioteca; // mesmo pacote do sistema: acessa RegrasBiblioteca sem import

import static org.junit.jupiter.api.Assertions.*; // assertEquals, assertThrows
import org.junit.jupiter.api.Test;  // teste simples (um caso)

public class MultaTotalCentavos {
    
    private final RegrasBiblioteca regras = new RegrasBiblioteca();
    @Test
    void caminho1_listaVazia() {
        assertEquals(0, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[]{}));
    }

    @Test
    void caminho2_listaNulaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.multaTotalCentavos(TipoUsuario.ALUNO, null));
    }

    @Test
    void caminho3_somaDosEmprestimos() {
        assertEquals(1900, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[]{5, 12}));
    }

    @Test
    void caminho4_totalLimitadoAoTeto() {
        assertEquals(10000, regras.multaTotalCentavos(TipoUsuario.ALUNO, new int[]{25, 25, 25, 25}));
    }
}
