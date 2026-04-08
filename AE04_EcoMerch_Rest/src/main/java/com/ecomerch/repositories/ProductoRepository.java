package com.ecomerch.repositories;

import com.ecomerch.models.CategoriaModel;
import com.ecomerch.models.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel,Long> {

    // Obtener productos por Id de Categoría
    List<ProductoModel> findByCategoriaId(Long categoriaId);

    // Obtener productos por Nombre de Categoría
    List<ProductoModel> findByCategoriaNombre(String categoriaNombre);

    // Obtener Productos con precio igual
    List<ProductoModel> findByPrecio(Double precio);

    // Obtener Productos con precio superior
    List<ProductoModel> findByPrecioGreaterThan(Double precio);

    // Obtener Productos con precio inferior
    List<ProductoModel> findByPrecioLessThan(Double precio);

    // Existe Producto con mismo Nombre
    boolean existsByNombre(String nombre);
}
