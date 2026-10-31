package web.service;

import java.util.List;

public record Pesquisa(
        Long inicio,
        Long limite,
        List<Filtro> filtros,
        List<Ordenacao> ordenacoes
) {
}
