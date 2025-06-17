package com.forumhub.api.dto;

import com.forumhub.api.model.StatusTopico;

import java.time.LocalDateTime;

public class TopicoDetalhadoResponse {

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeCurso;
    private String nomeAutor;
    private StatusTopico status;

    public TopicoDetalhadoResponse(String titulo, String mensagem, LocalDateTime dataCriacao,
                                   String nomeCurso, String nomeAutor, StatusTopico status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.nomeCurso = nomeCurso;
        this.nomeAutor = nomeAutor;
        this.status = status;
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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }
}
