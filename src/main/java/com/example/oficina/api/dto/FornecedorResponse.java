package com.example.oficina.api.dto;

import com.example.oficina.domain.Status;

public record FornecedorResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        Status status
) {
}