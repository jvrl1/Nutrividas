package com.example.Nutrividas.service;

import com.example.Nutrividas.dto.ConsultaDto;
import com.example.Nutrividas.mapper.ConsultaMapper;
import com.example.Nutrividas.model.Consulta;
import com.example.Nutrividas.model.Nutricionista;
import com.example.Nutrividas.model.Usuario;
import com.example.Nutrividas.repository.ConsultaRepository;
import com.example.Nutrividas.repository.NutricionistaRepository;
import com.example.Nutrividas.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public ResponseEntity<String> marcarConsulta(Long nutricionistaId, Long usuarioId, LocalDate data, LocalTime horario, String pagamento) {
        try {
            // Verifica se o nutricionista existe
            Nutricionista nutricionista = nutricionistaRepository.findById(nutricionistaId)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado"));
    
            // Verifica se o usuário existe
            Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
            // Criação da consulta
            Consulta consulta = new Consulta();
            consulta.setNutricionista(nutricionista);  // Associando o nutricionista
            consulta.setUsuario(usuario);  // Associando o usuário
            consulta.setData(data);
            consulta.setHorario(horario);
            consulta.setPagamento(pagamento);
    
            // Salvando a consulta
            consultaRepository.save(consulta);
            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta marcada com sucesso!");
        } catch (Exception e) {
            // Em caso de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao marcar a consulta: " + e.getMessage());
        }
    }

    public ResponseEntity<String> save(ConsultaDto consultaDto) {
        try {
            // Convertendo para modelo e salvando
            Consulta entity = ConsultaMapper.INSTANCE.toModel(consultaDto);
            consultaRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta salva com sucesso!");
        } catch (Exception e) {
            // Erro ao salvar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao salvar a consulta: " + e.getMessage());
        }
    }
    
    public ResponseEntity<String> deleteConsultaById(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            consultaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Consulta removida com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada!");
    }

    public List<ConsultaDto> findConsultasByUsuarioId(Long usuarioId){
        List<Consulta> consultas = consultaRepository.findByUsuarioId(usuarioId);
        return consultas.stream()
                    .map(ConsultaMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
    }

    public ResponseEntity<ConsultaDto> getConsulta(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()) {
            ConsultaDto consultaDto = ConsultaMapper.INSTANCE.toDto(consulta.get());
            return ResponseEntity.ok(consultaDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public List<ConsultaDto> findAllConsultas() {
        List<Consulta> consultas = consultaRepository.findAll(); // Buscar todas as consultas
        return consultas.stream()
                    .map(ConsultaMapper.INSTANCE::toDto) // Mapeia para DTO
                    .collect(Collectors.toList()); // Coleta a lista de DTOs
    }

    public ResponseEntity<?> updateConsultaById(Long id, ConsultaDto consultaDto) {
        Optional<Consulta> consultaExistente = consultaRepository.findById(id);
        if (consultaExistente.isPresent()) {
            try {
                Consulta consulta = consultaExistente.get();
                Consulta updatedConsulta = ConsultaMapper.INSTANCE.toModel(consultaDto);
                updatedConsulta.setId(consulta.getId()); // Preserva o ID original
                consultaRepository.save(updatedConsulta);
                return ResponseEntity.ok(ConsultaMapper.INSTANCE.toDto(updatedConsulta));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar a consulta: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada!");
    }

    public List<ConsultaDto> getHistoricoConsultas(Long usuarioId) {
        // Busca as consultas associadas ao usuário
        List<Consulta> consultas = consultaRepository.findByUsuarioId(usuarioId);

        // Mapeia as consultas para incluir o nome do nutricionista
        return consultas.stream()
                        .map(consulta -> {
                            Nutricionista nutricionista = consulta.getNutricionista(); // Obtém o nutricionista da consulta
                            String nutricionistaNome = nutricionista != null ? nutricionista.getNome() : "Desconhecido"; // Verifica se o nutricionista existe
                            ConsultaDto consultaDto = ConsultaMapper.INSTANCE.toDto(consulta);
                            consultaDto.setNutricionistaNome(nutricionistaNome); // Adiciona o nome do nutricionista ao DTO
                            return consultaDto;
                        })
                        .collect(Collectors.toList());
    }
}
