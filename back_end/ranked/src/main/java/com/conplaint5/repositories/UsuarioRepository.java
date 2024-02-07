package com.conplaint5.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conplaint5.entitys.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    List<Usuario> findAllByAtivoTrue();

    Optional<Usuario> findByAtivoTrueAndId(UUID id);
}
