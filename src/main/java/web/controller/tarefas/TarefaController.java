package web.controller.tarefas;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.controller.tarefas.DTO.TarefaCriarRequestDTO;
import web.controller.tarefas.DTO.TarefaCriarResponseDTO;
import web.controller.tarefas.DTO.TarefaListarResponseDTO;
import web.domain.tarefas.Tarefa;
import web.service.Pesquisa;
import web.service.TarefaService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService service;

    @PostMapping("/criar")
    public ResponseEntity<TarefaCriarResponseDTO> criar(@Valid @RequestBody TarefaCriarRequestDTO request){
        Tarefa tarefa = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TarefaCriarResponseDTO.from(tarefa));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TarefaListarResponseDTO>> listar(Pesquisa pesquisa){
        List<Tarefa> tarefas = service.listar(pesquisa);
        return ResponseEntity.status(HttpStatus.OK).body(TarefaListarResponseDTO.from(tarefas));
    }

}
