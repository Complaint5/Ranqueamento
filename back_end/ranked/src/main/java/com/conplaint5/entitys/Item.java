package com.conplaint5.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import com.conplaint5.dtos.ItemDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String descricao;
    private long posicao;
    private boolean ativo;
    private LocalDateTime dataCriacao;
    @ManyToOne
    private Lista lista;

    public Item(ItemDto itemDto){
        this.nome = itemDto.nome();
        this.descricao = itemDto.descricao();
        this.posicao = itemDto.posicao();
        this.lista = new Lista(itemDto.lista());
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }
    
    public boolean getAtivo(){
        return this.ativo;
    }
}
