package com.example.Nutrividas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutricionistaDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String descricao;
    private String imagem;  
}

