package com.example.oficina.api.mapper;

import com.example.oficina.api.dto.CategoriaServicoRequest;
import com.example.oficina.api.dto.CategoriaServicoResponse;
import com.example.oficina.domain.CategoriaServico;
import org.springframework.stereotype.Component;

@Component
public class CategoriaServicoMapper {

    public CategoriaServico toEntity(
            CategoriaServicoRequest request) {

        return new CategoriaServico(
                request.nome()
        );
    }

    public CategoriaServicoResponse toResponse(
            CategoriaServico categoriaServico) {

        return new CategoriaServicoResponse(
                categoriaServico.getId(),
                categoriaServico.getNome(),
                categoriaServico.getStatus()
        );
    }
}