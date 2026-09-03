package com.example.oficina.service;

import com.example.oficina.domain.Fornecedor;
import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import com.example.oficina.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository repository;

    @InjectMocks
    private FornecedorService service;

    @Test
    void deveCadastrarFornecedor() {

        when(repository.existsByCnpj("12345678000199"))
                .thenReturn(false);

        Fornecedor fornecedor = new Fornecedor(
                "Oficina Fornecedora LTDA",
                "12345678000199"
        );

        when(repository.save(any(Fornecedor.class)))
                .thenReturn(fornecedor);

        Fornecedor resultado = service.cadastrar(
                "Oficina Fornecedora LTDA",
                "12345678000199"
        );

        assertNotNull(resultado);
        assertEquals(
                "Oficina Fornecedora LTDA",
                resultado.getRazaoSocial()
        );
        assertEquals(
                "12345678000199",
                resultado.getCnpj()
        );

        verify(repository).existsByCnpj("12345678000199");
        verify(repository).save(any(Fornecedor.class));
    }

    @Test
    void naoDeveCadastrarFornecedorComCnpjDuplicado() {

        when(repository.existsByCnpj("12345678000199"))
                .thenReturn(true);

        assertThrows(
                RecursoDuplicadoException.class,
                () -> service.cadastrar(
                        "Oficina Fornecedora LTDA",
                        "12345678000199"
                )
        );

        verify(repository).existsByCnpj("12345678000199");
        verify(repository, never()).save(any(Fornecedor.class));
    }

    @Test
    void deveBuscarFornecedorPorId() {

        Fornecedor fornecedor = new Fornecedor(
                "Oficina Fornecedora LTDA",
                "12345678000199"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(fornecedor));

        Fornecedor resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(
                "12345678000199",
                resultado.getCnpj()
        );

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoFornecedorNaoExistir() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(999L)
        );

        verify(repository).findById(999L);
    }

    @Test
    void deveListarFornecedores() {

        Fornecedor fornecedor1 = new Fornecedor(
                "Fornecedor A LTDA",
                "11111111000111"
        );

        Fornecedor fornecedor2 = new Fornecedor(
                "Fornecedor B LTDA",
                "22222222000122"
        );

        when(repository.findAll())
                .thenReturn(List.of(fornecedor1, fornecedor2));

        List<Fornecedor> resultado = service.listar();

        assertEquals(2, resultado.size());
        assertEquals(
                "11111111000111",
                resultado.get(0).getCnpj()
        );
        assertEquals(
                "22222222000122",
                resultado.get(1).getCnpj()
        );

        verify(repository).findAll();
    }
}