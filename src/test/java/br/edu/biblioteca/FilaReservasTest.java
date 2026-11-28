package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;


public class FilaReservasTest {
    private final FilaReservas fila = new FilaReservas();

    @Test
    public void filaNovaTemTamanhoZero() {
        assertEquals(0, fila.tamanho());
    }

    @Test
    public void filaNovaNaoTemProximo() {
        assertEquals(Optional.empty(), fila.proximo());
    }
    @Test
    public void reservarAumentaOTamanho() {
        fila.reservar("ana", TipoUsuario.ALUNO);
        assertEquals(1, fila.tamanho());
    }
    @Test
    public void cancelarReservaDeUsuario(){
        fila.cancelar("ana");
        assertEquals(0, fila.tamanho());
    }
    @Test
    void proximoDevolveOReservadoERemove() {
        fila.reservar("ana", TipoUsuario.ALUNO);
        assertEquals(Optional.of("ana"), fila.proximo());
        assertEquals(0, fila.tamanho());
    }
    @Test
    void alunosSaemPorOrdemDeChegada() {
        fila.reservar("ana", TipoUsuario.ALUNO);
        fila.reservar("bia", TipoUsuario.ALUNO);
        assertEquals(Optional.of("ana"), fila.proximo());
        assertEquals(Optional.of("bia"), fila.proximo());
        assertEquals(Optional.empty(), fila.proximo());
    }
}
