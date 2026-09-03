package com.example.oficina.service;

import com.example.oficina.domain.Fornecedor;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(
            FornecedorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Fornecedor cadastrar(
            String razaoSocial,
            String cnpj) {

        if (repository.existsByCnpj(cnpj)) {
            throw new RecursoDuplicadoException(
                    "CNPJ do fornecedor já cadastrado"
            );
        }

        return repository.save(
                new Fornecedor(razaoSocial, cnpj)
        );
    }

    @Transactional(readOnly = true)
    public Fornecedor buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado"
                ));
    }

    @Transactional(readOnly = true)
    public List<Fornecedor> listar() {
        return repository.findAll();
    }
}