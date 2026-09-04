package com.example.oficina.api.controller;

import com.example.oficina.domain.CategoriaServico;
import com.example.oficina.repository.CategoriaServicoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CategoriaServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaServicoRepository repository;

    @Test
    void deveCadastrarCategoriaServico() throws Exception {

        mockMvc.perform(
                        post("/api/categorias-servico")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "nome": "Mecânica"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Mecânica"))
                .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveBuscarCategoriaServicoPorId() throws Exception {

        CategoriaServico categoria =
                repository.save(
                        new CategoriaServico("Elétrica")
                );

        mockMvc.perform(
                        get("/api/categorias-servico/{id}",
                                categoria.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoria.getId()))
                .andExpect(jsonPath("$.nome").value("Elétrica"))
                .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveRetornarErroQuandoNomeForInvalido() throws Exception {

        mockMvc.perform(
                        post("/api/categorias-servico")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "nome": ""
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Dados de entrada inválidos"));
    }

    @Test
    void deveRetornar404QuandoCategoriaNaoExistir()
            throws Exception {

        mockMvc.perform(
                        get("/api/categorias-servico/{id}", 999999L)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("Categoria de serviço não encontrada"))
                .andExpect(jsonPath("$.path")
                        .value("/api/categorias-servico/999999"));
    }
}