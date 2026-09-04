package com.example.oficina.api.controller;

import com.example.oficina.api.dto.CategoriaServicoRequest;
import com.example.oficina.api.dto.CategoriaServicoResponse;
import com.example.oficina.api.mapper.CategoriaServicoMapper;
import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.service.CategoriaServicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorias-servico")
public class CategoriaServicoController {

    private final CategoriaServicoService service;
    private final CategoriaServicoMapper mapper;

    public CategoriaServicoController(
            CategoriaServicoService service,
            CategoriaServicoMapper mapper) {

        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CategoriaServicoResponse> cadastrar(
            @Valid @RequestBody CategoriaServicoRequest request) {

        CategoriaServico categoriaServico =
                mapper.toEntity(request);

        CategoriaServico cadastrada =
                service.cadastrar(categoriaServico.getNome());

        URI location = URI.create(
                "/api/categorias-servico/" + cadastrada.getId()
        );

        return ResponseEntity
                .created(location)
                .body(mapper.toResponse(cadastrada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaServicoResponse> buscarPorId(
            @PathVariable Long id) {

        CategoriaServico categoriaServico =
                service.buscarPorId(id);

        return ResponseEntity.ok(
                mapper.toResponse(categoriaServico)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoriaServicoResponse>> listar() {

        List<CategoriaServicoResponse> response =
                service.listar()
                        .stream()
                        .map(mapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
