package com.blogpessoal.dto;

import java.time.LocalDateTime;

import com.blogpessoal.model.Tema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para transferência de dados da Postagem")
public class PostagemDTO {

    @Schema(description = "ID da postagem", example = "1")
    private Long id;

    @Schema(description = "Título da postagem", example = "Minha primeira postagem")
    private String titulo;

    @Schema(description = "Conteúdo da postagem", example = "Este é o texto da postagem.")
    private String texto;

    @Schema(description = "Data e hora da postagem")
    private LocalDateTime data;

    @Schema(description = "Tema associado à postagem")
    private TemaResumoDTO tema;

    @Schema(description = "Usuário que criou a postagem")
    private UsuarioResumoDTO usuario;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public TemaResumoDTO getTema() {
        return tema;
    }
    public void setTema(TemaResumoDTO tema) {
        this.tema = tema;
    }
    public UsuarioResumoDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResumoDTO usuario) {
        this.usuario = usuario;
    }
}
