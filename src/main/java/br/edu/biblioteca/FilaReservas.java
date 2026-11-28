package br.edu.biblioteca;

import java.util.Optional;


public class FilaReservas {
    private int total = 0;
    private String primeiro;

    public void reservar(String usuario, TipoUsuario tipo){
        primeiro = usuario;
        total++;
    }
    public int tamanho(){
        return total;
    }
    public int posicao(String usuario){
        
    }
    public boolean cancelar(String usuario){
        if(total > 0){
            total--;
            return true;
        }
        return false;
    }
    public Optional<String> proximo(){
        if (total == 0) {
            return Optional.empty();
        }
        total--;
        return Optional.of(primeiro);
    }
}
