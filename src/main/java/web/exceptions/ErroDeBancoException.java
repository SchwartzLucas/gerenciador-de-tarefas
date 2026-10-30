package web.exceptions;

import java.sql.SQLException;

public class ErroDeBancoException extends RuntimeException {
    public ErroDeBancoException(String msg, SQLException e){ super(msg, e);}
}
