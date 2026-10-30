package web.exceptions;

// exception/ (todas estendem RuntimeException)
public class ConflitoException extends RuntimeException {
    public ConflitoException(String msg) { super(msg); }
}
