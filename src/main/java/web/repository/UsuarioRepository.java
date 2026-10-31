package web.repository;

import org.springframework.stereotype.Repository;
import web.Utils;
import web.domain.Usuario;
import web.exceptions.ConflitoException;
import web.service.Filtro;
import web.service.Pesquisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    public boolean existePorEmail(Connection conn, String email) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("select 1 from usuarios u where u.email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Usuario inserir(Connection con, Usuario u) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("insert into usuarios(nome, email, senha_hash) values(?,?,?)")) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha_hash());
            ps.executeUpdate();
            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    u.setId(key.getLong("id"));
                    if (key.getObject("criado_em") instanceof LocalDateTime t) {
                        u.setCriado_em(t);
                    }
                    return u;
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {// registro duplicado (email)
                throw new ConflitoException("Já existe uma conta com este e-mail");
            }
            throw e;
        }
        return null;
    }

    public List<Usuario> listar(Connection conn, Pesquisa pesquisa) throws SQLException {
        StringBuilder buf = new StringBuilder(5000);
        buf.append("""
                select u.id, u.nome, u.email, u.criado_em, u.atualizado_em
                from usuarios u
                where 1=1
                """);
        if (!Utils.isEmpty(pesquisa.filtros())) {
            for (Filtro filtro : pesquisa.filtros()) {
                switch (filtro.propriedade()) {
                    case "like" -> buf.append(" and u.nome like ? ");
                    case "email" -> buf.append(" and u.email = ? ");
                    case "data_criacao" -> {
                        if (filtro.valor() instanceof List<?> v && !Utils.isEmpty(filtro.operador())) {
                            if (!Utils.isEmpty(v)) {
                                switch (filtro.operador()) {
                                    case "eq" -> buf.append(" and u.criado_em = ? ");
                                    case "gt" -> buf.append(" and u.criado_em > ? ");
                                    case "lt" -> buf.append(" and u.criado_em < ? ");
                                    case "bt" -> buf.append("and u.criado_em > ? and u.criado_em < ? ");
                                }
                            }
                        }
                    }
                    case "data_atualizacao" -> {
                        if (filtro.valor() instanceof List<?> v && !Utils.isEmpty(filtro.operador())) {
                            if (!Utils.isEmpty(v)) {
                                switch (filtro.operador()) {
                                    case "eq" -> buf.append(" and u.atualizado_em = ? ");
                                    case "gt" -> buf.append(" and u.atualizado_em > ? ");
                                    case "lt" -> buf.append(" and u.atualizado_em < ? ");
                                    case "bt" -> buf.append("and u.atualizado_em > ? and u.atualizado_em < ? ");
                                }
                            }
                        }
                    }
                }
            }
        }
        try (PreparedStatement ps = conn.prepareStatement(buf.toString())) {
            if (!Utils.isEmpty(pesquisa.filtros())) {
                int i = 1;
                for (Filtro filtro : pesquisa.filtros()) {
                    switch (filtro.propriedade()) {
                        case "like" -> {
                            if (filtro.valor() instanceof String v) {
                                ps.setString(i++, v);
                            }
                        }
                        case "email" -> {
                            if (filtro.valor() instanceof String v) {
                                ps.setString(i++, v);
                            }
                        }
                        case "data_criacao" -> {
                            if (filtro.valor() instanceof List<?> v && !Utils.isEmpty(filtro.operador())) {
                                switch (filtro.operador()) {
                                    case "eq", "gt", "lt" -> {
                                        ps.setObject(i++, v.getFirst());
                                    }
                                    case "bt" -> {
                                        if (v.size() != 2) {
                                            throw new IllegalArgumentException("É necessário utilizar 2 datas para procurar entre datas");
                                        }
                                        List<?> ordenado = v.stream().sorted().toList();
                                        ps.setObject(i++, ordenado.get(0));
                                        ps.setObject(i++, ordenado.get(1));
                                    }
                                }
                            }
                        }
                        case "data_atualizacao" -> {
                            if (filtro.valor() instanceof List<?> v && !Utils.isEmpty(filtro.operador())) {
                                switch (filtro.operador()) {
                                    case "eq", "gt", "lt" -> {
                                        ps.setObject(i++, v.getFirst());
                                    }
                                    case "bt" -> {
                                        if (v.size() != 2) {
                                            throw new IllegalArgumentException("É necessário utilizar 2 datas para procurar entre datas");
                                        }
                                        List<?> ordenado = v.stream().sorted().toList();
                                        ps.setObject(i++, ordenado.get(0));
                                        ps.setObject(i++, ordenado.get(1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Usuario> usuarios = new ArrayList<>(rs.getRow());
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    if (rs.getObject("criado_em") instanceof LocalDateTime t) {
                        u.setCriado_em(t);
                    }
                    if (rs.getObject("atualizado_em") instanceof LocalDateTime t) {
                        u.setAtualizado_em(t);
                    }
                    usuarios.add(u);
                }
                return usuarios;
            }
        }
    }
}
