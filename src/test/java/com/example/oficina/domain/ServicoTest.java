package com.example.oficina.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTest {

    @Test
    void deveCriarServicoComEstoqueMinimoZero() {

        Servico servico = new Servico(
                "SERV-001",
                "Troca de óleo",
                new BigDecimal("1.5"),
                new BigDecimal("100.00"),
                LocalDate.of(2026, 9, 2)
        );

        assertEquals(
                BigDecimal.ZERO,
                servico.getEstoqueMinimo()
        );

        assertEquals(
                Status.ATIVO,
                servico.getStatus()
        );

        assertNull(servico.getFornecedor());
    }

    @Test
    void deveCriarServicoComEstoqueMinimoInformado() {

        Servico servico = new Servico(
                "SERV-002",
                "Alinhamento",
                new BigDecimal("2.0"),
                new BigDecimal("150.00"),
                new BigDecimal("5.000"),
                LocalDate.of(2026, 9, 2)
        );

        assertEquals(
                new BigDecimal("5.000"),
                servico.getEstoqueMinimo()
        );
    }

    @Test
    void naoDeveAceitarEstoqueMinimoNegativo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Servico(
                        "SERV-003",
                        "Balanceamento",
                        new BigDecimal("1.0"),
                        new BigDecimal("80.00"),
                        new BigDecimal("-1.000"),
                        LocalDate.of(2026, 9, 2)
                )
        );
    }

    @Test
    void naoDeveAceitarEstoqueMinimoNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Servico(
                        "SERV-004",
                        "Revisão",
                        new BigDecimal("3.0"),
                        new BigDecimal("300.00"),
                        null,
                        LocalDate.of(2026, 9, 2)
                )
        );
    }

    @Test
    void deveAlterarEstoqueMinimo() {

        Servico servico = new Servico(
                "SERV-005",
                "Troca de filtro",
                new BigDecimal("1.0"),
                new BigDecimal("50.00"),
                LocalDate.of(2026, 9, 2)
        );

        servico.alterarEstoqueMinimo(
                new BigDecimal("10.000")
        );

        assertEquals(
                new BigDecimal("10.000"),
                servico.getEstoqueMinimo()
        );
    }

    @Test
    void naoDeveAlterarEstoqueMinimoParaValorNegativo() {

        Servico servico = new Servico(
                "SERV-006",
                "Troca de pastilhas",
                new BigDecimal("2.0"),
                new BigDecimal("200.00"),
                LocalDate.of(2026, 9, 2)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> servico.alterarEstoqueMinimo(
                        new BigDecimal("-5.000")
                )
        );

        assertEquals(
                BigDecimal.ZERO,
                servico.getEstoqueMinimo()
        );
    }

    @Test
    void deveDefinirFornecedor() {

        Servico servico = new Servico(
                "SERV-007",
                "Diagnóstico",
                new BigDecimal("1.0"),
                new BigDecimal("120.00"),
                LocalDate.of(2026, 9, 2)
        );

        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Teste LTDA",
                "12345678000199"
        );

        servico.definirFornecedor(fornecedor);

        assertSame(
                fornecedor,
                servico.getFornecedor()
        );
    }

    @Test
    void devePermitirRemoverFornecedor() {

        Servico servico = new Servico(
                "SERV-008",
                "Inspeção",
                new BigDecimal("1.0"),
                new BigDecimal("90.00"),
                LocalDate.of(2026, 9, 2)
        );

        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Teste LTDA",
                "12345678000199"
        );

        servico.definirFornecedor(fornecedor);
        servico.definirFornecedor(null);

        assertNull(servico.getFornecedor());
    }
}