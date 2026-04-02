package client;

import model.Producto;
import utils.Utilidades;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void showMenu() {
        System.out.println("=================== MENÚ DE OPCIONES =================");
        System.out.println("|= 1. Listar todos los productos.");
        System.out.println("|= 2. Buscar un producto por ID");
        System.out.println("|= 3. Buscar un producto por categorías.");
        System.out.println("|= 4. Crear un nuevo producto.");
        System.out.println("|= 5. Actualizar stock de un producto existente.");
        System.out.println("|= 6. Eliminar un producto por ID");
        System.out.println("|= 7. Salir.");
        System.out.println("|===================================================== ");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Creamos la conexión
            Socket socket = new Socket(Utilidades.DIRECCION, Utilidades.PUERTO);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

            // Creamos objeto utils para usar todas sus funciones
            Utilidades utils = new Utilidades();
            Boolean programaAbierto = true;

            // Iniciamos el bucle del programa
            while(programaAbierto) {
                showMenu(); // Mostramos el menú de opciones
                int opcion;
                do {
                    // Recogemos la opción elegida
                    opcion = utils.leerEnteroPositivo("Selecciona una opción: ");
                    // Si está fuera de las opciones lanzamos error
                    if(opcion > 7 || opcion < 1 ) {
                        System.out.println("ERROR - Opción fuera de rango.");
                        continue;
                    }
                    break;
                } while(true);

                // Según la opción elegida lanzamos la petición al servidor
                switch (opcion) {
                    case 1: // Mostrar todos los productos
                        // Petición
                        os.writeInt(opcion);
                        os.flush();
                        // Respuesta
                        List<Producto> productos = null;
                        productos = (List<Producto>) is.readObject();
                        if(productos.isEmpty()) {
                            System.out.println("\nRESPUESTA: Tabla productos vacía.\n");
                        } else  {
                            utils.mostrarProductos(productos);
                        }
                        break;
                    case 2: // Mostrar producto por Id
                        // Pedimos el Id
                        int idProducto = utils.leerEnteroPositivo("Selecciona el ID del producto: ");
                        // Petición
                        os.writeInt(opcion);
                        os.writeInt(idProducto);
                        os.flush();
                        // Respuesta
                        Producto producto = (Producto) is.readObject();
                        if(producto == null) {
                            System.out.println("No existe el producto.");
                        } else {
                            System.out.println("\n======================= PRODUCTO =======================");
                            System.out.println(producto.toString());
                        }
                        break;

                    case 3: // Buscar productos por categoría
                        // Pedimos la categoría
                        String categoria = utils.leerTexto("Escribe la categoría por la que filtrar: ");
                        // Petición
                        os.writeInt(opcion);
                        os.writeUTF(categoria);
                        os.flush();
                        // Respuesta
                        List<Producto> productosPorCategoria = null;
                        productosPorCategoria = (List<Producto>) is.readObject();
                        if(productosPorCategoria.isEmpty()) {
                            System.out.println("\nRESPUESTA: No existen productos filtrados por esa categoría.\n");
                        } else  {
                            utils.mostrarProductos(productosPorCategoria);
                        }
                        break;

                    case 4: // Crear producto
                        // Solicitamos los datos del producto
                        System.out.println("Introduce los datos del nuevo producto:");
                        Producto productoACrear = utils.recogerDatosProducto();
                        // Petición
                        os.writeInt(opcion);
                        os.writeObject(productoACrear);
                        os.flush();
                        // Respuesta
                        Boolean productoCreado = is.readBoolean();
                        if(productoCreado) {
                            System.out.println("\nRESPUESTA: Producto creado con éxito.\n");
                        } else {
                            System.out.println("\nRESPUESTA: No se ha podido crear producto.\n");
                        }
                        break;

                    case 5: // Modificar Stock de Producto
                        // Solicitamos Id de producto y nuevo Stock
                        int idProductoAModificar = utils.leerEnteroPositivo("Introduce el id del producto a modificar: ");
                        int nuevoStock = utils.leerEnteroPositivo("Introduce el nuevo stock del producto: ");
                        // Petición
                        os.writeInt(opcion);
                        os.writeInt(idProductoAModificar);
                        os.writeInt(nuevoStock);
                        os.flush();
                        //Respuesta
                        Boolean productoModificado = is.readBoolean();
                        if(productoModificado) {
                            System.out.println("\nRESPUESTA: Stock de producto(id:"+idProductoAModificar+") actualizado con éxito a "+nuevoStock+".\n");
                        } else {
                            System.out.println("\nRESPUESTA: No se ha podido modificar el producto.\n");
                        }
                        break;

                    case 6: // Eliminar producto
                        // Pedimo Id del producto
                        int idProductoAEliminar = utils.leerEnteroPositivo("Introduce el id del producto a eliminar: ");
                        // Petición
                        os.writeInt(opcion);
                        os.writeInt(idProductoAEliminar);
                        os.flush();
                        // Respuesta
                        Boolean productoEliminado = is.readBoolean();
                        if(productoEliminado) {
                            System.out.println("\nRESPUESTA: Producto (Id:"+idProductoAEliminar+") eliminado con éxito.\n");
                        } else {
                            System.out.println("\nRESPUESTA: No se ha podido eliminar el producto.\n");
                        }
                        break;

                    case 7: // Cerrar programa y conexión
                        System.out.println("Cerrando programa...");
                        // Petición
                        os.writeInt(opcion);
                        os.flush();
                        programaAbierto = false;
                        // Cerramos conexión
                        os.close();
                        is.close();
                        socket.close();
                        System.out.println("Conexiones cerradas.");
                        break;
                }


            }

            System.out.println("¡Hasta la próxima!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
