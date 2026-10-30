package web.controller.usuario;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.controller.usuario.dto.UsuarioCriarRequestDTO;
import web.controller.usuario.dto.UsuarioCriarResponseDTO;
import web.controller.usuario.dto.UsuarioListarResponseDTO;
import web.domain.Usuario;
import web.repository.Transacao;
import web.service.Pesquisa;
import web.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService usuarioService){
        this.service = usuarioService;
    }


    @PostMapping("/criar")
    public ResponseEntity<UsuarioCriarResponseDTO> criar (@Valid @RequestBody UsuarioCriarRequestDTO request){
        Usuario usuario = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioCriarResponseDTO.from(usuario));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioListarResponseDTO>> listar (Pesquisa pesquisa){
        List<Usuario> usuarioList = service.listar(pesquisa);

        return ResponseEntity.status(HttpStatus.OK).body(UsuarioListarResponseDTO.from(usuarioList));
    }
}
