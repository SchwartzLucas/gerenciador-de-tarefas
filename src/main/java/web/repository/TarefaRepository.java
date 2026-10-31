package web.repository;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import web.domain.tarefas.Prioridade;
import web.domain.tarefas.Tarefa;
import web.service.HistoricoHelper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
public class TarefaRepository {

    private HistoricoHelper helper;

    public void inserir(Connection conn, Tarefa t) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("""
        insert into tarefas(usuario_id, status_id, titulo, descricao, prioridade, prazo, esforco_estimado_min, 
        criado_em)
        values(?,?,?,?,?,?,?,?)
                            """)){
            ps.setLong(1, t.getUsuario_id());
            ps.setLong(2, t.getStatus_id());
            ps.setString(3, t.getTitulo());
            ps.setString(4, t.getDescricao());
            ps.setString(5, String.valueOf(t.getPrioridade()));
            ps.setDate(6, (Date) t.getPrazo());
            ps.setInt(7, t.getEsforfo_estimado_min());
            ps.setObject(8, LocalDateTime.now(ZoneOffset.UTC));
            boolean tarefa_criada = false;
            try(ResultSet key = ps.getGeneratedKeys()){
                if(key.next()){
                    t.setId(key.getLong("id"));
                    t.setAtiva(key.getByte("ativa"));
                    if (key.getObject("criado_em") instanceof LocalDateTime criado) {
                        t.setCriado_em(criado);
                    }
                    if(key.getObject("atualiazado_em") instanceof  LocalDateTime atualizado){
                        t.setAtualizado_em(atualizado);
                    }
                    tarefa_criada = true;
                }
            }
            if(tarefa_criada){
                helper.atualizarHistoricoTarefas(conn, t, true);
            }
        }
    }

    public Tarefa listarPorId(Connection conn, Long id) throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("""
        select t.ativa, t.atualizado_em, t.criado_em, t.descricao, t.esforco_estimado_min, t.inativada_em, t.prazo,
        t.prioridade, t.status_id, t.titulo, t.usuario_id
        from tarefas t where t.id = ?
        """)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Tarefa t = new Tarefa();
                    t.setAtiva(rs.getByte("ativa"));
                    if(rs.getObject("atualizada_em") instanceof LocalDateTime l){
                        t.setAtualizado_em(l);
                    }
                    if(rs.getObject("criado_em") instanceof LocalDateTime l){
                        t.setCriado_em(l);
                    }
                    if(rs.getObject("inativada_em") instanceof  LocalDateTime l){
                        t.setInativada_em(l);
                    }
                    if(rs.getObject("prazo") instanceof Date l){
                        t.setPrazo(l);
                    }
                    t.setDescricao(rs.getString("descricao"));
                    t.setEsforfo_estimado_min(rs.getInt("esforco_estimato_min"));
                    if(rs.getObject("prioridade") instanceof Prioridade p){
                        t.setPrioridade(p);
                    }
                    t.setStatus_id(rs.getLong("status_id"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setUsuario_id(rs.getLong("usuario_id"));
                    return t;
                }
            }
        }
        return null;
    }
}
