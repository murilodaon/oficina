package com.example.oficina.service;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.CategoriaServicoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoriaServicoServiceTest {

    @Autowired
    private CategoriaServicoService service;

    @Autowired
    private CategoriaServicoRepository repository;

    @Test
    void deveCadastrarCategoria() {

        CategoriaServico categoria =
                service.cadastrar("Motor");

        assertNotNull(categoria.getId());
        assertEquals("Motor", categoria.getNome());
    }

    @Test
    void naoDeveCadastrarCategoriaDuplicada() {

        service.cadastrar("Motor");

        assertThrows(
                RecursoDuplicadoException.class,
                () -> service.cadastrar("motor")
        );
    }

    @Test
    void deveBuscarCategoriaPorId() {

        CategoriaServico cadastrada =
                service.cadastrar("Freios");

        CategoriaServico encontrada =
                service.buscarPorId(cadastrada.getId());

        assertEquals(
                cadastrada.getId(),
                encontrada.getId()
        );

        assertEquals(
                "Freios",
                encontrada.getNome()
        );
    }

    @Test
    void deveLancarExcecaoAoBuscarCategoriaInexistente() {

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(999999L)
        );
    }

    @Test
    void deveListarCategorias() {

        service.cadastrar("Motor");
        service.cadastrar("Freios");

        var categorias = service.listar();

        assertEquals(2, categorias.size());
    }
}