package web.domain.tarefas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class tarefas {
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
}
