
package com.example.oficina.repository;

import com.example.oficina.domain.Servico;
import com.example.oficina.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Optional<Servico> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    List<Servico> findByCategoriaServicoId(Long categoriaServicoId);

    List<Servico> findByStatus(Status status);
}
