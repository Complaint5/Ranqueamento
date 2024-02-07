package com.conplaint5.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record ListaPosicaoDto(
    UUID id,
    @NotBlank String nome,
    Long posicaoInicial,
    Long posicaoFinal,
    UsuarioDto usuario
) {
}
