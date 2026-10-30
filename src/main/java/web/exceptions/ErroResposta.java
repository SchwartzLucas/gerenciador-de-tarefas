package web.exceptions;

import java.util.List;

public record ErroResposta(String codigo, String mensagem, List<CampoInvalido> campos) {
    public record CampoInvalido(String campo, String mensagem) {}
}
