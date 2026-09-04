package com.example.oficina.api.dto;

import com.example.oficina.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ServicoResponse(
        Long id,
        String codigo,
        String descricao,
        BigDecimal horasEstimadas,
        BigDecimal valorBase,
        BigDecimal estoqueMinimo,
        LocalDate dataCadastro,
        Status status,
        Long categoriaServicoId,
        String categoriaServicoNome,
        Long fornecedorId,
        String fornecedorRazaoSocial
) {
}