package com.example.Nutrividas.service;

import com.example.Nutrividas.dto.NutricionistaDto;
import com.example.Nutrividas.mapper.NutricionistaMapper;
import com.example.Nutrividas.model.Nutricionista;
import com.example.Nutrividas.repository.NutricionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutricionistaService {

    @Autowired
    NutricionistaRepository nutricionistaRepository;

    // Método para salvar um nutricionista a partir de um DTO
    public void save(NutricionistaDto nutricionistaDto) {
        // Converte o DTO em um modelo Nutricionista e salva no repositório
        Nutricionista entity = NutricionistaMapper.INSTANCE.toModel(nutricionistaDto);
        nutricionistaRepository.save(entity);
    }

    // Método para deletar um nutricionista com base no ID
    public ResponseEntity<?> deleteNutricionistaById(Long id) {
        // Verifica se o nutricionista existe no banco de dados
        if (nutricionistaRepository.existsById(id)) {
            // Se existir, deleta e retorna uma resposta de sucesso
            nutricionistaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Nutricionista removido com sucesso!");
        }
        // Caso não encontre o nutricionista, retorna uma resposta de erro
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutricionista não encontrado!");
    }

    // Método para obter um nutricionista pelo ID
    public ResponseEntity<?> getNutricionista(Long id){
        // Tenta buscar o nutricionista no banco de dados
        Optional<Nutricionista> nutricionista = nutricionistaRepository.findById(id);
        // Se o nutricionista existir, converte para DTO e retorna em uma resposta com status 200 OK
        if(nutricionista.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(NutricionistaMapper.INSTANCE.toDto(nutricionista.get()));
        // Se não encontrar, retorna um erro 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutricionista não encontrado!");
    }

    // Método para buscar todos os nutricionistas cadastrados
    public List<NutricionistaDto> findAllNutricionistas() {
        // Busca todos os nutricionistas no banco de dados
        List<Nutricionista> nutricionistas = nutricionistaRepository.findAll();
        // Converte a lista de nutricionistas em uma lista de DTOs
        return nutricionistas.stream()
                      .map(NutricionistaMapper.INSTANCE::toDto)
                      .toList();
    }

    // Método para atualizar os dados de um nutricionista com base no ID
    public ResponseEntity<?> updateNutricionistaById(Long id, NutricionistaDto nutricionistaDto) {
        // Verifica se o nutricionista já existe no banco de dados
        Optional<Nutricionista> nutricionistaExistente = nutricionistaRepository.findById(id);
        // Se existir, atualiza os dados do nutricionista e salva no banco
        if (nutricionistaExistente.isPresent()) {
            Nutricionista nutricionista = nutricionistaExistente.get();
            nutricionista.setNome(nutricionistaDto.getNome());
            nutricionista.setEmail(nutricionistaDto.getEmail());
            nutricionista.setTelefone(nutricionistaDto.getTelefone());
            nutricionista.setEndereco(nutricionistaDto.getEndereco());
            nutricionista.setDescricao(nutricionistaDto.getDescricao());
            nutricionista.setImagem(nutricionistaDto.getImagem());
            nutricionistaRepository.save(nutricionista);
            // Retorna os dados atualizados do nutricionista
            return ResponseEntity.status(HttpStatus.OK).body(NutricionistaMapper.INSTANCE.toDto(nutricionista));
        }
        // Caso não encontre o nutricionista, retorna um erro 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutricionista não encontrado!");
    }
}
