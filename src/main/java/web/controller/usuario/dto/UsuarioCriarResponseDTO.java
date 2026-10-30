package web.controller.usuario.dto;

import web.domain.Usuario;

public record UsuarioCriarResponseDTO(
    Long id,
    String nome,
    String email
) {
    public static UsuarioCriarResponseDTO from(Usuario u){
        return new UsuarioCriarResponseDTO(
                u.getId(),
                u.getNome(),
                u.getEmail()
        );
    }
}
