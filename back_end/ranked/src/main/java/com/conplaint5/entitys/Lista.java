package com.conplaint5.entitys;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.conplaint5.dtos.ListaDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private Long posicao;
    private boolean ativo;
    private LocalDateTime dataCriacao;
    @OneToMany(mappedBy = "lista")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Item> itens;
    @ManyToOne
    private Usuario usuario;

    public Lista (ListaDto lista){
        this.id = lista.id();
        this.nome = lista.nome();
        this.usuario = new Usuario(lista.usuario());
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    // public Lista (Lista lista){
    //     this.id = lista.getId();
    //     this.nome = lista.getNome();
    //     this.posicao = lista.getPosicao();
    //     this.ativo = lista.getAtivo();
    //     this.dataCriacao = lista.getDataCriacao();
    //     this.itens = lista.getItens();
    //     this.usuario = lista.getUsuario();
    // }
    
    public boolean getAtivo(){
        return this.ativo;
    }
}
