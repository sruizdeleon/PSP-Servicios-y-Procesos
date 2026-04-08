package com.ecomerch.repositories;

import com.ecomerch.models.CategoriaModel;
import com.ecomerch.models.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
    // Existe Categoria con mismo Nombre
    Boolean existsByNombre(String nombre);
}
