package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DataDevolucaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // A data de devolução (retirada + prazo) cai em dia que NÃO é domingo: sem ajuste.
    // Cobre sábado (dia normal), segunda (dia logo após o domingo), dias comuns e virada de mês.
    // Formato do CSV: tipo, retirada, devolução esperada
    @ParameterizedTest(name = "{0}, retirada {1} -> {2}")
    @CsvSource({
        "ALUNO,2026-11-21,2026-12-05",      // devolução num sábado
        "ALUNO,2026-11-23,2026-12-07",      // devolução numa segunda
        "ALUNO,2026-11-20,2026-12-04",      // devolução numa sexta
        "ALUNO,2026-11-24,2026-12-08",      // devolução numa terça (virada de mês)
        "PROFESSOR,2026-11-19,2026-12-19",  // devolução num sábado
        "PROFESSOR,2026-11-21,2026-12-21",  // devolução numa segunda
        "PROFESSOR,2026-11-24,2026-12-24",  // devolução numa quinta
        "VISITANTE,2026-11-21,2026-11-28",  // devolução num sábado
        "VISITANTE,2026-11-23,2026-11-30"   // devolução numa segunda
    })
    void devolucaoSemAjuste(TipoUsuario tipo, LocalDate retirada, LocalDate esperada) {
        assertEquals(esperada, regras.dataDevolucao(retirada, tipo));
    }

    // A data de devolução cai num domingo: passa para a segunda-feira seguinte.
    @ParameterizedTest(name = "{0}, retirada {1} -> {2}")
    @CsvSource({
        "ALUNO,2026-11-22,2026-12-07",      // domingo 06/12 -> segunda 07/12
        "PROFESSOR,2026-11-20,2026-12-21",  // domingo 20/12 -> segunda 21/12
        "VISITANTE,2026-11-22,2026-11-30",  // domingo 29/11 -> segunda 30/11
        "VISITANTE,2026-12-27,2027-01-04",  // domingo 03/01 -> segunda 04/01 (virada de ano)
        "VISITANTE,2027-01-24,2027-02-01"   // domingo 31/01 -> segunda 01/02 (virada de mês)
    })
    void devolucaoQueCaiNoDomingoVaiParaSegunda(TipoUsuario tipo, LocalDate retirada, LocalDate esperada) {
        assertEquals(esperada, regras.dataDevolucao(retirada, tipo));
    }

    // Classe inválida: retirada nula
    @Test
    void retiradaNulaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.dataDevolucao(null, TipoUsuario.ALUNO));
    }

    // Classe inválida: tipo nulo
    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> regras.dataDevolucao(LocalDate.of(2026, 11, 24), null));
    }
}
