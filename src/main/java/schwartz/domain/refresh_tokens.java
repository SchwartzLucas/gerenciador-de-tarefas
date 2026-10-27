package schwartz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class refresh_tokens {

    private Long id;
    private Long usuario_id;
    private String token_hash;
    private LocalDateTime expira_em;
    private LocalDateTime revogado_em;
    private LocalDateTime criado_em;
}
