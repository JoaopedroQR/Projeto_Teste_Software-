package br.edu.biblioteca;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilaReservas {
    private final List<String> professores = new ArrayList<>();
    private final List<String> alunos = new ArrayList<>();
    private static final int CAPACIDADE = 5;

    private boolean estaNaFila(String usuario) {
        return alunos.contains(usuario) || professores.contains(usuario);
    }

    public void reservar(String usuario, TipoUsuario tipo){
        if (tipo == null) {
            throw new IllegalArgumentException("tipo obrigatório");
        }else if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("usuário obrigatório");
        }else if(estaNaFila(usuario)){
            throw new IllegalStateException("usuário já está na fila");
        }else if(tamanho() >= CAPACIDADE){
            throw new IllegalStateException("Fila cheia");
        }else if(tipo == TipoUsuario.PROFESSOR){
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
        if (usuario == null || usuario.isBlank()) {
           throw new IllegalArgumentException("usuário obrigatório");
        }
        int i = professores.indexOf(usuario);
        if (i >= 0) {
            return i + 1;
        }
        i = alunos.indexOf(usuario);
        if (i >= 0) {
            return professores.size() + i + 1;
        }
        return 0;
    }
    public boolean cancelar(String usuario){
        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("usuário obrigatório");
        } 
        return alunos.remove(usuario) || professores.remove(usuario);
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
