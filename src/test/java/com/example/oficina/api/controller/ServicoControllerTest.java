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
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaServicoRepository categoriaRepository;

    @Test
    void deveCadastrarServico() throws Exception {

        CategoriaServico categoria =
                categoriaRepository.save(
                        new CategoriaServico("Mecânica")
                );

        mockMvc.perform(
                        post("/api/servicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "codigo": "SRV-001",
                                          "descricao": "Troca de óleo",
                                          "horasEstimadas": 1.5,
                                          "valorBase": 150.00,
                                          "estoqueMinimo": 2.000,
                                          "dataCadastro": "2026-09-03",
                                          "categoriaServicoId": %d,
                                          "fornecedorId": null
                                        }
                                        """.formatted(categoria.getId()))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.codigo")
                        .value("SRV-001"))
                .andExpect(jsonPath("$.descricao")
                        .value("Troca de óleo"))
                .andExpect(jsonPath("$.status")
                        .value("ATIVO"))
                .andExpect(jsonPath("$.categoriaServicoId")
                        .value(categoria.getId()))
                .andExpect(jsonPath("$.categoriaServicoNome")
                        .value("Mecânica"));
    }

    @Test
    void deveBuscarServicoPorId() throws Exception {

        CategoriaServico categoria =
                categoriaRepository.save(
                        new CategoriaServico("Elétrica")
                );

        String location =
                mockMvc.perform(
                                post("/api/servicos")
                                        .contentType(
                                                MediaType.APPLICATION_JSON)
                                        .content("""
                                                {
                                                  "codigo": "SRV-002",
                                                  "descricao": "Revisão elétrica",
                                                  "horasEstimadas": 2.0,
                                                  "valorBase": 250.00,
                                                  "estoqueMinimo": 1.000,
                                                  "dataCadastro": "2026-09-03",
                                                  "categoriaServicoId": %d,
                                                  "fornecedorId": null
                                                }
                                                """.formatted(
                                                categoria.getId()))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo")
                        .value("SRV-002"))
                .andExpect(jsonPath("$.descricao")
                        .value("Revisão elétrica"))
                .andExpect(jsonPath("$.categoriaServicoNome")
                        .value("Elétrica"));
    }

    @Test
    void deveRetornarErroQuandoDadosForemInvalidos()
            throws Exception {

        mockMvc.perform(
                        post("/api/servicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "codigo": "",
                                          "descricao": "",
                                          "horasEstimadas": -1,
                                          "valorBase": -10,
                                          "estoqueMinimo": -1,
                                          "dataCadastro": null,
                                          "categoriaServicoId": null,
                                          "fornecedorId": null
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Dados de entrada inválidos"));
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado()
            throws Exception {

        mockMvc.perform(
                        post("/api/servicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "codigo": "SRV-003",
                                          "descricao": "Alinhamento",
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("JSON inválido ou malformado"))
                .andExpect(jsonPath("$.path")
                        .value("/api/servicos"));
    }
}