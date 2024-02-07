package com.conplaint5.dtos;

import java.util.UUID;

import com.conplaint5.entitys.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
    UUID id,
    @NotBlank String nome
    ) {
    public UsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome());
    }
}
