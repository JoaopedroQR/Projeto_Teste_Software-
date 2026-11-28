package br.edu.biblioteca;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Regras de empréstimo da biblioteca. Consulte a especificação do projeto
 * (Anexo A do enunciado) para o comportamento esperado de cada método.
 */
public class RegrasBiblioteca {

    public static final int TOLERANCIA_MULTA_CENTAVOS = 1000;
    public static final int TETO_MULTA_CENTAVOS = 5000;
    public static final int TETO_MULTA_TOTAL_CENTAVOS = 10000;
    public static final int CARENCIA_PROFESSOR_DIAS = 3;

    public int prazoDias(TipoUsuario tipo) {
        exigirTipo(tipo);
        switch (tipo) {
            case ALUNO:
                return 14;
            case PROFESSOR:
                return 30;
            default:
                return 10;
        }
    }

    public LocalDate dataDevolucao(LocalDate retirada, TipoUsuario tipo) {
        if (retirada == null) {
            throw new IllegalArgumentException("data de retirada obrigatória");
        }
        LocalDate devolucao = retirada.plusDays(prazoDias(tipo));
        if (retirada.getDayOfWeek() == DayOfWeek.SUNDAY) {
            devolucao = devolucao.plusDays(1);
        }
        return devolucao;
    }

    public int limiteEmprestimos(TipoUsuario tipo) {
        exigirTipo(tipo);
        switch (tipo) {
            case ALUNO:
                return 3;
            case PROFESSOR:
                return 8;
            default:
                return 1;
        }
    }

    public int multaCentavos(TipoUsuario tipo, int diasAtraso) {
        exigirTipo(tipo);
        int dias = diasAtraso;
        if (tipo == TipoUsuario.PROFESSOR) {
            dias = Math.max(0, diasAtraso - 2);
        }
        int multa;
        if (dias <= 11) {
            multa = dias * 100;
        } else {
            multa = 1000 + (dias - 10) * 200;
        }
        return multa;
    }

    public boolean podeEmprestar(TipoUsuario tipo, int emprestimosAtivos,
                                 int multaPendenteCentavos, boolean possuiAtraso) {
        exigirTipo(tipo);
        if (emprestimosAtivos < 0) {
            throw new IllegalArgumentException("valores negativos");
        }
        return emprestimosAtivos <= limiteEmprestimos(tipo)
                && (multaPendenteCentavos < TOLERANCIA_MULTA_CENTAVOS
                || !possuiAtraso);
    }

    public boolean podeRenovar(TipoUsuario tipo, int renovacoesFeitas,
                               boolean possuiReserva, boolean emAtraso) {
        exigirTipo(tipo);
        if (renovacoesFeitas < 0) {
            throw new IllegalArgumentException("renovações negativas");
        }
        int maximo = (tipo == TipoUsuario.PROFESSOR) ? 4 : 2;
        return renovacoesFeitas < maximo && !possuiReserva && !emAtraso;
    }

    public int multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo) {
        exigirTipo(tipo);
        if (diasAtrasoPorEmprestimo == null) {
            throw new IllegalArgumentException("lista de atrasos obrigatória");
        }
        int total = 0;
        for (int i = 1; i < diasAtrasoPorEmprestimo.length; i++) {
            total += multaCentavos(tipo, diasAtrasoPorEmprestimo[i]);
        }
        if (total > TETO_MULTA_TOTAL_CENTAVOS) {
            total = TETO_MULTA_CENTAVOS;
        }
        return total;
    }

    private void exigirTipo(TipoUsuario tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("tipo de usuário obrigatório");
        }
    }
}
