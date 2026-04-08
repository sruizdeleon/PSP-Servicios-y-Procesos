package com.ecomerch.controllers;

import com.ecomerch.models.ProductoModel;
import com.ecomerch.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // [GET] Todos los productos
    @GetMapping
    public ResponseEntity<?> getAllProductos(
            @RequestParam(value = "precio", required = false) Double precio,
            @RequestParam(value = "precioMayor", required = false) Double precioMayorQue,
            @RequestParam(value = "precioMenor", required = false) Double precioMenorQue
    ) {
        // Obtenemos los productos
        List<ProductoModel> productos;
        String mensaje;

        if (precio != null) {
            productos = productoRepository.findByPrecio(precio);
            mensaje = "Productos encontrados con precio igual a: " + precio;
        } else if (precioMayorQue != null) {
            productos = productoRepository.findByPrecioGreaterThan(precioMayorQue);
            mensaje = "Productos encontrados con precio mayor que: " + precioMayorQue;
        } else if (precioMenorQue != null) {
            productos = productoRepository.findByPrecioLessThan(precioMenorQue);
            mensaje = "Productos encontrados con precio menor que: " + precioMenorQue;
        } else {
            productos = productoRepository.findAll();
            mensaje = "Productos";
        }

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No se han encontrado productos"));
        }

        return ResponseEntity.ok().body(Map.of(
        "message", mensaje,
        "data", productos
        ));
    }

    // [GET] Producto por Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(Map.of(
                        "message", "Producto encontrado",
                        "data", producto
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "No existe el producto con id: " + id
        )));
    }

    // [GET] Producto por Id de Categoria
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> getProductoByCategoriaId(@PathVariable Long categoriaId) {
        List<ProductoModel> productosEncontrados = productoRepository.findByCategoriaId(categoriaId);
        if (productosEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existen productos con un id de categoria: " + categoriaId
            ));
        } else {
            return ResponseEntity.ok().body(Map.of(
                    "message", "Productos encontrados con un id de categoria: " + categoriaId,
                    "data", productosEncontrados
            ));
        }
    }

    // [GET] Producto por Nombre de Categoria
    @GetMapping("/categoria")
    public ResponseEntity<?> getProductoByCategoriaId(@RequestParam("nombre") String categoriaNombre) {
        List<ProductoModel> productosEncontrados = productoRepository.findByCategoriaNombre(categoriaNombre);
        if (productosEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existen productos con nombre de categoria: " + categoriaNombre
            ));
        } else {
            return ResponseEntity.ok().body(Map.of(
                    "message", "Productos encontrados con nombre de categoria: " + categoriaNombre,
                    "data", productosEncontrados
            ));
        }
    }


    // [POST] Insertar Producto
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductoModel producto) {
        if(producto.getNombre() == null || producto.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of(
                "message", "El nombre es obligatorio"
            ));
        }

        if(producto.getPrecio() < 0 || producto.getStock() < 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "El precio y stock no puede ser negativos"
            ));
        }

        if(producto.getCategoria() == null){
            return ResponseEntity.badRequest().body(Map.of(
                "message", "La categoria es obligatoria"
            ));
        }

        if(productoRepository.existsByNombre(producto.getNombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "message", "El producto ya existe"
            ));
        }

        ProductoModel productoCreado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Producto creado correctamente",
            "data", productoCreado
        ));
    }

    // [PUT] Modificar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Long id, @RequestBody ProductoModel producto) {
        ProductoModel productoAModificar = productoRepository.findById(id).orElse(null);
        if (productoAModificar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existe el producto con id: " + id
            ));
        } else {
            if(producto.getNombre() == null || producto.getNombre().isEmpty()){
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "El nombre es obligatorio"
                ));
            }

            if(producto.getPrecio() < 0 || producto.getStock() < 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "El precio y stock no puede ser negativos"
                ));
            }

            if(producto.getCategoria() == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "La categoria es obligatoria"
                ));
            }

            // Si pasa las validaciones actualizamos el producto
            productoAModificar.setNombre(producto.getNombre());
            productoAModificar.setPrecio(producto.getPrecio());
            productoAModificar.setStock(producto.getStock());
            productoAModificar.setCategoria(producto.getCategoria());
            return ResponseEntity.ok().body(Map.of(
                    "message", "Producto actualizado correctamente",
                    "data", productoRepository.save(productoAModificar)
            ));
        }
    }

    // [DELETE] Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id")Long id) {
        ProductoModel productoAEliminar = productoRepository.findById(id).orElse(null);
        if (productoAEliminar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "No existe el producto con id: " + id
            ));
        } else {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Producto eliminado correctamente",
                    "data", productoAEliminar
            ));
        }
    }

}
