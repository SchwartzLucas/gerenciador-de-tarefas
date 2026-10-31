package web.repository.transacoes;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Operacao<T> { T executar(Connection conn) throws SQLException; }
