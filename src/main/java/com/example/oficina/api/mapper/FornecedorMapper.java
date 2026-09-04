package com.example.oficina.api.mapper;

import com.example.oficina.api.dto.FornecedorRequest;
import com.example.oficina.api.dto.FornecedorResponse;
import com.example.oficina.domain.Fornecedor;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper {

    public Fornecedor toEntity(
            FornecedorRequest request) {

        return new Fornecedor(
                request.razaoSocial(),
                request.cnpj()
        );
    }

    public FornecedorResponse toResponse(
            Fornecedor fornecedor) {

        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getStatus()
        );
    }
}