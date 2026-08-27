package com.example.oficina.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicoTest {

    @Test
    void deveCriarServicoComStatusAtivo() {

        Servico servico = new Servico(
                "SERV-001",
                "Troca de óleo",
                new BigDecimal("1.5"),
                new BigDecimal("150.00"),
                LocalDate.now()
        );

        assertEquals(
                "SERV-001",
                servico.getCodigo()
        );

        assertEquals(
                "Troca de óleo",
                servico.getDescricao()
        );

        assertEquals(
                new BigDecimal("1.5"),
                servico.getHorasEstimadas()
        );

        assertEquals(
                new BigDecimal("150.00"),
                servico.getValorBase()
        );

        assertEquals(
                Status.ATIVO,
                servico.getStatus()
        );
    }

    @Test
    void naoDeveCriarServicoComCodigoVazio() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Servico(
                        " ",
                        "Troca de óleo",
                        new BigDecimal("1.5"),
                        new BigDecimal("150.00"),
                        LocalDate.now()
                )
        );
    }

    @Test
    void naoDeveCriarServicoComValorNegativo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Servico(
                        "SERV-001",
                        "Troca de óleo",
                        new BigDecimal("1.5"),
                        new BigDecimal("-150.00"),
                        LocalDate.now()
                )
        );
    }

    @Test
    void deveAlterarValorBase() {

        Servico servico = new Servico(
                "SERV-001",
                "Troca de óleo",
                new BigDecimal("1.5"),
                new BigDecimal("150.00"),
                LocalDate.now()
        );

        servico.alterarValorBase(
                new BigDecimal("200.00")
        );

        assertEquals(
                new BigDecimal("200.00"),
                servico.getValorBase()
        );
    }

    @Test
    void deveDefinirCategoria() {

        CategoriaServico categoria = new CategoriaServico(
                "Manutenção Preventiva"
        );

        Servico servico = new Servico(
                "SERV-001",
                "Troca de óleo",
                new BigDecimal("1.5"),
                new BigDecimal("150.00"),
                LocalDate.now()
        );

        servico.definirCategoria(categoria);

        assertEquals(
                categoria,
                servico.getCategoriaServico()
        );
    }

    @Test
    void deveInativarServico() {

        Servico servico = new Servico(
                "SERV-001",
                "Troca de óleo",
                new BigDecimal("1.5"),
                new BigDecimal("150.00"),
                LocalDate.now()
        );

        servico.inativar();

        assertEquals(
                Status.INATIVO,
                servico.getStatus()
        );
    }
}