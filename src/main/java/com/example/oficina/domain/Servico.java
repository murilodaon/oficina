
package com.example.oficina.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "horas_estimadas", nullable = false)
    private BigDecimal horasEstimadas;

    @Column(name = "valor_base", nullable = false)
    private BigDecimal valorBase;

    @Column(
        name = "estoque_minimo",
        nullable = false,
        precision = 18,
        scale = 3
    )
    private BigDecimal estoqueMinimo;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "categoria_servico_id",
        nullable = false
    )
    private CategoriaServico categoriaServico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "fornecedor_id",
        foreignKey = @ForeignKey(name = "fk_servico_fornecedor")
    )
    private Fornecedor fornecedor;

    protected Servico() {
    }

    public Servico(
        String codigo,
        String descricao,
        BigDecimal horasEstimadas,
        BigDecimal valorBase,
        LocalDate dataCadastro) {

        this(
            codigo,
            descricao,
            horasEstimadas,
            valorBase,
            BigDecimal.ZERO,
            dataCadastro
        );
    }

    public Servico(
        String codigo,
        String descricao,
        BigDecimal horasEstimadas,
        BigDecimal valorBase,
        BigDecimal estoqueMinimo,
        LocalDate dataCadastro) {

        this.codigo = validarTextoObrigatorio(
            codigo,
            "Código é obrigatório"
        );

        this.descricao = validarTextoObrigatorio(
            descricao,
            "Descrição é obrigatória"
        );

        this.horasEstimadas = validarNaoNegativo(
            horasEstimadas,
            "Horas estimadas não podem ser negativas"
        );

        this.valorBase = validarNaoNegativo(
            valorBase,
            "Valor base não pode ser negativo"
        );

        this.estoqueMinimo = validarNaoNegativo(
            estoqueMinimo,
            "Estoque mínimo não pode ser negativo"
        );

        this.dataCadastro = validarDataObrigatoria(
            dataCadastro,
            "Data de cadastro é obrigatória"
        );

        this.status = Status.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getHorasEstimadas() {
        return horasEstimadas;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Status getStatus() {
        return status;
    }

    public CategoriaServico getCategoriaServico() {
        return categoriaServico;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void alterarCodigo(String novoCodigo) {
        this.codigo = validarTextoObrigatorio(
            novoCodigo,
            "Código é obrigatório"
        );
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = validarTextoObrigatorio(
            novaDescricao,
            "Descrição é obrigatória"
        );
    }

    public void alterarHorasEstimadas(
        BigDecimal novasHorasEstimadas) {

        this.horasEstimadas = validarNaoNegativo(
            novasHorasEstimadas,
            "Horas estimadas não podem ser negativas"
        );
    }

    public void alterarValorBase(BigDecimal novoValorBase) {
        this.valorBase = validarNaoNegativo(
            novoValorBase,
            "Valor base não pode ser negativo"
        );
    }

    public void alterarEstoqueMinimo(
        BigDecimal novoEstoqueMinimo) {

        this.estoqueMinimo = validarNaoNegativo(
            novoEstoqueMinimo,
            "Estoque mínimo não pode ser negativo"
        );
    }

    public void alterarDataCadastro(LocalDate novaDataCadastro) {
        this.dataCadastro = validarDataObrigatoria(
            novaDataCadastro,
            "Data de cadastro é obrigatória"
        );
    }

    public void definirCategoria(
        CategoriaServico categoriaServico) {

        if (categoriaServico == null) {
            throw new IllegalArgumentException(
                "Categoria do serviço é obrigatória"
            );
        }

        this.categoriaServico = categoriaServico;
    }

    public void definirFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    private static String validarTextoObrigatorio(
        String texto,
        String mensagem) {

        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }

        return texto.trim();
    }

    private static BigDecimal validarNaoNegativo(
        BigDecimal valor,
        String mensagem) {

        if (valor == null || valor.signum() < 0) {
            throw new IllegalArgumentException(mensagem);
        }

        return valor;
    }

    private static LocalDate validarDataObrigatoria(
        LocalDate data,
        String mensagem) {

        if (data == null) {
            throw new IllegalArgumentException(mensagem);
        }

        return data;
    }
}
