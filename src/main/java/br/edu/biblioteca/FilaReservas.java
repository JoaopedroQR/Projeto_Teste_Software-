package br.edu.biblioteca;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FilaReservas {
    private final List<String> professores = new ArrayList<>();
    private final List<String> alunos = new ArrayList<>();

    public void reservar(String usuario, TipoUsuario tipo){
        if(tipo == TipoUsuario.PROFESSOR){
            professores.add(usuario);
        }else if(tipo == TipoUsuario.ALUNO){
            alunos.add(usuario);
        }else if(tipo == TipoUsuario.VISITANTE){
            throw new IllegalStateException("Visitante não pode reservar!");
        }
    }
    public int tamanho(){
        return professores.size() + alunos.size();
    }
    public int posicao(String usuario){
        
    }
    public boolean cancelar(String usuario){
        
    }
    public Optional<String> proximo(){
        if (!professores.isEmpty()) {
            return Optional.of(professores.remove(0));
        }
        if (!alunos.isEmpty()) {
            return Optional.of(alunos.remove(0));
        }
        return Optional.empty();
    }
}
