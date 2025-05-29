package com.blogpessoal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostagemRequestDTO {


    @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "O texto é obrigatório")
    @Size(min = 10, max = 1000, message = "O texto deve ter entre 10 e 1000 caracteres")
    private String texto;

    @NotNull(message = "O tema é obrigatório")
    private Long idTema;
    
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Long getIdTema() { return idTema; }
    public void setIdTema(Long idTema) { this.idTema = idTema; }
}
