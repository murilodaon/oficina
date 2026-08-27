package com.example.oficina.domain;

public class CategoriaServico {

    private String nome;
    private Status status;

    public CategoriaServico(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }

        this.nome = nome.trim();
        this.status = Status.ATIVO;
    }

    public String getNome() {
        return nome;
    }

    public Status getStatus() {
        return status;
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }
}