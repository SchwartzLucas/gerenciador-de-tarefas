package web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class status {
    private Long id;
    private String codigo;
    private String nome;
    private Integer ordem;
    private Byte concluido;
}
