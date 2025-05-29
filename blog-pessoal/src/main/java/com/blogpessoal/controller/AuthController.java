package com.blogpessoal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.dto.CredenciaisDTO;
import com.blogpessoal.dto.UsuarioDTO;
import com.blogpessoal.dto.UsuarioLoginDTO;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<CredenciaisDTO> login(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        System.out.println("Email recebido: " + loginDTO.getEmail());
        System.out.println("Senha recebida: " + loginDTO.getSenha());

        CredenciaisDTO credenciais = usuarioService.autenticarUsuario(loginDTO);
    

        if (credenciais == null) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas"
            );
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, credenciais.getToken())
                .body(credenciais);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registerUser(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        if (usuarioService.emailJaExiste(usuarioDTO.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email já está em uso"
            );
        }

        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO.toUsuario());

        if (usuario == null) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao cadastrar usuário"
            );
        }

        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO();
        loginDTO.setEmail(usuarioDTO.getEmail());
        loginDTO.setSenha(usuarioDTO.getSenha());

        CredenciaisDTO credenciais = usuarioService.autenticarUsuario(loginDTO);

        if (credenciais == null) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao autenticar usuário após cadastro"
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, credenciais.getToken())
                .body(UsuarioDTO.from(usuario));
    }
}
