package utils;

import model.Producto;

import java.util.List;
import java.util.Scanner;

public class Utilidades {

    // Atributos para la conexión
    public static final int PUERTO = 5000;
    public static final String DIRECCION = "Localhost";

    // Escaner para cada validación
    public final Scanner scanner;

    // Constructor
    public Utilidades() {
        scanner = new Scanner(System.in);
    }

    // Metodo para solicitar por pantalla enteros positivos
    public int leerEnteroPositivo(String mensaje){
        int num = 0;
        boolean error = false;
        do {
            error = false;
            try{
                // Mostramos el mensaje
                System.out.println(mensaje);
                num = Integer.parseInt(scanner.nextLine());
                // Si es menor que cero lanzamos error
                if(num < 0){
                    System.out.println("ERROR - Numero negativo.");
                    error = true;
                }
            // Si no se puede parsear a entero lanzamos un error
            } catch(NumberFormatException e){
                System.out.println("ERROR - Formato de número incorrecto.");
                error = true;
            }
        } while(error);
        return num;
    }

    // Metodo para solicitar por pantalla decimales positivos
    public double leerDoublePositivo(String mensaje){
        double num = 0;
        boolean error = false;
        do {
            error = false;
            try{
                // Mostramos el mensaje
                System.out.println(mensaje);
                num = Double.parseDouble(scanner.nextLine());
                // Si es menor que cero lanzamos error
                if(num < 0){
                    System.out.println("ERROR - Numero negativo.");
                    error = true;
                }
            // Si no se puede parsear a Double lanzamos error
            } catch(NumberFormatException e){
                System.out.println("ERROR - Formato de número incorrecto.");
                error = true;
            }
        } while(error);
        return num;
    }

    // Metodo para solicitar por pantalla texto
    public String leerTexto(String mensaje){
        String texto = "";
        do {
            // Mostramos mensaje
            System.out.println(mensaje);
            texto = scanner.nextLine();
            // Comprobamos que no esté vacío
            if(texto.trim().isEmpty()){
                System.out.println("ERROR - Texto vacío.");
                continue;
            }
            break;
        } while(true);
        return texto;
    }

    // Metodo para mostra listado de productos por pantalla
    public void mostrarProductos(List<Producto> productos) throws  Exception {
        System.out.println("\n======================= PRODUCTOS =======================");
        for(Producto producto : productos) {
            System.out.println(producto.toString());
        }
        System.out.println();
    }

    // Metodo para recoger los atributos de un producto y devolverlo
    public Producto recogerDatosProducto(){
        String nombre = leerTexto("Nombre del producto: ");
        String categoria = leerTexto("Categoria del producto: ");
        Double precio = leerDoublePositivo("Precio del producto: ");
        int stock = leerEnteroPositivo("Stock del producto: ");
        Producto producto = new Producto(nombre, categoria, precio, stock);
        return producto;
    }

}
