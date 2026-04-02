package model;

import java.util.List;

public interface IProductoDAO {
    // Acciones del menú
    // 1. Listar todos los productos
    List<Producto> obtenerProductos();

    // 2. Buscar producto por ID
    Producto obtenerProducto(int id);

    // 3. Buscar producto por categoría
    List<Producto> obtenerProductosPorCategoria(String categoria);

    // 4. Insertar nuevo producto
    Boolean crearProducto(Producto producto);

    // 5. Actualizar stock de un producto
    Boolean actualizarStockDeProducto(int id, int stock);

    // 6. Eliminar Producto
    Boolean eliminarProducto(int id);
}
