package web.controller.usuario.dto;


import web.domain.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioListarResponseDTO(
        Long id,
        String name,
        String email,
        LocalDateTime criado_em,
        LocalDateTime atualizado_em
) {
    public static List<UsuarioListarResponseDTO> from(List<Usuario> u){
        return u.stream().map(r -> new UsuarioListarResponseDTO(
                r.getId(),
                r.getNome(),
                r.getEmail(),
                r.getCriado_em(),
                r.getAtualizado_em()
        )).toList();
    }
}
