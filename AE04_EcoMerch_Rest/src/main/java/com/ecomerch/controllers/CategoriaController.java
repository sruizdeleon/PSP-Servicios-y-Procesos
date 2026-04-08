package com.ecomerch.controllers;

import com.ecomerch.models.CategoriaModel;
import com.ecomerch.models.ProductoModel;
import com.ecomerch.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // [GET] Todas las categorías
    @GetMapping
    public ResponseEntity<?> findAllCategorias() {
        List<CategoriaModel> categoriasEncontradas = categoriaRepository.findAll();
        if (categoriasEncontradas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                    "message", "No se han encontrado productos"
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "message", "Categorias encontradas",
                "data", categoriasEncontradas
        ));
    }

    // [POST] Insertar Categoria
    @PostMapping
    public ResponseEntity<?> createCategoria(@RequestBody CategoriaModel categoria) {
        if(categoria.getNombre() == null || categoria.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "El nombre es obligatorio"
            ));
        }

        if(categoriaRepository.existsByNombre(categoria.getNombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "message", "La categoria ya existe"
            ));
        }

        CategoriaModel categoriaCreada = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Categoria creada correctamente",
                "data", categoriaCreada
        ));
    }

    // [PUT] Modificar categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable("id") Long id, @RequestBody CategoriaModel categoria) {
        CategoriaModel categoriaAModificar = categoriaRepository.findById(id).orElse(null);
        if (categoriaAModificar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existe la catgeoria con id: " + id
            ));
        } else {
            categoriaAModificar.setNombre(categoria.getNombre());
            return ResponseEntity.ok().body(Map.of(
                    "message", "Categoria actualizada correctamente",
                    "data", categoriaRepository.save(categoriaAModificar)
            ));
        }
    }

    // [DELETE] Eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable("id")Long id) {
        CategoriaModel categoriaAEliminar = categoriaRepository.findById(id).orElse(null);
        if (categoriaAEliminar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existe la categoria con id: " + id
            ));
        } else {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Categoria eliminada correctamente",
                    "data", categoriaAEliminar
            ));
        }
    }


}
