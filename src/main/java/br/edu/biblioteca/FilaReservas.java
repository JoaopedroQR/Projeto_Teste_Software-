package br.edu.biblioteca;

import java.util.Optional;


public class FilaReservas {
    private int total = 0;

    public void reservar(String usuario, TipoUsuario tipo){
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
        return Optional.empty();
    }
}
