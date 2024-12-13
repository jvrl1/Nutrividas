package com.example.Nutrividas.service;

import com.example.Nutrividas.dto.UsuarioDto;
import com.example.Nutrividas.mapper.UsuarioMapper;
import com.example.Nutrividas.model.Usuario;
import com.example.Nutrividas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<?> login(UsuarioDto usuarioDto) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(usuarioDto.getEmail());

        if (usuario != null && usuario.getSenha().equals(usuarioDto.getSenha())) {
            // Retorna apenas as informações necessárias, excluindo a senha
            UsuarioDto response = new UsuarioDto();
            response.setId(usuario.getId());
            response.setNome(usuario.getNome());
            response.setEmail(usuario.getEmail());
            response.setTelefone(usuario.getTelefone());
            response.setCpf(usuario.getCpf());
            return ResponseEntity.ok(response);
        }

        // Caso a autenticação falhe
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
    }
     
    public void save(UsuarioDto usuarioDto) {
        // Mapeia o DTO para a entidade Usuario e salva no banco
        Usuario entity = UsuarioMapper.INSTANCE.toModel(usuarioDto);
        usuarioRepository.save(entity);
    }

    public ResponseEntity<?> deleteUsuarioById(Long id) {
        // Verifica se o usuário existe antes de deletar
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
    }

    public ResponseEntity<?> getUsuario(Long id) {
        // Recupera um usuário pelo ID
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.INSTANCE.toDto(usuario.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
    }

    public List<UsuarioDto> findAllUsuarios() {
        // Recupera todos os usuários e os converte para DTO
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                       .map(UsuarioMapper.INSTANCE::toDto)
                       .toList();
    }

    public ResponseEntity<?> updateUsuarioById(Long id, UsuarioDto usuarioDto) {
        // Verifica se o usuário existe antes de realizar a atualização
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setTelefone(usuarioDto.getTelefone());
            usuario.setCpf(usuarioDto.getCpf());
            usuario.setSenha(usuarioDto.getSenha());
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.INSTANCE.toDto(usuario));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
    }
}
