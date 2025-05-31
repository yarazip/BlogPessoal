package com.blogpessoal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogpessoal.dto.CredenciaisDTO;
import com.blogpessoal.dto.UsuarioLoginDTO;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.security.JwtService;
import com.blogpessoal.security.UserDetailsImpl;

@Service
public class UsuarioService {
	
	@Autowired
	private CloudinaryService cloudinaryService;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailJaExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (emailJaExiste(usuario.getEmail())) {
            return null;
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(List.of("ROLE_USER"));  

        return usuarioRepository.save(usuario);
    }

    public CredenciaisDTO autenticarUsuario(UsuarioLoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginDTO.getEmail());
        
        if (usuarioOpt.isEmpty() || !compararSenhas(loginDTO.getSenha(), usuarioOpt.get().getSenha())) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
        String token = jwtService.generateToken(userDetails);

        return new CredenciaisDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getFoto(),
            "Bearer " + token
        );
    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        return passwordEncoder.matches(senhaDigitada, senhaBanco);
    }

    public Usuario atualizarFoto(Long id, MultipartFile foto) throws IOException {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (foto != null && !foto.isEmpty()) {
            String urlFoto = cloudinaryService.upload(foto);
            usuario.setFoto(urlFoto);
        }

        return usuarioRepository.save(usuario);
    }


    private String salvarArquivo(MultipartFile arquivo) throws IOException {
        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path caminho = Paths.get("uploads/" + nomeArquivo);

        Files.createDirectories(caminho.getParent());
        Files.copy(arquivo.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }
}
