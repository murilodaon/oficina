package com.example.oficina.repository;

import com.example.oficina.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository
        extends JpaRepository<Fornecedor, Long> {

    boolean existsByCnpj(String cnpj);
}
