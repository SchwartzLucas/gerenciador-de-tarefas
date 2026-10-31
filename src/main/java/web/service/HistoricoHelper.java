package web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import web.Utils;
import web.domain.Usuario;
import web.domain.tarefas.Tarefa;
import web.repository.TarefaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class HistoricoHelper {

    TarefaRepository tarefaRepository;

    public void atualizarHistoricoTarefas(Connection conn, Tarefa tarefa, Tarefa tarefa_antiga, boolean tarefa_criada, Usuario usuario) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("""
        insert into historico_tarefas(tarefa_id, usuario_id, campo, valor_antigo, valor_novo, criado_em)
        values(?,?,?,?,?);
    """)){
            if(tarefa_criada){
                ps.setLong(1, tarefa.getId());
                ps.setLong(2, usuario.getId());
                ps.setString(2, "Tarefa Criada");
                ps.setString(4, null);
                ps.setString(5, String.valueOf(tarefa.getId()));
                ps.executeUpdate();
                return;
            }
            if(tarefa != null && tarefa_antiga != null){

            }
        }
    }
}
