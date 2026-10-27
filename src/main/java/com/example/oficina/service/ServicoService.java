package com.example.oficina.service;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.domain.Servico;
import com.example.oficina.domain.Status;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.CategoriaServicoRepository;
import com.example.oficina.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final CategoriaServicoRepository categoriaServicoRepository;
    private final FornecedorService fornecedorService;

    public ServicoService(
        ServicoRepository servicoRepository,
        CategoriaServicoRepository categoriaServicoRepository,
        FornecedorService fornecedorService) {

        this.servicoRepository = servicoRepository;
        this.categoriaServicoRepository = categoriaServicoRepository;
        this.fornecedorService = fornecedorService;
    }

    @Transactional
    public Servico cadastrar(Servico servico, Long categoriaServicoId) {

        if (servicoRepository.existsByCodigo(servico.getCodigo())) {
            throw new RecursoDuplicadoException(
                "Código do serviço já cadastrado"
            );
        }

        CategoriaServico categoria = categoriaServicoRepository
            .findById(categoriaServicoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                "Categoria de serviço não encontrada"
            ));

        servico.definirCategoria(categoria);

        return servicoRepository.save(servico);
    }

    @Transactional
    public Servico atualizar(
        Long id,
        Servico dadosAtualizados,
        Long categoriaServicoId,
        Long fornecedorId) {

        Servico servico = buscarPorId(id);

        boolean codigoEmUso =
            servicoRepository.existsByCodigoAndIdNot(
                dadosAtualizados.getCodigo(), id
            );

        if (codigoEmUso) {
            throw new RecursoDuplicadoException(
                "Código do serviço já cadastrado"
            );
        }

        CategoriaServico categoria = categoriaServicoRepository
            .findById(categoriaServicoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                "Categoria de serviço não encontrada"
            ));

        servico.alterarCodigo(dadosAtualizados.getCodigo());
        servico.alterarDescricao(dadosAtualizados.getDescricao());
        servico.alterarHorasEstimadas(dadosAtualizados.getHorasEstimadas());
        servico.alterarValorBase(dadosAtualizados.getValorBase());
        servico.alterarEstoqueMinimo(dadosAtualizados.getEstoqueMinimo());
        servico.alterarDataCadastro(dadosAtualizados.getDataCadastro());
        servico.definirCategoria(categoria);

        if (fornecedorId == null) {
            servico.definirFornecedor(null);
        } else {
            servico.definirFornecedor(
                fornecedorService.buscarPorId(fornecedorId)
            );
        }

        return servicoRepository.save(servico);
    }

    @Transactional
    public void excluir(Long id) {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }

    @Transactional(readOnly = true)
    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                "Serviço não encontrado"
            ));
    }

    @Transactional(readOnly = true)
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Servico> listar(Pageable pageable) {
        return servicoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPorCategoria(Long categoriaServicoId) {
        return servicoRepository.findByCategoriaServicoId(categoriaServicoId);
    }

    @Transactional(readOnly = true)
    public Page<Servico> listarPorCategoria(
        Long categoriaServicoId,
        Pageable pageable) {

        return servicoRepository.findByCategoriaServicoId(
            categoriaServicoId,
            pageable
        );
    }

    @Transactional(readOnly = true)
    public List<Servico> listarPorStatus(Status status) {
        return servicoRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public Page<Servico> listarPorStatus(
        Status status,
        Pageable pageable) {

        return servicoRepository.findByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Servico> listarPorCategoriaEStatus(
        Long categoriaServicoId,
        Status status,
        Pageable pageable) {

        return servicoRepository.findByCategoriaServicoIdAndStatus(
            categoriaServicoId,
            status,
            pageable
        );
    }
}
