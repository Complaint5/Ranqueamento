package com.conplaint5.dtos;

import java.util.UUID;

import com.conplaint5.entitys.Lista;

import jakarta.validation.constraints.NotBlank;

public record ListaDto(
    UUID id,
    @NotBlank String nome,
    Long posicao,
    UsuarioDto usuario
) {
    public ListaDto(Lista lista){
        this(lista.getId(), lista.getNome(), lista.getPosicao(), new UsuarioDto(lista.getUsuario()));
    }
}
