package server;

import model.Producto;
import model.ProductoDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteHandler extends Thread {
    private Socket socket;
    private ProductoDAO productoDAO;

    // Constructor
    public ClienteHandler(Socket socket, ProductoDAO productoDAO) {
        super();
        this.socket = socket;
        this.productoDAO = productoDAO;
        this.start();
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            boolean conectado = true;
            while (conectado) {
                // Esperamos la opción del cliente
                int opcion = in.readInt();
                System.out.println("[INFO] Petición: " + opcion);

                switch (opcion) {
                    case 1: // Obtener todos los productos
                        out.writeObject(productoDAO.obtenerProductos());
                        out.flush();
                        break;
                    case 2: // Buscar porducto por ID
                        int idProductoABuscar = in.readInt(); // Obtenemos el id del Producto
                        out.writeObject(productoDAO.obtenerProducto(idProductoABuscar));
                        out.flush();
                        break;
                    case 3: // Buscar producto por categoria
                        String cartegoria = in.readUTF(); // Obtenemos el id del Producto
                        out.writeObject(productoDAO.obtenerProductosPorCategoria(cartegoria));
                        out.flush();
                        break;
                    case 4: // Crear porducto nuevo
                        Producto p = (Producto) in.readObject(); // Obtenemos el producto a crear
                        out.writeBoolean(productoDAO.crearProducto(p));
                        out.flush();
                        break;
                    case 5: // Actualizar stock del producto
                        int idProductoAActualizar = in.readInt(); // Obtenemos el id del Producto
                        int stock = in.readInt(); // Obtenemos el stock a actualizar
                        out.writeBoolean(productoDAO.actualizarStockDeProducto(idProductoAActualizar, stock));
                        out.flush();
                        break;
                    case 6: // Eliminar producto
                        int idProductoAEliminar = in.readInt(); // Obtenemos el id del Producto
                        out.writeBoolean(productoDAO.eliminarProducto(idProductoAEliminar));
                        out.flush();
                        break;
                    case 7:
                        // Cerramos la conexión
                        conectado = false;
                        System.out.println("[Info] Cliente cierra conexión.");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR - " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - " + e.getMessage());
        }
    }
}