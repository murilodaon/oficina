package com.example.oficina.exception;

public class RecursoDuplicadoException extends RuntimeException {

    public RecursoDuplicadoException(String mensagem) {
        super(mensagem);
    }
}