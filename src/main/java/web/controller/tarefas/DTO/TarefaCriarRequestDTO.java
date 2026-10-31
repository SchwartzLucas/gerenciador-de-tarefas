package web.controller.tarefas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import web.domain.tarefas.Prioridade;

import java.time.LocalDate;

public record TarefaCriarRequestDTO(
        Long usuario_id,
        Long status_id,
        @NotBlank @Size(max = 200) String titulo,
        @NotBlank @Size(max = 2000) String descricao,
        Prioridade prioridade,
        LocalDate prazo,
        Integer esforco_estimado_min
) {
}
