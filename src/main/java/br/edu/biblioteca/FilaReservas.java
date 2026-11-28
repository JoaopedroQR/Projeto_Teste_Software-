package br.edu.biblioteca;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FilaReservas {
    private final List<String> reservas = new ArrayList<>();

    public void reservar(String usuario, TipoUsuario tipo){
        reservas.add(usuario);
    }
    public int tamanho(){
        return reservas.size();
    }
    public int posicao(String usuario){
        
    }
    public boolean cancelar(String usuario){
        
    }
    public Optional<String> proximo(){
        if (reservas.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(reservas.remove(0));
    }
}
