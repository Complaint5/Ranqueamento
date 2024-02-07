package com.conplaint5.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.conplaint5.dtos.UsuarioDto;
import com.conplaint5.entitys.Usuario;
import com.conplaint5.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@Valid @RequestBody UsuarioDto usuarioDto,
            UriComponentsBuilder uriBuilder) {
        UsuarioDto usuario = usuarioService.create(usuarioDto);
        return ResponseEntity.created(uriBuilder.path("/usuario/" + usuario.id()).build().toUri()).body(usuario);
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> update(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usaurio = usuarioService.update(usuarioDto);
        return usaurio != null ? ResponseEntity.ok(new UsuarioDto(usaurio)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok(new UsuarioDto(usuario.get()))
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<UsuarioDto> delete(@Valid @RequestBody UsuarioDto usuario) {
        return usuarioService.delete(usuario) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
