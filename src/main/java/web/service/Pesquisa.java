package web.service;

import java.util.List;

public record Pesquisa(
        Long inicio,
        Long limite,
        List<Filtro> filtro
) {
}
