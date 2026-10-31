package web.controller.tarefas;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.controller.tarefas.DTO.TarefaCriarRequestDTO;
import web.controller.tarefas.DTO.TarefaCriarResponseDTO;
import web.domain.tarefas.Tarefa;
import web.service.TarefaService;

@AllArgsConstructor
@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService service;

    public ResponseEntity<TarefaCriarResponseDTO> criar(@Valid @RequestBody TarefaCriarRequestDTO request){
        Tarefa tarefa = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TarefaCriarResponseDTO.from(tarefa));
    }

}
