package web.repository;

import org.springframework.stereotype.Component;
import web.Utils;
import web.exceptions.ErroDeBancoException;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class Transacao {

    public <T> T executar(Operacao<T> operacao) {
        try (Connection conn = Utils.getConnection()) {
            try {
                T resultado = operacao.executar(conn);
                conn.commit();
                return resultado;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new ErroDeBancoException("Falha ao acessar o banco de dados.", e);
        }
    }

    public <T> T ler(Operacao<T> operacao) {
        try (Connection conn = Utils.getConnection()) {
            return operacao.executar(conn);
        } catch (SQLException e) {
            throw new ErroDeBancoException("Falha ao acessar o banco de dados.", e);
        }
    }
}
