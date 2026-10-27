package schwartz.domain.tarefas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class tarefas_etiquetas {
    private Long tarega_id;
    private Long etiqueta_id;
    private Long usuario_id;
}
