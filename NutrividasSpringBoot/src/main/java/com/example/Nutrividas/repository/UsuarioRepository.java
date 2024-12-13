package com.example.Nutrividas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Nutrividas.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Override
    Optional<Usuario> findById(Long id);
    
    // Método adicional para buscar um usuário pelo email (se necessário)
    Usuario findByEmail(String email);
    
    // Método adicional para buscar um usuário pelo CPF (se necessário)
    Optional<Usuario> findByCpf(String cpf);

}
