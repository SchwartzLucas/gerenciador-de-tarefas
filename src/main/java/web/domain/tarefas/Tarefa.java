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
    public final static Byte ATIVA = 1;
    public final static Byte INATIVA = 0;
    private Long id;
    private Long usuario_id;
    private Long status_id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Date prazo;
    private Integer esforco_estimado_min;
    private Integer posicao;
    private Byte ativa;
    private LocalDateTime inativada_em;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    public Tarefa(String titulo, String descricao, LocalDate prazo, Integer esforcoEstimadoMin) {
    }
}
