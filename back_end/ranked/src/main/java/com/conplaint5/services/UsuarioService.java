package com.conplaint5.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conplaint5.dtos.UsuarioDto;
import com.conplaint5.entitys.Usuario;
import com.conplaint5.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDto create(UsuarioDto usuarioDto) {
        return new UsuarioDto(this.usuarioRepository.save(new Usuario(usuarioDto)));
    }

    @Transactional
    public Usuario update(UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioBd = this.findById(usuarioDto.id());
        if (usuarioBd.isPresent()) {
            Usuario novoUsuario = usuarioBd.get();
            novoUsuario.setNome(usuarioDto.nome());
            return this.usuarioRepository.save(novoUsuario);
        }
        return null;
    }

    public List<UsuarioDto> findAll() {
        return this.usuarioRepository.findAllByAtivoTrue().stream().map(usuario -> new UsuarioDto(usuario)).toList();
    }

    public Optional<Usuario> findById(UUID id) {
        return this.usuarioRepository.findByAtivoTrueAndId(id);
    }

    @Transactional
    public boolean delete(UsuarioDto usuarioDto) {
        Optional<Usuario> usuario = this.findById(usuarioDto.id());
        if (usuario.isPresent()) {
            Usuario usuario2 = usuario.get();
            usuario2.setAtivo(false);
            this.usuarioRepository.save(usuario2);
            return true;
        }
        return false;
    }
}
