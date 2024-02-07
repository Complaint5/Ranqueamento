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

import com.conplaint5.dtos.ListaDto;
import com.conplaint5.dtos.ListaPosicaoDto;
import com.conplaint5.dtos.UsuarioDto;
import com.conplaint5.entitys.Lista;
import com.conplaint5.services.ListaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lista")
public class ListaController {
    @Autowired
    private ListaService listaService;

    @PostMapping
    public ResponseEntity<ListaDto> create(@Valid @RequestBody ListaDto listaDto, UriComponentsBuilder uriBuilder) {
        ListaDto lista = this.listaService.create(listaDto);
        return ResponseEntity.created(uriBuilder.path("/lista/" + lista.id()).build().toUri()).body(lista);
    }

    @PutMapping
    public ResponseEntity<ListaDto> update(@Valid @RequestBody ListaDto listaDto) {
        ListaDto lista = this.listaService.update(listaDto);
        return lista != null ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
    }

    @PutMapping("/posicao")
    public ResponseEntity<List<ListaDto>> updatePositon(@Valid @RequestBody ListaPosicaoDto listaPosicaoDto) {
        List<ListaDto> lista = this.listaService.updatePositon(listaPosicaoDto);
        return lista == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<ListaDto>> findByUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(this.listaService.findByUsuario(usuarioDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDto> findById(@PathVariable UUID id) {
        Optional<Lista> lista = this.listaService.findById(id);
        return lista.isPresent() ? ResponseEntity.ok(new ListaDto(lista.get())) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ListaDto>> findAll() {
        return ResponseEntity.ok(this.listaService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<ListaDto> delete(@Valid @RequestBody ListaDto listaDto) {
        return this.listaService.delete(listaDto) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
