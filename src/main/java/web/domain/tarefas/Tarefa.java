package web.domain.tarefas;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {
    private Long id;
    private Long usuario_id;
    private Long status_id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Date prazo;
    private Integer esforfo_estimado_min;
    private Integer posicao;
    private Byte ativa;
    private LocalDateTime inativada_em;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    public Tarefa(String titulo, String descricao, LocalDate prazo, Integer esforcoEstimadoMin) {
    }
}
