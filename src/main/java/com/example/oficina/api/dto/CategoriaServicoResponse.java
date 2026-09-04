package com.example.oficina.api.dto;

import com.example.oficina.domain.Status;

public record CategoriaServicoResponse(
        Long id,
        String nome,
        Status status
) {
}