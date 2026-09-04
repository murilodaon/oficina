package com.example.oficina.api.mapper;

import com.example.oficina.api.dto.ServicoRequest;
import com.example.oficina.api.dto.ServicoResponse;
import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.domain.Fornecedor;
import com.example.oficina.domain.Servico;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper {

    public Servico toEntity(
            ServicoRequest request) {

        return new Servico(
                request.codigo(),
                request.descricao(),
                request.horasEstimadas(),
                request.valorBase(),
                request.estoqueMinimo(),
                request.dataCadastro()
        );
    }

    public ServicoResponse toResponse(
            Servico servico) {

        CategoriaServico categoriaServico =
                servico.getCategoriaServico();

        Fornecedor fornecedor =
                servico.getFornecedor();

        return new ServicoResponse(
                servico.getId(),
                servico.getCodigo(),
                servico.getDescricao(),
                servico.getHorasEstimadas(),
                servico.getValorBase(),
                servico.getEstoqueMinimo(),
                servico.getDataCadastro(),
                servico.getStatus(),
                categoriaServico.getId(),
                categoriaServico.getNome(),
                fornecedor != null
                        ? fornecedor.getId()
                        : null,
                fornecedor != null
                        ? fornecedor.getRazaoSocial()
                        : null
        );
    }
}