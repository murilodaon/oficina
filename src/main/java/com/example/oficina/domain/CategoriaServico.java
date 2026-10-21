package com.example.oficina.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria_servico")
public class CategoriaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected CategoriaServico() {
    }

    public CategoriaServico(String nome) {
        this.nome = validarNome(nome);
        this.status = Status.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Status getStatus() {
        return status;
    }

    public void alterarNome(String novoNome) {
        this.nome = validarNome(novoNome);
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome da categoria é obrigatório"
            );
        }

        return nome.trim();
    }
}
