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

import com.conplaint5.dtos.ItemDto;
import com.conplaint5.dtos.ItemPosicaoDto;
import com.conplaint5.dtos.ListaDto;
import com.conplaint5.entitys.Item;
import com.conplaint5.services.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> creat(@RequestBody ItemDto itemDto, UriComponentsBuilder uriBuilder) {
        ItemDto item = this.itemService.create(itemDto);
        return ResponseEntity.created(uriBuilder.path("/item/" + item.id()).build().toUri()).body(item);
    }

    @PutMapping
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto itemDto) {
        ItemDto item = this.itemService.update(itemDto);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PutMapping("/posicao")
    public ResponseEntity<List<ItemDto>> updatePosition(@RequestBody ItemPosicaoDto itemPosicaoDto) {
        List<ItemDto> itens = this.itemService.updatePositon(itemPosicaoDto);
        return itens == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(itens);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> findAll() {
        return ResponseEntity.ok(this.itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> findById(@PathVariable UUID id) {
        Optional<Item> item = this.itemService.findById(id);
        return item.isPresent() ? ResponseEntity.ok(new ItemDto(item.get())) : ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ItemDto>> findByLista(@RequestBody ListaDto listaDto) {
        return ResponseEntity.ok(this.itemService.findByLista(listaDto));
    }

    @DeleteMapping
    public ResponseEntity<ItemDto> delete(@RequestBody ItemDto itemDto) {
        return this.itemService.delete(itemDto) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
