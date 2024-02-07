package com.conplaint5.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conplaint5.dtos.ListaDto;
import com.conplaint5.dtos.ListaPosicaoDto;
import com.conplaint5.dtos.UsuarioDto;
import com.conplaint5.entitys.Lista;
import com.conplaint5.entitys.Usuario;
import com.conplaint5.repositories.ListaRepository;

import jakarta.transaction.Transactional;

@Service
public class ListaService {
    @Autowired
    private ListaRepository listaRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public ListaDto create(ListaDto listaDto) {
        Optional<Usuario> usuario = this.usuarioService.findById(listaDto.usuario().id());

        if (usuario.isPresent()) {
            Lista lista = new Lista(listaDto);
            lista.setUsuario(usuario.get());
            lista.setPosicao(this.listaRepository.contByUsuario_id(usuario.get().getId()) + 1);
            return new ListaDto(this.listaRepository.save(lista));
        }
        
        return null;
    }

    @Transactional
    public ListaDto update(ListaDto listadDto) {
        Optional<Lista> listaDb = findById(listadDto.id());

        if (listaDb.isPresent()) {
            Lista lista = listaDb.get();
            lista.setNome(listadDto.nome());
            return new ListaDto(this.listaRepository.save(lista));
        }

        return null;
    }

    public List<ListaDto> updatePositon(ListaPosicaoDto listaPosicaoDto) {
        Optional<Lista> listaTroca = this.findById(listaPosicaoDto.id());
        Optional<Usuario> usuarioTroca = this.usuarioService.findById(listaPosicaoDto.usuario().id());

        if (usuarioTroca.isPresent()) {
            Long contagemListas = this.listaRepository.contByUsuario_id(usuarioTroca.get().getId());

            if (listaTroca.isPresent()
                    && listaTroca.get().getUsuario().getId().equals(usuarioTroca.get().getId())
                    && !(listaPosicaoDto.posicaoFinal() > contagemListas)
                    && !(listaPosicaoDto.posicaoInicial() > contagemListas)
                    && !(listaPosicaoDto.posicaoFinal() < 1)
                    && !(listaPosicaoDto.posicaoInicial() < 1)
                    && listaPosicaoDto.posicaoInicial() == listaTroca.get().getPosicao()
                    && listaPosicaoDto.posicaoInicial() != listaPosicaoDto.posicaoFinal()) {

                List<Lista> lista = this.listaRepository.findByUsuario_idAndPosicaoBetween(
                        usuarioTroca.get().getId(),
                        listaPosicaoDto.posicaoInicial() >= listaPosicaoDto.posicaoFinal()
                                ? listaPosicaoDto.posicaoFinal() : listaPosicaoDto.posicaoInicial(),
                        listaPosicaoDto.posicaoInicial() <= listaPosicaoDto.posicaoFinal()
                                ? listaPosicaoDto.posicaoFinal() : listaPosicaoDto.posicaoInicial());

                lista.remove(listaTroca.get());

                if (listaPosicaoDto.posicaoInicial() >= listaPosicaoDto.posicaoFinal()) {
                    lista.addFirst(listaTroca.get());
                } else {
                    lista.addLast(listaTroca.get());
                }

                Long j = listaPosicaoDto.posicaoInicial() > listaPosicaoDto.posicaoFinal()
                        ? listaPosicaoDto.posicaoFinal() : listaPosicaoDto.posicaoInicial();

                for (int i = 0; i < lista.size(); i++) {
                    lista.get(i).setPosicao(j++);
                    this.updateListaPosition(lista.get(i));
                }

                return lista.stream().map(listaa -> new ListaDto(listaa)).toList();
            }
        }

        return null;
    }

    public List<ListaDto> findAll() {
        return this.listaRepository.findAllByAtivoTrue().stream().map(lista -> new ListaDto(lista)).toList();
    }

    public Optional<Lista> findById(UUID id) {
        return this.listaRepository.findByAtivoTrueAndId(id);
    }

    public List<ListaDto> findByUsuario(UsuarioDto usuarioDto) {
        return this.listaRepository.findAllListasByAtivoTrueAndUsuario_id(usuarioDto.id()).stream()
                .map(lista -> new ListaDto(lista)).toList();
    }

    @Transactional
    public boolean delete(ListaDto listaDto) {
        Optional<Lista> listaDb = this.findById(listaDto.id());

        if (listaDb.isPresent()) {
            Lista lista = listaDb.get();
            lista.setAtivo(false);
            this.listaRepository.save(lista);
            return true;
        }

        return false;
    }

    @Transactional
    private void updateListaPosition(Lista lista) {
        this.listaRepository.save(lista);
    }
}
