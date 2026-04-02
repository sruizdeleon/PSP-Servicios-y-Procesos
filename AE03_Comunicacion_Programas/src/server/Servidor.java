package server;

import model.ProductoDAO;
import utils.Utilidades;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    public static void main(String args[]) {
        System.err.println("Iniciando servidor...");
        ServerSocket server;
        ProductoDAO productoDAO = new ProductoDAO();

        try {
            // Iniciamos un nuevo server
            server = new ServerSocket();

            // Cargamos la dirección y el puerto
            InetSocketAddress direccion = new InetSocketAddress(Utilidades.DIRECCION, Utilidades.PUERTO);

            // Vinculamos la dirección y el puerto al servidor
            server.bind(direccion);

            while (true) {
                // Por cada cliente obtenemos un socket
                Socket socketCliente = server.accept();
                System.out.println("Cliente conectado con el servidor");
                new ClienteHandler(socketCliente, productoDAO);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
