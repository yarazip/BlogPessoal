package com.blogpessoal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.dto.PostagemDTO;
import com.blogpessoal.dto.PostagemRequestDTO;
import com.blogpessoal.dto.StatsResponse;
import com.blogpessoal.dto.TemaResumoDTO;
import com.blogpessoal.dto.UsuarioResumoDTO;
import com.blogpessoal.model.Postagem;
import com.blogpessoal.model.Tema;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repository.PostagemRepository;
import com.blogpessoal.repository.TemaRepository;
import com.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.service.PostagemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/postagens")
public class PostagemController {

    @Autowired
    private PostagemService postagemService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TemaRepository temaRepository;


    @Autowired
    private PostagemRepository postagemRepository;


    @Operation(summary = "Buscar postagem por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PostagemDTO> getById(@Parameter(description = "ID da postagem") @PathVariable Long id) {
        return postagemService.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<PostagemDTO>> getByTitulo(@PathVariable String titulo) {
        List<PostagemDTO> dtos = postagemService.findByTitulo(titulo)
            .stream()
            .map(this::toDTO)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatsResponse>> getPostStats() {
        return ResponseEntity.ok(postagemService.getPostStats());
    }


    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countPostagens() {
        Map<String, Long> resposta = Map.of("quantidade", postagemRepository.count());
        return ResponseEntity.ok(resposta);
    }
    
    
    @GetMapping("/filtro")
    public ResponseEntity<List<PostagemDTO>> getByFiltro(
            @RequestParam(value = "autor", required = false) Long autorId,
            @RequestParam(value = "tema", required = false) Long temaId) {

        List<Postagem> postagens;

        if (autorId != null && temaId != null)
            postagens = postagemService.findByUsuarioIdAndTemaId(autorId, temaId);
        else if (autorId != null)
            postagens = postagemService.findByUsuarioId(autorId);
        else if (temaId != null)
            postagens = postagemService.findByTemaId(temaId);
        else
            return ResponseEntity.badRequest().build();

        List<PostagemDTO> dtos = postagens.stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<PostagemDTO> post(@Valid @RequestBody PostagemRequestDTO postagemDTO, 
            @AuthenticationPrincipal UserDetails usuarioLogado) 
 {
        Usuario usuario = usuarioRepository.findByEmail(usuarioLogado.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        Tema tema = temaRepository.findById(postagemDTO.getIdTema())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado"));

        Postagem postagem = new Postagem();
        postagem.setTitulo(postagemDTO.getTitulo());
        postagem.setTexto(postagemDTO.getTexto());
        postagem.setTema(tema);
        postagem.setUsuario(usuario);

        Postagem postagemSalva = postagemService.save(postagem, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(postagemSalva));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<PostagemDTO> put(@PathVariable Long id,
            @Valid @RequestBody PostagemRequestDTO postagemDTO,
            @AuthenticationPrincipal UserDetails usuarioLogado) {

    	    Usuario usuario = usuarioRepository.findByEmail(usuarioLogado.getUsername())
    	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

    	    Tema tema = temaRepository.findById(postagemDTO.getIdTema())
    	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado"));

    	    Postagem postagemAtualizada = new Postagem();
    	    postagemAtualizada.setTitulo(postagemDTO.getTitulo());
    	    postagemAtualizada.setTexto(postagemDTO.getTexto());
    	    postagemAtualizada.setTema(tema);
    	    postagemAtualizada.setUsuario(usuario);

    	    Postagem postagemSalva = postagemService.update(id, postagemAtualizada, usuario);

    	    return ResponseEntity.ok(toDTO(postagemSalva));
    	}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails usuarioLogado) {
        Postagem postagemExistente = postagemService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada"));

        Usuario usuario = usuarioRepository.findByEmail(usuarioLogado.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        if (!postagemExistente.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode deletar essa postagem");

        }
        System.out.println("Usuário logado: " + usuarioLogado.getUsername());

        postagemService.delete(id);
        return ResponseEntity.noContent().build();
    }
  
    
    
    @GetMapping
    public ResponseEntity<List<PostagemDTO>> getAll() {
        List<PostagemDTO> dtos = postagemService.findAll()
            .stream()
            .map(this::toDTO)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    private PostagemDTO toDTO(Postagem postagem) {
        UsuarioResumoDTO usuarioDTO = new UsuarioResumoDTO();
        usuarioDTO.setId(postagem.getUsuario().getId());
        usuarioDTO.setNome(postagem.getUsuario().getNome());
        usuarioDTO.setEmail(postagem.getUsuario().getEmail());
        usuarioDTO.setFoto(postagem.getUsuario().getFoto());

        TemaResumoDTO temaDTO = new TemaResumoDTO();
        temaDTO.setId(postagem.getTema().getId());
        temaDTO.setDescricao(postagem.getTema().getDescricao());

        PostagemDTO dto = new PostagemDTO();
        dto.setId(postagem.getId());
        dto.setTitulo(postagem.getTitulo());
        dto.setTexto(postagem.getTexto());
        dto.setData(postagem.getData());
        dto.setTema(temaDTO);
        dto.setUsuario(usuarioDTO);

        return dto;
    }


}
