package com.example.Nutrividas.controller;

import com.example.Nutrividas.dto.ConsultaDto;
import com.example.Nutrividas.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    // Endpoint para marcar consulta
    @PostMapping(value = "/marcar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> marcarConsulta(@RequestParam Long nutricionistaId,
                                                 @RequestParam Long usuarioId,
                                                 @RequestParam LocalDate data,
                                                 @RequestParam LocalTime horario,
                                                 @RequestParam String pagamento) {
        try {
            // Verifica se o ID do usuário é válido
            if (usuarioId == null || usuarioId <= 0) {
                return ResponseEntity.badRequest().body("ID de usuário inválido. Por favor, forneça um ID válido.");
            }
    
            service.marcarConsulta(nutricionistaId, usuarioId, data, horario, pagamento);
            return ResponseEntity.ok("Consulta agendada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao agendar consulta: " + e.getMessage());
        }
    }
    

    // Endpoint para obter consulta por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultaDto> getConsulta(@PathVariable("id") Long id) {
        return service.getConsulta(id); // Retorna diretamente a resposta do serviço
    }

    // Endpoint para deletar consulta por ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteConsulta(@PathVariable Long id) {
        try {
            service.deleteConsultaById(id);
            return ResponseEntity.ok("Consulta deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar consulta: " + e.getMessage());
        }
    }

    // Endpoint para obter todas as consultas
    @GetMapping("/all")
    public ResponseEntity<List<ConsultaDto>> getAllConsultas() {
        List<ConsultaDto> consultas = service.findAllConsultas();
        return ResponseEntity.ok(consultas);
    }


    // Endpoint para atualizar consulta por ID
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        try {
            service.updateConsultaById(id, consultaDto);
            return ResponseEntity.ok("Consulta atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    @GetMapping("/historico")
    public ResponseEntity<List<ConsultaDto>> obterHistoricoConsultas(@RequestParam Long usuarioId) {
        List<ConsultaDto> consultas = service.getHistoricoConsultas(usuarioId);
        return ResponseEntity.ok(consultas);
    }

}
