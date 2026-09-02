package com.example.oficina.service;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.CategoriaServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServicoService {

    private final CategoriaServicoRepository repository;

    public CategoriaServicoService(
            CategoriaServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CategoriaServico cadastrar(String nome) {

        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new RecursoDuplicadoException(
                    "Nome da categoria já cadastrado");
        }

        return repository.save(new CategoriaServico(nome));
    }

    @Transactional(readOnly = true)
    public CategoriaServico buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria de serviço não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<CategoriaServico> listar() {
        return repository.findAll();
    }
}
