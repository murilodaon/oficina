package com.example.oficina.repository;

import com.example.oficina.domain.CategoriaServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaServicoRepository
        extends JpaRepository<CategoriaServico, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<CategoriaServico> findByNomeIgnoreCase(String nome);
}