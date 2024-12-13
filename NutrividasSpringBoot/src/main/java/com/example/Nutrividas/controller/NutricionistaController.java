package com.example.Nutrividas.controller;

import com.example.Nutrividas.dto.NutricionistaDto;
import com.example.Nutrividas.service.NutricionistaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nutricionista")
public class NutricionistaController {

    @Autowired
    NutricionistaService service;

    @PostMapping(value = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody NutricionistaDto nutricionistaDto) {
        service.save(nutricionistaDto);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNutricionista(@PathVariable("id") Long id) {
        return service.getNutricionista(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteNutricionista(@PathVariable("id") Long id) {
        return service.deleteNutricionistaById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NutricionistaDto>> getAllNutricionistas() {
        List<NutricionistaDto> nutricionistas = service.findAllNutricionistas();
        return ResponseEntity.ok(nutricionistas);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> updateNutricionista(@PathVariable("id") Long id, @RequestBody NutricionistaDto nutricionistaDto) {
    return service.updateNutricionistaById(id, nutricionistaDto);
}
}
