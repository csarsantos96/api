package com.forumhub.api.dto;

import java.time.LocalDateTime;

public class TopicoResponse {

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoResponse(String titulo, String mensagem, LocalDateTime dataCriacao) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
