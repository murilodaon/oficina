package com.example.oficina.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorRequest(

        @NotBlank(message = "Razão social é obrigatória")
        @Size(
                max = 150,
                message = "Razão social deve possuir no máximo 150 caracteres"
        )
        String razaoSocial,

        @NotBlank(message = "CNPJ é obrigatório")
        @Size(
                max = 14,
                message = "CNPJ deve possuir no máximo 14 caracteres"
        )
        String cnpj
) {
}
