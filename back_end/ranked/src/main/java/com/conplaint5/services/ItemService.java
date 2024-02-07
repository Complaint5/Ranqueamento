package com.conplaint5.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conplaint5.dtos.ItemDto;
import com.conplaint5.dtos.ItemPosicaoDto;
import com.conplaint5.dtos.ListaDto;
import com.conplaint5.entitys.Item;
import com.conplaint5.entitys.Lista;
import com.conplaint5.repositories.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ListaService listaService;

    @Transactional
    public ItemDto create(ItemDto itemDto) {
        Optional<Lista> lista = this.listaService.findById(itemDto.lista().id());

        if (lista.isPresent()) {
            Item item = new Item(itemDto);
            item.setLista(lista.get());
            item.setPosicao(this.itemRepository.contByLista_id(lista.get().getId()) + 1);
            return new ItemDto(this.itemRepository.save(item));
        }
        
        return null;
    }

    @Transactional
    public ItemDto update(ItemDto itemDto) {
        Optional<Item> itemDb = this.itemRepository.findById(itemDto.id());

        if (itemDb.isPresent()) {
            Item item = itemDb.get();
            item.setNome(itemDto.nome());
            item.setDescricao(itemDto.descricao());
            return new ItemDto(this.itemRepository.save(item));
        }

        return null;
    }

    public List<ItemDto> updatePositon(ItemPosicaoDto itemPosicaoDto) {
        Optional<Item> itemTroca = this.findById(itemPosicaoDto.id());
        Optional<Lista> listaTroca = this.listaService.findById(itemPosicaoDto.lista().id());

        if (listaTroca.isPresent()) {
            long contagemItens = this.itemRepository.contByLista_id(listaTroca.get().getId());

            if (itemTroca.isPresent()
                    && itemTroca.get().getLista().getId().equals(listaTroca.get().getId())
                    && !(itemPosicaoDto.posicaoFinal() > contagemItens)
                    && !(itemPosicaoDto.posicaoInicial() > contagemItens)
                    && !(itemPosicaoDto.posicaoFinal() < 1)
                    && !(itemPosicaoDto.posicaoInicial() < 1)
                    && itemPosicaoDto.posicaoInicial() == itemTroca.get().getPosicao()
                    && itemPosicaoDto.posicaoInicial() != itemPosicaoDto.posicaoFinal()) {

                List<Item> itens = this.itemRepository.findByLista_idAndPosicaoBetween(
                        listaTroca.get().getId(),
                        itemPosicaoDto.posicaoInicial() > itemPosicaoDto.posicaoFinal()
                                ? itemPosicaoDto.posicaoFinal() : itemPosicaoDto.posicaoInicial(),
                        itemPosicaoDto.posicaoInicial() < itemPosicaoDto.posicaoFinal()
                                ? itemPosicaoDto.posicaoFinal() : itemPosicaoDto.posicaoInicial());

                itens.remove(itemTroca.get());

                if (itemPosicaoDto.posicaoInicial() > itemPosicaoDto.posicaoFinal()) {
                    itens.addFirst(itemTroca.get());
                } else {
                    itens.addLast(itemTroca.get());
                }

                Long j = itemPosicaoDto.posicaoInicial() > itemPosicaoDto.posicaoFinal()
                        ? itemPosicaoDto.posicaoFinal() : itemPosicaoDto.posicaoInicial();

                for (int i = 0; i < itens.size(); i++) {
                    itens.get(i).setPosicao(j++);
                    this.updateItemPosition(itens.get(i));
                }

                return itens.stream().map(itemm -> new ItemDto(itemm)).toList();
            }
        }

        return null;
    }

    public List<ItemDto> findAll() {
        return itemRepository.findAllByAtivoTrue().stream().map(item -> new ItemDto(item)).toList();
    }

    public Optional<Item> findById(UUID id) {
        return itemRepository.findById(id);
    }

    public List<ItemDto> findByLista(ListaDto listaDto) {
        return this.itemRepository.findAllItensByAtivoTrueAndLista_Id(listaDto.id()).stream()
                .map(item -> new ItemDto(item)).toList();
    }

    @Transactional
    public boolean delete(ItemDto itemDto) {
        Optional<Item> itemDb = this.findById(itemDto.id());

        if (itemDb.isPresent()) {
            Item item = itemDb.get();
            item.setAtivo(false);
            this.itemRepository.save(item);
            return true;
        }

        return false;
    }

    @Transactional
    private void updateItemPosition(Item item) {
        this.itemRepository.save(item);
    }
}
