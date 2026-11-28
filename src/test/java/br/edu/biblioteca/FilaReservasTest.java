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
}
