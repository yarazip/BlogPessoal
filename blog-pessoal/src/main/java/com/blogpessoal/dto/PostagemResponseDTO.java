package com.blogpessoal.dto;

import java.time.LocalDateTime;

import com.blogpessoal.model.Postagem;

public class PostagemResponseDTO {

    private Long id;
    private String titulo;
    private String texto;
    private LocalDateTime data;
    private String tema;
    private UsuarioDTO usuario;

    public PostagemResponseDTO() {}

    public PostagemResponseDTO(Postagem postagem) {
        this.id = postagem.getId();
        this.titulo = postagem.getTitulo();
        this.texto = postagem.getTexto();
        this.data = postagem.getData();
        this.tema = postagem.getTema() != null ? postagem.getTema().getDescricao() : null;
        this.usuario = postagem.getUsuario() != null ? UsuarioDTO.from(postagem.getUsuario()) : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }
}
