
package com.example.oficina.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTest {

    private Servico criarServico() {
        return new Servico(
            "SERV-001",
            "Troca de óleo",
            new BigDecimal("1.5"),
            new BigDecimal("100.00"),
            LocalDate.of(2026, 9, 2)
        );
    }

    @Test
    void deveCriarServicoComEstoqueMinimoZero() {
        Servico servico = criarServico();

        assertEquals(BigDecimal.ZERO, servico.getEstoqueMinimo());
        assertEquals(Status.ATIVO, servico.getStatus());
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

        assertEquals(new BigDecimal("5.000"), servico.getEstoqueMinimo());
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
        Servico servico = criarServico();

        servico.alterarEstoqueMinimo(new BigDecimal("10.000"));

        assertEquals(new BigDecimal("10.000"), servico.getEstoqueMinimo());
    }

    @Test
    void naoDeveAlterarEstoqueMinimoParaValorNegativo() {
        Servico servico = criarServico();

        assertThrows(
            IllegalArgumentException.class,
            () -> servico.alterarEstoqueMinimo(new BigDecimal("-5.000"))
        );

        assertEquals(BigDecimal.ZERO, servico.getEstoqueMinimo());
    }

    @Test
    void deveAlterarDescricao() {
        Servico servico = criarServico();

        servico.alterarDescricao("Troca de óleo e filtro");

        assertEquals("Troca de óleo e filtro", servico.getDescricao());
    }

    @Test
    void naoDeveAlterarDescricaoParaVazia() {
        Servico servico = criarServico();

        assertThrows(
            IllegalArgumentException.class,
            () -> servico.alterarDescricao(" ")
        );

        assertEquals("Troca de óleo", servico.getDescricao());
    }

    @Test
    void deveAlterarHorasEstimadas() {
        Servico servico = criarServico();

        servico.alterarHorasEstimadas(new BigDecimal("2.5"));

        assertEquals(new BigDecimal("2.5"), servico.getHorasEstimadas());
    }

    @Test
    void naoDeveAlterarHorasEstimadasParaValorNegativo() {
        Servico servico = criarServico();

        assertThrows(
            IllegalArgumentException.class,
            () -> servico.alterarHorasEstimadas(new BigDecimal("-1"))
        );

        assertEquals(new BigDecimal("1.5"), servico.getHorasEstimadas());
    }

    @Test
    void deveAlterarValorBase() {
        Servico servico = criarServico();

        servico.alterarValorBase(new BigDecimal("180.00"));

        assertEquals(new BigDecimal("180.00"), servico.getValorBase());
    }

    @Test
    void naoDeveAlterarValorBaseParaValorNegativo() {
        Servico servico = criarServico();

        assertThrows(
            IllegalArgumentException.class,
            () -> servico.alterarValorBase(new BigDecimal("-10.00"))
        );

        assertEquals(new BigDecimal("100.00"), servico.getValorBase());
    }

    @Test
    void deveDefinirFornecedor() {
        Servico servico = criarServico();

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Teste LTDA",
            "12345678000199"
        );

        servico.definirFornecedor(fornecedor);

        assertSame(fornecedor, servico.getFornecedor());
    }

    @Test
    void devePermitirRemoverFornecedor() {
        Servico servico = criarServico();

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Teste LTDA",
            "12345678000199"
        );

        servico.definirFornecedor(fornecedor);
        servico.definirFornecedor(null);

        assertNull(servico.getFornecedor());
    }

    @Test
    void deveAtivarEInativarServico() {
        Servico servico = criarServico();

        servico.inativar();
        assertEquals(Status.INATIVO, servico.getStatus());

        servico.ativar();
        assertEquals(Status.ATIVO, servico.getStatus());
    }
}
