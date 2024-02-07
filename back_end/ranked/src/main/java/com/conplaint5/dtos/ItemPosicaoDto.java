package com.conplaint5.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record ItemPosicaoDto(
    UUID id,
    @NotBlank String nome,
    @NotBlank String descricao,
    Long posicaoInicial,
    Long posicaoFinal,
    ListaDto lista
) {
}
