package com.blogpessoal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.dto.StatsResponse;
import com.blogpessoal.model.Postagem;
import com.blogpessoal.model.Tema;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repository.PostagemRepository;
import com.blogpessoal.repository.TemaRepository;
import com.blogpessoal.repository.UsuarioRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Postagem> findAll() {
		return postagemRepository.findAll();
	}

	public List<StatsResponse> getPostStats() {
		return postagemRepository.getPostCountByWeekDay();
	}

	public Optional<Postagem> findById(Long id) {
		return postagemRepository.findById(id);
	}

	public List<Postagem> findByTitulo(String titulo) {
		return postagemRepository.findAllByTituloContainingIgnoreCase(titulo);
	}

	public List<Postagem> findByUsuarioId(Long usuarioId) {
		return postagemRepository.findAllByUsuarioId(usuarioId);
	}

	public List<Postagem> findByTemaId(Long temaId) {
		return postagemRepository.findAllByTemaId(temaId);
	}

	public List<Postagem> findByUsuarioIdAndTemaId(Long usuarioId, Long temaId) {
		return postagemRepository.findAllByUsuarioIdAndTemaId(usuarioId, temaId);
	}
	
	public Postagem save(Postagem postagem, Usuario usuario) {
		Tema tema = temaRepository.findById(postagem.getTema().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado!"));

		postagem.setTema(tema);
		postagem.setUsuario(usuario);

		return postagemRepository.save(postagem);
	}

	public Postagem update(Long id, Postagem postagemAtualizada, Usuario usuario) {
		Postagem postagemExistente = postagemRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada!"));

		if (!postagemExistente.getUsuario().getId().equals(usuario.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode editar essa postagem");
		}

		Tema tema = temaRepository.findById(postagemAtualizada.getTema().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado!"));

		postagemExistente.setTitulo(postagemAtualizada.getTitulo());
		postagemExistente.setTexto(postagemAtualizada.getTexto());
		postagemExistente.setTema(tema);

		return postagemRepository.save(postagemExistente);
	}

	public void delete(Long id) {
		if (!postagemRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada com o ID: " + id);
		}
		postagemRepository.deleteById(id);
	}

}