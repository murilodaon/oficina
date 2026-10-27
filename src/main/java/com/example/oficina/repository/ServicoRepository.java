package com.example.oficina.repository;

import com.example.oficina.domain.Servico;
import com.example.oficina.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends
    JpaRepository<Servico, Long>,
    JpaSpecificationExecutor<Servico> {

    Optional<Servico> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    List<Servico> findByCategoriaServicoId(Long categoriaServicoId);

    List<Servico> findByStatus(Status status);

    Page<Servico> findByCategoriaServicoId(
        Long categoriaServicoId,
        Pageable pageable
    );

    Page<Servico> findByStatus(
        Status status,
        Pageable pageable
    );

    Page<Servico> findByCategoriaServicoIdAndStatus(
        Long categoriaServicoId,
        Status status,
        Pageable pageable
    );
}
