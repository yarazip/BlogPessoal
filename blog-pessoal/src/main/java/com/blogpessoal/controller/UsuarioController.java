package com.blogpessoal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.dto.UsuarioDTO;
import com.blogpessoal.exception.UsuarioExistenteException;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.security.JwtService;
import com.blogpessoal.security.UserDetailsImpl;
import com.blogpessoal.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")  
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registerUser(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        if (usuarioService.emailJaExiste(usuarioDTO.getEmail())) {
            throw new UsuarioExistenteException();  
        }
        
        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO.toUsuario())
                .orElseThrow(UsuarioExistenteException::new); 
        
        UserDetailsImpl userDetails = new UserDetailsImpl(usuario); 
        String token = jwtService.generateToken(userDetails); 
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(UsuarioDTO.from(usuario));
    }
}
