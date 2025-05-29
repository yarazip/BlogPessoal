package com.blogpessoal.dto;

public class UsuarioResumoDTO {

    private Long id;
    private String nome;
    private String email;
    private String foto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public UsuarioResumoDTO(Long id, String nome, String email, String foto) {
	    this.id = id;
	    this.nome = nome;
	    this.email = email;
	    this.foto = foto;
	}

	public UsuarioResumoDTO() {}

}
