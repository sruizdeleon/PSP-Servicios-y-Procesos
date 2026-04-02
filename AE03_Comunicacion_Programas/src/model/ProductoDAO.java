package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    // Método para el mapeo de productos
    public Producto mapearProducto(ResultSet rs) {
        try {
            // Mapeamos los atributos
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String categoria = rs.getString("categoria");
            Double precio = rs.getDouble("precio");
            int stock = rs.getInt("stock");

            // Creamos el producto
            Producto producto = new Producto(id, nombre, categoria, precio, stock);

            // Devolvemos el producto
            return producto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1. Listar todos los productos
    public List<Producto> obtenerProductos() {
        try {
            // Creamos la conexión
            Connection conexion = DBConnection.getConnection();

            // Creamos la query
            String query = "SELECT * FROM productos";

            // Creamos el Statement
            PreparedStatement ps = conexion.prepareStatement(query);

            // Ejecutamos el statement y guardamos su respuesta
            ResultSet rs = ps.executeQuery();

            // Creamos una lista para copiar los productos recibidos en el ResultSet
            List<Producto> productos = new ArrayList<>();

            // Recorremos el result statement y lo agregamos a nuestro array
            while (rs.next()) {
                Producto p = mapearProducto(rs);
                productos.add(p);
            }

            // Devolvemos nuestro array de productos
            return productos;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    // 2. Buscar producto por ID
    public Producto obtenerProducto(int id) {
        try {
            // Creamos la conexión
            Connection conexion = DBConnection.getConnection();

            // Creamos la query
            String query = "SELECT * FROM productos WHERE id = ?";

            // Creamos el Statement
            PreparedStatement ps = conexion.prepareStatement(query);

            // Añadimos el parametro id al Statement
            ps.setInt(1, id);

            // Ejecutamos el statement y guardamos su respuesta
            ResultSet rs = ps.executeQuery();

            // Si el result statement tiene algún registro
            if (rs.next()) {
                // Mapeamos y devolvemos el producto
                return mapearProducto(rs);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    // 3. Buscar producto por categoría
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        try {
            // Creamos la conexión
            Connection conexion = DBConnection.getConnection();

            // Creamos la query
            String query = "SELECT * FROM productos Where categoria = ?";

            // Creamos el Statement
            PreparedStatement ps = conexion.prepareStatement(query);

            // Asignamos el parametro de categoría al statement
            ps.setString(1, categoria);

            // Ejecutamos el statement y guardamos su respuesta
            ResultSet rs = ps.executeQuery();

            // Creamos una lista para copiar los productos recibidos en el ResultSet
            List<Producto> productos = new ArrayList<>();

            // Recorremos el result statement y lo agregamos a nuestro array
            while (rs.next()) {
                Producto p = mapearProducto(rs);
                productos.add(p);
            }

            // Devolvemos nuestro array de productos
            return productos;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    // 4. Insertar nuevo producto
    public synchronized Boolean crearProducto(Producto producto) {
        try {
            // Creamos la conexión a la BBDD
            Connection conexion = DBConnection.getConnection();

            // Creamos la Query
            String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

            // Creamos la consulta preparada
            PreparedStatement ps = conexion.prepareStatement(sql);

            // Asignamos los valores a cada ?
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());

            // Ejecutamos la consulta preparada y guardamos su respuesta
            int filas = ps.executeUpdate();

            // Devolvemos el resultado
            if (filas > 0) {
                return true;
            }  else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("ERROR - "  + e.getMessage());
            return false;
        }
    }

    // 5. Actualizar stock de un producto
    public synchronized Boolean actualizarStockDeProducto(int id, int stock) {
        try {

            // Creamos la conexión a BBDD
            Connection conexion = DBConnection.getConnection();

            // Creamos la Query
            String sql = "UPDATE productos SET stock = ? WHERE id = ?";

            // Creamos la consulta preparada
            PreparedStatement ps = conexion.prepareStatement(sql);

            // Asignamos los valores a cada ?
            ps.setInt(1, stock);
            ps.setInt(2, id);

            // Ejecutamos la consulta preparada y guardamos su respuesta
            int filas = ps.executeUpdate();

            // Devolvemos el resultado
            if (filas > 0) {
                return true;
            }  else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("ERROR - "  + e.getMessage());
            return false;
        }
    }

    // 6. Eliminar Producto
    public synchronized Boolean eliminarProducto(int id) {
        try {
            // Creamos la conexión
            Connection conexion = DBConnection.getConnection();

            // Creamos la query
            String sql = "DELETE FROM productos WHERE id = ?";

            // Creamos el stantement con la consulta
            PreparedStatement ps = conexion.prepareStatement(sql);

            // Añadimos el id del parámetro a nuestro statement
            ps.setInt(1, id);

            // Ejecutamos la consulta
            int filas = ps.executeUpdate();

            // Devolvemos el resultado
            if (filas > 0) {
                return true;
            } else  {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("ERROR - "  + e.getMessage());
            return false;
        }
    }
}
