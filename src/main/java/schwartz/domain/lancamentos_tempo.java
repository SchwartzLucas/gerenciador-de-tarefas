package schwartz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class lancamentos_tempo {
    private Long id;
    private Long tarefa_id;
    private Long usuario_id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Integer duracao_seg;
    private String anotacao;
    private LocalDateTime criado_em;
    private Long usuarui_em_andamento;
}
