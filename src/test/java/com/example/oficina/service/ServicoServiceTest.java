package com.example.oficina.service;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.domain.Servico;
import com.example.oficina.domain.Status;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.CategoriaServicoRepository;
import com.example.oficina.repository.ServicoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ServicoServiceTest {

    @Autowired
    private ServicoService service;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private CategoriaServicoRepository categoriaServicoRepository;

    @Test
    void deveCadastrarServico() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Motor")
        );

        Servico servico = new Servico(
            "SRV-001",
            "Troca de óleo",
            new BigDecimal("2.00"),
            new BigDecimal("150.00"),
            LocalDate.now()
        );

        Servico cadastrado = service.cadastrar(servico, categoria.getId());

        assertNotNull(cadastrado.getId());
        assertEquals("SRV-001", cadastrado.getCodigo());
        assertEquals("Troca de óleo", cadastrado.getDescricao());
        assertEquals(
            categoria.getId(),
            cadastrado.getCategoriaServico().getId()
        );
    }

    @Test
    void naoDeveCadastrarServicoComCodigoDuplicado() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Motor")
        );

        Servico primeiro = new Servico(
            "SRV-001",
            "Troca de óleo",
            new BigDecimal("2.00"),
            new BigDecimal("150.00"),
            LocalDate.now()
        );

        service.cadastrar(primeiro, categoria.getId());

        Servico segundo = new Servico(
            "SRV-001",
            "Alinhamento",
            new BigDecimal("1.00"),
            new BigDecimal("100.00"),
            LocalDate.now()
        );

        assertThrows(
            RecursoDuplicadoException.class,
            () -> service.cadastrar(segundo, categoria.getId())
        );
    }

    @Test
    void naoDeveCadastrarServicoComCategoriaInexistente() {
        Servico servico = new Servico(
            "SRV-002",
            "Troca de pastilhas",
            new BigDecimal("2.00"),
            new BigDecimal("250.00"),
            LocalDate.now()
        );

        assertThrows(
            RecursoNaoEncontradoException.class,
            () -> service.cadastrar(servico, 999999L)
        );
    }

    @Test
    void deveLancarRollbackQuandoCategoriaNaoExiste() {
        Servico servico = new Servico(
            "SRV-ROLLBACK",
            "Serviço para testar rollback",
            new BigDecimal("2.00"),
            new BigDecimal("200.00"),
            LocalDate.now()
        );

        assertThrows(
            RecursoNaoEncontradoException.class,
            () -> service.cadastrar(servico, 999999L)
        );

        assertFalse(
            servicoRepository.existsByCodigo("SRV-ROLLBACK")
        );
    }

    @Test
    void deveBuscarServicoPorId() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Freios")
        );

        Servico servico = new Servico(
            "SRV-003",
            "Troca de pastilhas",
            new BigDecimal("2.00"),
            new BigDecimal("250.00"),
            LocalDate.now()
        );

        Servico cadastrado = service.cadastrar(servico, categoria.getId());
        Servico encontrado = service.buscarPorId(cadastrado.getId());

        assertEquals(cadastrado.getId(), encontrado.getId());
        assertEquals("SRV-003", encontrado.getCodigo());
    }

    @Test
    void deveLancarExcecaoAoBuscarServicoInexistente() {
        assertThrows(
            RecursoNaoEncontradoException.class,
            () -> service.buscarPorId(999999L)
        );
    }

    @Test
    void deveListarServicos() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Motor")
        );

        service.cadastrar(
            new Servico(
                "SRV-004",
                "Troca de óleo",
                new BigDecimal("2.00"),
                new BigDecimal("150.00"),
                LocalDate.now()
            ),
            categoria.getId()
        );

        service.cadastrar(
            new Servico(
                "SRV-005",
                "Revisão do motor",
                new BigDecimal("4.00"),
                new BigDecimal("500.00"),
                LocalDate.now()
            ),
            categoria.getId()
        );

        var servicos = service.listar();

        assertEquals(2, servicos.size());
    }

    @Test
    void deveListarServicosPorCategoria() {
        CategoriaServico motor = categoriaServicoRepository.save(
            new CategoriaServico("Motor")
        );

        CategoriaServico freios = categoriaServicoRepository.save(
            new CategoriaServico("Freios")
        );

        service.cadastrar(
            new Servico(
                "SRV-006",
                "Troca de óleo",
                new BigDecimal("2.00"),
                new BigDecimal("150.00"),
                LocalDate.now()
            ),
            motor.getId()
        );

        service.cadastrar(
            new Servico(
                "SRV-007",
                "Troca de pastilhas",
                new BigDecimal("2.00"),
                new BigDecimal("250.00"),
                LocalDate.now()
            ),
            freios.getId()
        );

        var servicos = service.listarPorCategoria(motor.getId());

        assertEquals(1, servicos.size());
        assertEquals("SRV-006", servicos.get(0).getCodigo());
    }

    @Test
    void deveListarServicosPorStatus() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Motor")
        );

        service.cadastrar(
            new Servico(
                "SRV-008",
                "Troca de óleo",
                new BigDecimal("2.00"),
                new BigDecimal("150.00"),
                LocalDate.now()
            ),
            categoria.getId()
        );

        var servicos = service.listarPorStatus(Status.ATIVO);

        assertEquals(1, servicos.size());
        assertEquals(Status.ATIVO, servicos.get(0).getStatus());
    }

    @Test
    void deveSepararServicosAtivosEInativos() {
        CategoriaServico categoria = categoriaServicoRepository.save(
            new CategoriaServico("Suspensão")
        );

        Servico ativo = service.cadastrar(
            new Servico(
                "SRV-009",
                "Troca de amortecedor",
                new BigDecimal("2.00"),
                new BigDecimal("350.00"),
                LocalDate.now()
            ),
            categoria.getId()
        );

        Servico inativo = service.cadastrar(
            new Servico(
                "SRV-010",
                "Revisão da suspensão",
                new BigDecimal("3.00"),
                new BigDecimal("400.00"),
                LocalDate.now()
            ),
            categoria.getId()
        );

        inativo.inativar();

        var ativos = service.listarPorStatus(Status.ATIVO);
        var inativos = service.listarPorStatus(Status.INATIVO);

        assertTrue(
            ativos.stream()
                .anyMatch(s -> s.getId().equals(ativo.getId()))
        );

        assertFalse(
            ativos.stream()
                .anyMatch(s -> s.getId().equals(inativo.getId()))
        );

        assertTrue(
            inativos.stream()
                .anyMatch(s -> s.getId().equals(inativo.getId()))
        );

        assertFalse(
            inativos.stream()
                .anyMatch(s -> s.getId().equals(ativo.getId()))
        );
    }
}
