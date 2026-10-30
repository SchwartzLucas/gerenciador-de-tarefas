package web.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha_hash;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    public Usuario(String nome, String email, String senha_hash){
        this.nome = nome;
        this.email = email;
        this.senha_hash = senha_hash;
    }

}
