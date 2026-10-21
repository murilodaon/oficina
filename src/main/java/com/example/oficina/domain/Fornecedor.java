package com.example.oficina.domain;

import jakarta.persistence.*;

@Entity
@Table(
    name = "fornecedor",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_fornecedor_cnpj",
        columnNames = "cnpj"
    )
)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 150)
    private String razaoSocial;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    protected Fornecedor() {
    }

    public Fornecedor(
        String razaoSocial,
        String cnpj) {

        this.razaoSocial = validarRazaoSocial(razaoSocial);
        this.cnpj = validarCnpj(cnpj);
        this.status = Status.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Status getStatus() {
        return status;
    }

    public void alterarRazaoSocial(String novaRazaoSocial) {
        this.razaoSocial = validarRazaoSocial(novaRazaoSocial);
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    private static String validarRazaoSocial(String razaoSocial) {
        if (razaoSocial == null || razaoSocial.isBlank()) {
            throw new IllegalArgumentException(
                "Razão social é obrigatória"
            );
        }

        return razaoSocial.trim();
    }

    private static String validarCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException(
                "CNPJ é obrigatório"
            );
        }

        return cnpj.trim();
    }
}
