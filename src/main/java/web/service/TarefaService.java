package web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.controller.tarefas.DTO.TarefaCriarRequestDTO;
import web.domain.Usuario;
import web.domain.tarefas.Tarefa;
import web.repository.TarefaRepository;
import web.repository.transacoes.Transacao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class TarefaService {

    private final Transacao transacao;
    private final TarefaRepository repository;

    public Tarefa criar(TarefaCriarRequestDTO request){
        final String titulo = request.titulo().trim();
        final String descricao = request.descricao().trim();
        final LocalDate prazo = LocalDate.parse(request.prazo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        final Integer esforco_estimado_min = request.esforco_estimado_min();
        final Usuario usuario = new Usuario();

        return transacao.executar(conn ->
                repository.inserir(conn, new Tarefa(titulo, descricao, prazo, esforco_estimado_min), usuario));
    }

    public List<Tarefa> listar(Pesquisa pesquisa) {
        return transacao.ler(conn -> repository.carregarTarefas(conn, null, pesquisa));
    }
}
