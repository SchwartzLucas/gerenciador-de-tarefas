package web.controller.usuario;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.controller.usuario.DTO.UsuarioCriarRequestDTO;
import web.controller.usuario.DTO.UsuarioCriarResponseDTO;
import web.controller.usuario.DTO.UsuarioListarResponseDTO;
import web.domain.Usuario;
import web.service.Pesquisa;
import web.service.UsuarioService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

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
