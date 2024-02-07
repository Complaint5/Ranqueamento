package com.conplaint5.entitys;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.conplaint5.dtos.UsuarioDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @OneToMany(mappedBy = "usuario")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Lista> listas;
    private boolean ativo;
    private LocalDateTime dataCriacao;

    public Usuario(UsuarioDto usuarioDto) {
        this.id = usuarioDto.id();
        this.nome = usuarioDto.nome();
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public boolean getAtivo() {
        return this.ativo;
    }
}
