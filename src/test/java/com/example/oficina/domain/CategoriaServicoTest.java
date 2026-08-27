package com.example.oficina.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoriaServicoTest {

    @Test
    void deveCriarCategoriaComStatusAtivo() {

        CategoriaServico categoria = new CategoriaServico(
                "Manutenção Preventiva"
        );

        assertEquals(
                "Manutenção Preventiva",
                categoria.getNome()
        );

        assertEquals(
                Status.ATIVO,
                categoria.getStatus()
        );
    }

    @Test
    void naoDeveCriarCategoriaComNomeVazio() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new CategoriaServico(" ")
        );
    }

    @Test
    void deveInativarCategoria() {

        CategoriaServico categoria = new CategoriaServico(
                "Serviços de Motor"
        );

        categoria.inativar();

        assertEquals(
                Status.INATIVO,
                categoria.getStatus()
        );
    }

    @Test
    void deveAtivarCategoriaInativa() {

        CategoriaServico categoria = new CategoriaServico(
                "Serviços Elétricos"
        );

        categoria.inativar();
        categoria.ativar();

        assertEquals(
                Status.ATIVO,
                categoria.getStatus()
        );
    }
}