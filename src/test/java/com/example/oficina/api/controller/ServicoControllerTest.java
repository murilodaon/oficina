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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String json = """
            {
                "codigo": "SRV-001",
                "descricao": "Troca de óleo",
                "horasEstimadas": 2,
                "valorBase": 150.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.codigo").value("SRV-001"))
            .andExpect(jsonPath("$.descricao").value("Troca de óleo"));
    }

    @Test
    void deveBuscarServicoPorId() throws Exception {
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String json = """
            {
                "codigo": "SRV-002",
                "descricao": "Revisão geral",
                "horasEstimadas": 3,
                "valorBase": 200.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        String location = mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        mockMvc.perform(get(location))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo").value("SRV-002"))
            .andExpect(jsonPath("$.descricao").value("Revisão geral"));
    }

    @Test
    void deveAtualizarServico() throws Exception {
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String jsonInicial = """
            {
                "codigo": "SRV-003",
                "descricao": "Serviço inicial",
                "horasEstimadas": 2,
                "valorBase": 100.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        String location = mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInicial))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        String jsonAtualizado = """
            {
                "codigo": "SRV-003",
                "descricao": "Serviço atualizado",
                "horasEstimadas": 4,
                "valorBase": 250.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(put(location)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualizado))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descricao").value("Serviço atualizado"))
            .andExpect(jsonPath("$.valorBase").value(250.00));
    }

    @Test
    void deveRetornar404AoAtualizarServicoInexistente() throws Exception {
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String json = """
            {
                "codigo": "SRV-004",
                "descricao": "Serviço de teste",
                "horasEstimadas": 2,
                "valorBase": 100.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(put("/api/servicos/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Serviço não encontrado"));
    }

    @Test
    void naoDeveAtualizarServicoComCodigoDuplicado() throws Exception {
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String primeiroServico = """
            {
                "codigo": "SRV-005",
                "descricao": "Primeiro serviço",
                "horasEstimadas": 2,
                "valorBase": 100.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        String segundoServico = """
            {
                "codigo": "SRV-006",
                "descricao": "Segundo serviço",
                "horasEstimadas": 3,
                "valorBase": 150.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(primeiroServico))
            .andExpect(status().isCreated());

        String location = mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(segundoServico))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        String jsonAtualizado = """
            {
                "codigo": "SRV-005",
                "descricao": "Segundo serviço atualizado",
                "horasEstimadas": 3,
                "valorBase": 150.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(put(location)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualizado))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message")
                .value("Código do serviço já cadastrado"));
    }

    @Test
    void deveExcluirServico() throws Exception {
        CategoriaServico categoria = categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );

        String json = """
            {
                "codigo": "SRV-007",
                "descricao": "Serviço para excluir",
                "horasEstimadas": 2,
                "valorBase": 100.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        String location = mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        mockMvc.perform(delete(location))
            .andExpect(status().isNoContent());

        mockMvc.perform(get(location))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoExcluirServicoInexistente() throws Exception {
        mockMvc.perform(delete("/api/servicos/999999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarErroQuandoDadosForemInvalidos() throws Exception {
        String json = """
            {
                "codigo": "",
                "descricao": "",
                "horasEstimadas": -1,
                "valorBase": -10.00,
                "estoqueMinimo": -1,
                "categoriaServicoId": 1
            }
            """;

        mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("Dados de entrada inválidos"));
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() throws Exception {
        mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ json inválido"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("JSON inválido ou malformado"));
    }
}
