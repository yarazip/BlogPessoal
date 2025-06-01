package com.blogpessoal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogpessoal.model.Usuario;
import com.blogpessoal.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; 
    

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countUsuarios() {
        long count = usuarioService.countUsuarios(); 
        return ResponseEntity.ok(Map.of("quantidade", count));
    }
    
    @PutMapping(value = "/{id}/foto", consumes = {"multipart/form-data"})
    public ResponseEntity<Usuario> atualizarFoto(
        @PathVariable Long id,
        @RequestParam("foto") MultipartFile foto) {
        try {
            Usuario usuario = usuarioService.atualizarFoto(id, foto);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
