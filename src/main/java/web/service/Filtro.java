package web.service;

public record Filtro(
        String propriedade,
        String operador,
        Object valor
) {
}
