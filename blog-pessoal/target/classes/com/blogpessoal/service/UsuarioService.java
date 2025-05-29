package com.blogpessoal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogpessoal.dto.CredenciaisDTO;
import com.blogpessoal.dto.UsuarioLoginDTO;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.security.JwtService;
import com.blogpessoal.security.UserDetailsImpl;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public boolean emailJaExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent(); 
    }

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        if (emailJaExiste(usuario.getEmail())) {
            return Optional.empty();  
        }

        // Garante que o usuário terá o papel ROLE_USER
        usuario.setRole("ROLE_USER");

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));
    }

    public Optional<CredenciaisDTO> autenticarUsuario(UsuarioLoginDTO usuarioLogin) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogin.getEmail());

        if (usuario.isPresent() && compararSenhas(usuarioLogin.getSenha(), usuario.get().getSenha())) {
            UserDetailsImpl userDetails = new UserDetailsImpl(usuario.get());

            CredenciaisDTO credenciais = new CredenciaisDTO();
            credenciais.setId(usuario.get().getId());
            credenciais.setNome(usuario.get().getNome());
            credenciais.setEmail(usuario.get().getEmail()); 
            credenciais.setFoto(usuario.get().getFoto());
            credenciais.setToken(gerarToken(userDetails));

            return Optional.of(credenciais);
        }

        return Optional.empty();
    }

    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senhaDigitada, senhaBanco);
    }

    private String gerarToken(UserDetailsImpl userDetails) {
        return "Bearer " + jwtService.generateToken(userDetails);
    }
}
