package com.example.oficina.api.controller;

import com.example.oficina.api.dto.ServicoRequest;
import com.example.oficina.api.dto.ServicoResponse;
import com.example.oficina.api.mapper.ServicoMapper;
import com.example.oficina.domain.Fornecedor;
import com.example.oficina.domain.Servico;
import com.example.oficina.domain.Status;
import com.example.oficina.service.FornecedorService;
import com.example.oficina.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;
    private final FornecedorService fornecedorService;
    private final ServicoMapper mapper;

    public ServicoController(
        ServicoService servicoService,
        FornecedorService fornecedorService,
        ServicoMapper mapper) {

        this.servicoService = servicoService;
        this.fornecedorService = fornecedorService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ServicoResponse> cadastrar(
        @Valid @RequestBody ServicoRequest request) {

        Servico servico = mapper.toEntity(request);

        if (request.fornecedorId() != null) {
            Fornecedor fornecedor =
                fornecedorService.buscarPorId(request.fornecedorId());

            servico.definirFornecedor(fornecedor);
        }

        Servico cadastrado = servicoService.cadastrar(
            servico,
            request.categoriaServicoId()
        );

        URI location = URI.create(
            "/api/servicos/" + cadastrado.getId()
        );

        return ResponseEntity
            .created(location)
            .body(mapper.toResponse(cadastrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody ServicoRequest request) {

        Servico dadosAtualizados = mapper.toEntity(request);

        Servico atualizado = servicoService.atualizar(
            id,
            dadosAtualizados,
            request.categoriaServicoId(),
            request.fornecedorId()
        );

        return ResponseEntity.ok(
            mapper.toResponse(atualizado)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(
        @PathVariable Long id) {

        Servico servico = servicoService.buscarPorId(id);

        return ResponseEntity.ok(
            mapper.toResponse(servico)
        );
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponse>> listar() {

        List<ServicoResponse> response =
            servicoService.listar()
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ServicoResponse>> listarPaginado(
        @RequestParam(required = false) Long categoriaServicoId,
        @RequestParam(required = false) Status status,
        @PageableDefault(size = 10, sort = "codigo")
        Pageable pageable) {

        Page<Servico> servicos;

        if (categoriaServicoId != null && status != null) {
            servicos = servicoService.listarPorCategoriaEStatus(
                categoriaServicoId,
                status,
                pageable
            );
        } else if (categoriaServicoId != null) {
            servicos = servicoService.listarPorCategoria(
                categoriaServicoId,
                pageable
            );
        } else if (status != null) {
            servicos = servicoService.listarPorStatus(
                status,
                pageable
            );
        } else {
            servicos = servicoService.listar(pageable);
        }

        Page<ServicoResponse> response =
            servicos.map(mapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        servicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
