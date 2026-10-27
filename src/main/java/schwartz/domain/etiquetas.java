package schwartz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class etiquetas {
    private Long id;
    private Long usuario_id;
    private String nome;
    private String cor;
    private LocalDateTime criado_em;
}
