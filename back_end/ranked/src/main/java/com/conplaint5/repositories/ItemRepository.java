package com.conplaint5.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.conplaint5.entitys.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
    List<Item> findAllByListaId(UUID id);

    @Query(value = "SELECT COUNT(*) FROM Item WHERE LISTA_ID = ?1", nativeQuery = true)
    long contByLista_id(UUID id);

    List<Item> findAllItensByAtivoTrueAndLista_Id(UUID id);

    List<Item> findAllByAtivoTrue();

    List<Item> findByLista_idAndPosicaoBetween(UUID id, long valor1, long valor2);
}
