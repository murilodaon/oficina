package com.example.oficina.api.controller;

import com.example.oficina.api.dto.FornecedorRequest;
import com.example.oficina.api.dto.FornecedorResponse;
import com.example.oficina.api.mapper.FornecedorMapper;
import com.example.oficina.domain.Fornecedor;
import com.example.oficina.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService service;
    private final FornecedorMapper mapper;

    public FornecedorController(
            FornecedorService service,
            FornecedorMapper mapper) {

        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> cadastrar(
            @Valid @RequestBody FornecedorRequest request) {

        Fornecedor fornecedor =
                mapper.toEntity(request);

        Fornecedor cadastrado =
                service.cadastrar(
                        fornecedor.getRazaoSocial(),
                        fornecedor.getCnpj()
                );

        URI location = URI.create(
                "/api/fornecedores/" + cadastrado.getId()
        );

        return ResponseEntity
                .created(location)
                .body(mapper.toResponse(cadastrado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarPorId(
            @PathVariable Long id) {

        Fornecedor fornecedor =
                service.buscarPorId(id);

        return ResponseEntity.ok(
                mapper.toResponse(fornecedor)
        );
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> listar() {

        List<FornecedorResponse> response =
                service.listar()
                        .stream()
                        .map(mapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}