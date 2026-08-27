package com.example.oficina.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Servico {

    private final String codigo;
    private String descricao;
    private BigDecimal horasEstimadas;
    private BigDecimal valorBase;
    private final LocalDate dataCadastro;
    private Status status;
    private CategoriaServico categoriaServico;

    public Servico(
            String codigo,
            String descricao,
            BigDecimal horasEstimadas,
            BigDecimal valorBase,
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

        this.dataCadastro = validarDataObrigatoria(
                dataCadastro,
                "Data de cadastro é obrigatória"
        );

        this.status = Status.ATIVO;
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Status getStatus() {
        return status;
    }

    public CategoriaServico getCategoriaServico() {
        return categoriaServico;
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = validarTextoObrigatorio(
                novaDescricao,
                "Descrição é obrigatória"
        );
    }

    public void alterarHorasEstimadas(BigDecimal novasHorasEstimadas) {
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

    public void definirCategoria(CategoriaServico categoriaServico) {
        if (categoriaServico == null) {
            throw new IllegalArgumentException(
                    "Categoria do serviço é obrigatória"
            );
        }

        this.categoriaServico = categoriaServico;
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

        if (valor == null) {
            throw new NullPointerException(mensagem);
        }

        if (valor.signum() < 0) {
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