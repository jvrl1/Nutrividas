package com.example.Nutrividas.controller;

import com.example.Nutrividas.dto.UsuarioDto;
import com.example.Nutrividas.service.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping(value = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody UsuarioDto usuarioDto){
        service.save(usuarioDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsuario(@PathVariable("id") Long id){
        return service.getUsuario(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") Long id) {
        return service.deleteUsuarioById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<UsuarioDto> usuarios = service.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUsuario(@PathVariable("id") Long id, @RequestBody UsuarioDto usuarioDto) {
        return service.updateUsuarioById(id, usuarioDto);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UsuarioDto usuarioDto) {
        return service.login(usuarioDto);
    }
}
