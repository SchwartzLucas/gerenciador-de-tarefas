package schwartz.domain.tarefas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class historico_tarefas {
    private Long id;
    private Long tarefa_id;
    private Long usuario_id;
    private String campo;
    private String valor_antigo;
    private String valor_novo;
    private LocalDateTime criado_em;
}
