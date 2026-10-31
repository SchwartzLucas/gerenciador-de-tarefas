package web.controller.usuario.DTO;

import web.domain.Usuario;

public record UsuarioCriarResponseDTO(
    Long id,
    String nome,
    String email,
    String hash
) {
    public static UsuarioCriarResponseDTO from(Usuario u){
        return new UsuarioCriarResponseDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getSenha_hash()
        );
    }
}
