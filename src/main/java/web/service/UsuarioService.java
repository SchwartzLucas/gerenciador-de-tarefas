package web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.controller.usuario.dto.UsuarioCriarRequestDTO;
import web.domain.Usuario;
import web.Utils;
import web.exceptions.ConflitoException;
import web.repository.Transacao;
import web.repository.usuario.UsuarioRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final Transacao transacao;
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;



    public Usuario criar(UsuarioCriarRequestDTO request) {
        final String nome = request.nome().trim();
        final String email = request.email().trim();
        final String hash = enconder.encode(request.senha());

        return transacao.executar(conn -> {
            if(repository.existePorEmail(conn, email)){
                throw new ConflitoException("Já existe uma conta com este email registrado");
            }
            return repository.inserir(conn, new Usuario(nome, email, hash));
        });
    }

    public List<Usuario> listar(Pesquisa pesquisa) {
        return transacao.ler(conn -> repository.listar(conn, pesquisa));
    }
}
