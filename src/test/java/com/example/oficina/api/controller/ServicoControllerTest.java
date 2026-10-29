
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

    private CategoriaServico criarCategoria() {
        return categoriaRepository.save(
            new CategoriaServico("Manutenção")
        );
    }

    private String jsonServico(String codigo, Long categoriaId) {
        return """
            {
                "codigo": "%s",
                "descricao": "Serviço de teste",
                "horasEstimadas": 2,
                "valorBase": 150.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(codigo, categoriaId);
    }

    private String cadastrarServico(String codigo) throws Exception {
        CategoriaServico categoria = criarCategoria();

        return mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonServico(codigo, categoria.getId())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");
    }

    @Test
    void deveCadastrarServico() throws Exception {
        CategoriaServico categoria = criarCategoria();

        mockMvc.perform(post("/api/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonServico("SRV-001", categoria.getId())))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.codigo").value("SRV-001"));
    }

    @Test
    void deveBuscarServicoPorId() throws Exception {
        String location = cadastrarServico("SRV-002");

        mockMvc.perform(get(location))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo").value("SRV-002"));
    }

    @Test
    void deveAtualizarServico() throws Exception {
        String location = cadastrarServico("SRV-003");
        CategoriaServico categoria = criarCategoria();

        String json = """
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
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descricao")
                .value("Serviço atualizado"))
            .andExpect(jsonPath("$.valorBase").value(250.00));
    }

    @Test
    void deveRetornar404AoAtualizarServicoInexistente()
        throws Exception {

        CategoriaServico categoria = criarCategoria();

        mockMvc.perform(put("/api/servicos/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonServico("SRV-004", categoria.getId())))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message")
                .value("Serviço não encontrado"));
    }

    @Test
    void naoDeveAtualizarServicoComCodigoDuplicado() throws Exception {
        cadastrarServico("SRV-005");
        String locationSegundo = cadastrarServico("SRV-006");

        CategoriaServico categoria = criarCategoria();

        String json = """
            {
                "codigo": "SRV-005",
                "descricao": "Serviço atualizado",
                "horasEstimadas": 3,
                "valorBase": 150.00,
                "estoqueMinimo": 0,
                "dataCadastro": "2026-10-22",
                "categoriaServicoId": %d
            }
            """.formatted(categoria.getId());

        mockMvc.perform(put(locationSegundo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message")
                .value("Código do serviço já cadastrado"));
    }

    @Test
    void deveExcluirServico() throws Exception {
        String location = cadastrarServico("SRV-007");

        mockMvc.perform(delete(location))
            .andExpect(status().isNoContent());

        mockMvc.perform(get(location))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoExcluirServicoInexistente()
        throws Exception {

        mockMvc.perform(delete("/api/servicos/999999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarErroQuandoDadosForemInvalidos()
        throws Exception {

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

    @Test
    void deveAtivarServico() throws Exception {
        String location = cadastrarServico("SRV-008");
        Long id = Long.valueOf(
            location.substring(location.lastIndexOf("/") + 1)
        );

        mockMvc.perform(put("/api/servicos/" + id + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "status": "ATIVO"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveInativarServico() throws Exception {
        String location = cadastrarServico("SRV-009");
        Long id = Long.valueOf(
            location.substring(location.lastIndexOf("/") + 1)
        );

        mockMvc.perform(put("/api/servicos/" + id + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "status": "INATIVO"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("INATIVO"));
    }

    @Test
    void deveRetornar404AoAlterarStatusDeServicoInexistente()
        throws Exception {

        mockMvc.perform(put("/api/servicos/999999/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "status": "ATIVO"
                    }
                    """))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoStatusForNulo() throws Exception {
        String location = cadastrarServico("SRV-010");
        Long id = Long.valueOf(
            location.substring(location.lastIndexOf("/") + 1)
        );

        mockMvc.perform(put("/api/servicos/" + id + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "status": null
                    }
                    """))
            .andExpect(status().isBadRequest());
    }
}
