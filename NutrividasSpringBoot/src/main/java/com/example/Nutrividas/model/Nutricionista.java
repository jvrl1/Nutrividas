package com.example.Nutrividas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="nutricionista")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Nutricionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutricionista_id")
    private Long id;

    @Column(length = 150)
    private String nome;

    @Column(unique = true, length = 150)
    private String email;

    @Column(length = 15)
    private String telefone;

    @Column(length = 250)
    private String endereco;

    @Column(length = 500)
    private String descricao;

    @Column(length = 255)  
    private String imagem; 

}
