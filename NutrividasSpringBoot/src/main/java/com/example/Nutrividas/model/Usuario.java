package com.example.Nutrividas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(unique = true, length = 150, nullable = false)
    private String email;

    @Column(length = 15, nullable = false)
    private String telefone;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(length = 255, nullable = false)
    private String senha;
}
