package com.example.oficina.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FornecedorTest {

    @Test
    void deveCriarFornecedorComStatusAtivo() {

        Fornecedor fornecedor = new Fornecedor(
            "Auto Peças Brasil",
            "12345678000199"
        );

        assertEquals(
            "Auto Peças Brasil",
            fornecedor.getRazaoSocial()
        );

        assertEquals(
            "12345678000199",
            fornecedor.getCnpj()
        );

        assertEquals(
            Status.ATIVO,
            fornecedor.getStatus()
        );
    }

    @Test
    void deveAlterarRazaoSocial() {

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Antigo",
            "12345678000199"
        );

        fornecedor.alterarRazaoSocial(
            "Fornecedor Atualizado"
        );

        assertEquals(
            "Fornecedor Atualizado",
            fornecedor.getRazaoSocial()
        );
    }

    @Test
    void naoDeveAlterarRazaoSocialParaVazia() {

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Teste",
            "12345678000199"
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> fornecedor.alterarRazaoSocial(" ")
        );

        assertEquals(
            "Fornecedor Teste",
            fornecedor.getRazaoSocial()
        );
    }

    @Test
    void deveInativarFornecedor() {

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Teste",
            "12345678000199"
        );

        fornecedor.inativar();

        assertEquals(
            Status.INATIVO,
            fornecedor.getStatus()
        );
    }

    @Test
    void deveAtivarFornecedorInativo() {

        Fornecedor fornecedor = new Fornecedor(
            "Fornecedor Teste",
            "12345678000199"
        );

        fornecedor.inativar();
        fornecedor.ativar();

        assertEquals(
            Status.ATIVO,
            fornecedor.getStatus()
        );
    }
}
