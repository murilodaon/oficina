package com.example.oficina.service;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.domain.Servico;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.CategoriaServicoRepository;
import com.example.oficina.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final CategoriaServicoRepository categoriaServicoRepository;

    public ServicoService(
            ServicoRepository servicoRepository,
            CategoriaServicoRepository categoriaServicoRepository) {
        this.servicoRepository = servicoRepository;
        this.categoriaServicoRepository = categoriaServicoRepository;
    }

    @Transactional
    public Servico cadastrar(Servico servico, Long categoriaServicoId) {

        if (servicoRepository.existsByCodigo(servico.getCodigo())) {
            throw new RecursoDuplicadoException(
                    "Código do serviço já cadastrado");
        }

        CategoriaServico categoria = categoriaServicoRepository
                .findById(categoriaServicoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria de serviço não encontrada"));

        servico.definirCategoria(categoria);

        return servicoRepository.save(servico);
    }

    @Transactional(readOnly = true)
    public Servico buscarPorId(Long id) {

        return servicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Serviço não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPorCategoria(Long categoriaServicoId) {
        return servicoRepository.findByCategoriaServicoId(
                categoriaServicoId);
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPorStatus(
            com.example.oficina.domain.Status status) {

        return servicoRepository.findByStatus(status);
    }
}