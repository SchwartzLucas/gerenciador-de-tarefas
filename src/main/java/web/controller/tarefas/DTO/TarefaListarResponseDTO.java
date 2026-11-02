package web.controller.tarefas.DTO;

import web.domain.tarefas.Tarefa;

import java.util.List;

public record TarefaListarResponseDTO(
        Long id
) {
    public static List<TarefaListarResponseDTO> from(List<Tarefa> tarefas){
        return tarefas.stream().map(t -> new TarefaListarResponseDTO(
                t.getId()
        )).toList();
    }
}
