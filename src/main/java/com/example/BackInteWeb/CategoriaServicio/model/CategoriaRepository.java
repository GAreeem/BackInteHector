package com.example.BackInteWeb.CategoriaServicio.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaServicio, Long> {

    //Buscar el nombre
    boolean existsByNombre(String nombre);

    //Buscar nombre y id
    boolean existsByNombreAndIdCategoriaServicio(String nombre, Long idCategoriaServicio);
}
