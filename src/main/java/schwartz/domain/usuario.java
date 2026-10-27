package schwartz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha_hash;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;
}
