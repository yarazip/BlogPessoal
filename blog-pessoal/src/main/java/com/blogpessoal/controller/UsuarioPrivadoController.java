package com.blogpessoal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioPrivadoController {

    @PreAuthorize("hasRole('ROLE_USER')") 
    @GetMapping("/meus-dados")
    public ResponseEntity<String> getDadosDoUsuario() {
        return ResponseEntity.ok("Este é um endpoint protegido. Apenas ROLE_USER pode acessar.");
    }
}
