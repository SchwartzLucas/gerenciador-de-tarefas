package web.controller.tarefas.DTO;

import web.domain.tarefas.Tarefa;

public record TarefaCriarResponseDTO(
        Long id,
        Long usuario_id

) {
    public static TarefaCriarResponseDTO from(Tarefa t) {
        return new TarefaCriarResponseDTO(
                t.getId(),
                t.getUsuario_id()
        );
    }
}
