package web.repository;

import org.springframework.stereotype.Repository;
import web.Utils;
import web.domain.Usuario;
import web.domain.tarefas.Prioridade;
import web.domain.tarefas.Tarefa;
import web.service.Filtro;
import web.service.HistoricoHelper;
import web.service.Pesquisa;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class TarefaRepository {

    private HistoricoHelper helper;

    public Tarefa inserir(Connection conn, Tarefa t, Usuario usuario) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
                insert into tarefas(usuario_id, status_id, titulo, descricao, prioridade, prazo, esforco_estimado_min, 
                criado_em)
                values(?,?,?,?,?,?,?,?)
                """)) {
            ps.setLong(1, t.getUsuario_id());
            ps.setLong(2, t.getStatus_id());
            ps.setString(3, t.getTitulo());
            ps.setString(4, t.getDescricao());
            ps.setString(5, String.valueOf(t.getPrioridade()));
            ps.setDate(6, (Date) t.getPrazo());
            ps.setInt(7, t.getEsforco_estimado_min());
            ps.setObject(8, LocalDateTime.now(ZoneOffset.UTC));
            boolean tarefa_criada = false;
            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    t.setId(key.getLong("id"));
                    t.setAtiva(key.getByte("ativa"));
                    if (key.getObject("criado_em") instanceof LocalDateTime criado) {
                        t.setCriado_em(criado);
                    }
                    if (key.getObject("atualiazado_em") instanceof LocalDateTime atualizado) {
                        t.setAtualizado_em(atualizado);
                    }
                    tarefa_criada = true;
                }
            }
            if (tarefa_criada) {
                helper.atualizarHistoricoTarefas(conn, t, null, true, usuario);
            }
            return t;
        }
    }

    public List<Tarefa> carregarTarefas(Connection conn, Long id, Pesquisa pesquisa) throws SQLException {
        StringBuilder buf = new StringBuilder(5000);
        buf.append("""
                select count(*) as quantidade, t.ativa, t.atualizado_em, t.criado_em, t.descricao, t.esforco_estimado_min, t.inativada_em, t.prazo,
                t.prioridade, t.status_id, t.titulo, t.usuario_id
                from tarefas t where 1=1
                """);
        if (!Utils.isEmpty(id)) {
            buf.append(" and t.id = ?");
        }
        if(pesquisa != null){
            if(!Utils.isEmpty(pesquisa.filtros()) && !pesquisa.filtros().isEmpty()){
                for(Filtro f: pesquisa.filtros()){
                    switch (f.propriedade()){
                        case "ativa" -> {
                            if(f.valor() instanceof List<?> v){
                                if(!v.isEmpty()){
                                    if(v.size() != 2){
                                        buf.append(" and t.ativa = ? ");
                                    }
                                }
                            }
                        }
                        case "usuario" -> {
                            if(f.valor() instanceof List<?> v){
                                buf.append(" and usuario_id in ( ").append(String.join(", ", Collections.nCopies(v.size(), " ?, "))).append(" )");
                            }
                        }
                    }
                }
            }
        }
        try (PreparedStatement ps = conn.prepareStatement(buf.toString())) {
            int i = 0;
            if (!Utils.isEmpty(id)) {
                ps.setLong(++i, id);
            }
            if(pesquisa != null){
                if(!Utils.isEmpty(pesquisa.filtros()) && !pesquisa.filtros().isEmpty()){
                    for(Filtro f: pesquisa.filtros()){
                        switch (f.propriedade()){
                            case "ativa" -> {
                                if(f.valor() instanceof List<?> v){
                                    if(!v.isEmpty()){
                                        if(v.size() != 2 && v.getFirst() instanceof int vf){
                                            ps.setInt(++i, vf);
                                        }
                                    }
                                }
                            }
                            case "usuario" -> {
                                if(f.valor() instanceof List<?> v){
                                    for(Object valor : v){
                                        if(valor instanceof Long vl){
                                            ps.setLong(++i, vl);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Tarefa> tarefas = new ArrayList<>();
                while (rs.next()) {
                    Tarefa t = new Tarefa();
                    t.setAtiva(rs.getByte("ativa"));
                    if (rs.getObject("atualizada_em") instanceof LocalDateTime l) {
                        t.setAtualizado_em(l);
                    }
                    if (rs.getObject("criado_em") instanceof LocalDateTime l) {
                        t.setCriado_em(l);
                    }
                    if (rs.getObject("inativada_em") instanceof LocalDateTime l) {
                        t.setInativada_em(l);
                    }
                    if (rs.getObject("prazo") instanceof Date l) {
                        t.setPrazo(l);
                    }
                    t.setDescricao(rs.getString("descricao"));
                    t.setEsforco_estimado_min(rs.getInt("esforco_estimato_min"));
                    if (rs.getObject("prioridade") instanceof Prioridade p) {
                        t.setPrioridade(p);
                    }
                    t.setStatus_id(rs.getLong("status_id"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setUsuario_id(rs.getLong("usuario_id"));
                    tarefas.add(t);
                }
                return tarefas;
            }
        }
    }
}
