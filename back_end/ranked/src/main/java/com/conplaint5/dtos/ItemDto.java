package com.conplaint5.dtos;

import java.util.UUID;

import com.conplaint5.entitys.Item;

import jakarta.validation.constraints.NotBlank;

public record ItemDto(
    UUID id,
    @NotBlank String nome,
    @NotBlank String descricao,
    long posicao,
    ListaDto lista
) {
    public ItemDto (Item item){
        this(item.getId(), item.getNome(), item.getDescricao(), item.getPosicao(), new ListaDto(item.getLista()));
    }
}
