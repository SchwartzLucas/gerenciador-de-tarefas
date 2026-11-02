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
        try (PreparedStatement ps = conn.prepareStatement("""
                    insert into historico_tarefas(tarefa_id, usuario_id, campo, valor_antigo, valor_novo)
                    values(?,?,?,?,?);
                """)) {
            if (tarefa_criada) {
                adicionarHistorico(ps, tarefa, usuario, "Tarefa criada", null, String.valueOf(tarefa.getId()));
                ps.executeUpdate();
                return;
            }
            if (tarefa != null && tarefa_antiga != null) {
                if (tarefa.getPrazo().compareTo(tarefa_antiga.getPrazo()) != 0) {
                    adicionarHistorico(ps, tarefa, usuario, "Prazo alterado", String.valueOf(tarefa_antiga.getPrazo()), String.valueOf(tarefa.getPrazo()));
                }
                if (!Utils.isEmpty(tarefa.getUsuario_id()) && !Utils.isEmpty(tarefa_antiga.getUsuario_id())) {
                    if (!tarefa_antiga.getUsuario_id().equals(tarefa.getUsuario_id())) {
                        adicionarHistorico(ps, tarefa, usuario, "Usuário alterado", String.valueOf(tarefa_antiga.getUsuario_id()), String.valueOf(tarefa.getUsuario_id()));
                    }
                } else if (Utils.isEmpty(tarefa.getUsuario_id()) && !Utils.isEmpty(tarefa_antiga.getUsuario_id())) {
                    adicionarHistorico(ps, tarefa, usuario, "Usuário adicionado", String.valueOf(tarefa_antiga.getUsuario_id()), String.valueOf(tarefa.getUsuario_id()));
                } else if (!Utils.isEmpty(tarefa.getUsuario_id()) && Utils.isEmpty(tarefa_antiga.getUsuario_id())) {
                    adicionarHistorico(ps, tarefa, usuario, "Usuário removido", String.valueOf(tarefa_antiga.getUsuario_id()), String.valueOf(tarefa.getUsuario_id()));
                }
            }
            if(tarefa.getAtiva().equals(Tarefa.ATIVA) && tarefa_antiga.getAtiva().equals(Tarefa.INATIVA)){
                adicionarHistorico(ps, tarefa, usuario, "Tarefa ativada novamente", String.valueOf(tarefa_antiga.getUsuario_id()), String.valueOf(tarefa.getUsuario_id()));

            } else if (tarefa.getAtiva().equals(Tarefa.INATIVA) && tarefa_antiga.getAtiva().equals(Tarefa.ATIVA)) {
                adicionarHistorico(ps, tarefa, usuario, "Tarefa inativada", String.valueOf(tarefa_antiga.getUsuario_id()), String.valueOf(tarefa.getUsuario_id()));
            }
            if(!tarefa.getPrioridade().name().equals(tarefa_antiga.getPrioridade().name())){
                adicionarHistorico(ps, tarefa, usuario, "Prioridade alterada", tarefa_antiga.getPrioridade().name(), tarefa.getPrioridade().name());
            }
            if(!tarefa.getStatus_id().equals(tarefa_antiga.getStatus_id())){
                adicionarHistorico(ps, tarefa, usuario, "Status alterado", String.valueOf(tarefa_antiga.getStatus_id()), String.valueOf(tarefa.getStatus_id()));
            }
            if(tarefa.getEsforco_estimado_min().compareTo(tarefa_antiga.getEsforco_estimado_min()) != 0){
                adicionarHistorico(ps, tarefa, usuario, "Tempo estimado alterado", String.valueOf(tarefa_antiga.getEsforco_estimado_min()), String.valueOf(tarefa.getEsforco_estimado_min()));
            }
            if(tarefa.getTitulo().equalsIgnoreCase(tarefa_antiga.getTitulo())){
                adicionarHistorico(ps, tarefa, usuario, "Titulo modificado", tarefa_antiga.getTitulo(), tarefa.getTitulo());
            }
        }
    }

    public <T> void adicionarHistorico(PreparedStatement ps, T classe, Usuario usuario, String campo, String valor_antigo, String valor_novo) throws SQLException {
        if (classe instanceof Tarefa t) {
            ps.setLong(1, t.getId());
            ps.setLong(2, usuario.getId());
            ps.setString(2, campo);
            ps.setString(4, valor_antigo);
            ps.setString(5, valor_novo);
        }
        ps.executeUpdate();
    }
}
