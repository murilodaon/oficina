package com.example.oficina.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ServicoRequest(

        @NotBlank(message = "Código é obrigatório")
        @Size(
                max = 100,
                message = "Código deve possuir no máximo 100 caracteres"
        )
        String codigo,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(
                max = 255,
                message = "Descrição deve possuir no máximo 255 caracteres"
        )
        String descricao,

        @NotNull(message = "Horas estimadas são obrigatórias")
        @PositiveOrZero(message = "Horas estimadas não podem ser negativas")
        BigDecimal horasEstimadas,

        @NotNull(message = "Valor base é obrigatório")
        @PositiveOrZero(message = "Valor base não pode ser negativo")
        BigDecimal valorBase,

        @NotNull(message = "Estoque mínimo é obrigatório")
        @PositiveOrZero(message = "Estoque mínimo não pode ser negativo")
        BigDecimal estoqueMinimo,

        @NotNull(message = "Data de cadastro é obrigatória")
        LocalDate dataCadastro,

        @NotNull(message = "Categoria do serviço é obrigatória")
        @Positive(message = "Identificador da categoria deve ser positivo")
        Long categoriaServicoId,

        @Positive(message = "Identificador do fornecedor deve ser positivo")
        Long fornecedorId
) {
}