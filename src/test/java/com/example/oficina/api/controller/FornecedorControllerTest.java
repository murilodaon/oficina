package com.example.oficina.api.controller;

import com.example.oficina.domain.Fornecedor;
import com.example.oficina.repository.FornecedorRepository;
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
class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FornecedorRepository repository;

    @Test
    void deveCadastrarFornecedor() throws Exception {

        mockMvc.perform(
                        post("/api/fornecedores")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "razaoSocial": "Auto Peças Brasil",
                                          "cnpj": "12345678000199"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.razaoSocial")
                        .value("Auto Peças Brasil"))
                .andExpect(jsonPath("$.cnpj")
                        .value("12345678000199"))
                .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveBuscarFornecedorPorId() throws Exception {

        Fornecedor fornecedor =
                repository.save(
                        new Fornecedor(
                                "Distribuidora Oficina",
                                "98765432000188"
                        )
                );

        mockMvc.perform(
                        get("/api/fornecedores/{id}",
                                fornecedor.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(fornecedor.getId()))
                .andExpect(jsonPath("$.razaoSocial")
                        .value("Distribuidora Oficina"))
                .andExpect(jsonPath("$.cnpj")
                        .value("98765432000188"))
                .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveRetornarErroQuandoRazaoSocialForInvalida()
            throws Exception {

        mockMvc.perform(
                        post("/api/fornecedores")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "razaoSocial": "",
                                          "cnpj": "12345678000199"
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Dados de entrada inválidos"));
    }

    @Test
    void deveRetornar409QuandoCnpjJaEstiverCadastrado()
            throws Exception {

        repository.save(
                new Fornecedor(
                        "Fornecedor Existente",
                        "11222333000144"
                )
        );

        mockMvc.perform(
                        post("/api/fornecedores")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "razaoSocial": "Outro Fornecedor",
                                          "cnpj": "11222333000144"
                                        }
                                        """)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("CNPJ do fornecedor já cadastrado"))
                .andExpect(jsonPath("$.path")
                        .value("/api/fornecedores"));
    }
}