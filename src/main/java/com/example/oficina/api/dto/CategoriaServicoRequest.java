package com.example.oficina.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaServicoRequest(

        @NotBlank(message = "Nome da categoria é obrigatório")
        @Size(
                max = 100,
                message = "Nome da categoria deve possuir no máximo 100 caracteres"
        )
        String nome
) {
}