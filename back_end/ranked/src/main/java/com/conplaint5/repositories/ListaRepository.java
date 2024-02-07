package com.conplaint5.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.conplaint5.entitys.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, UUID> {
    List<Lista> findByUsuario_idAndPosicaoBetween(UUID id, long valor1, long valor2);

    @Query(value = "SELECT COUNT(*) FROM Lista WHERE USUARIO_ID = ?1", nativeQuery = true)
    long contByUsuario_id(UUID id);

    Optional<Lista> findByAtivoTrueAndId(UUID id);

    List<Lista> findAllByAtivoTrue();

    List<Lista> findAllListasByAtivoTrueAndUsuario_id(UUID id);
}
