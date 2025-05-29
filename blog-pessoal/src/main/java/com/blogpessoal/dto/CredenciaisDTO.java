package com.blogpessoal.dto;

public class CredenciaisDTO {
    private Long id;
    private String nome;
    private String email;
    private String foto;
    private String token;

    public CredenciaisDTO(Long id, String nome, String email, String foto, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.foto = foto;
        this.token = token;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getFoto() { return foto; }
    public String getToken() { return token; }
}