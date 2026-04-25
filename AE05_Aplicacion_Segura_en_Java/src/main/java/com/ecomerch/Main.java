package com.ecomerch;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final GestionUsuarios gestion = new GestionUsuarios();

    // Programa principal
    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            // Menú
            System.out.println("\n===== ECO MERCH S.A. SEGURO ====");
            System.out.println("1. Registro");
            System.out.println("2. Login");
            System.out.println("3. Salir");
            System.out.print("==| Selecciona la opción: ");
            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrar(); // Registrar usuario
                    break;
                case 2:
                    login(); // Iniciar sesión
                    break;
                case 3:
                    salir = true; // Salir
                    break;
                default:
                    System.out.println("[ERROR] - La opción elegida no es válida.");
            }
        }
    }

    // Función para registrar usuario contraseña y rol
    private static void registrar() {
        System.out.print("==| Nombre: "); String n = sc.nextLine();
        System.out.print("==| Contraseña: "); String p = sc.nextLine();
        System.out.print("==| Rol (admin/usuario): "); String r = sc.nextLine().toLowerCase();

        // Registramos el usuario
        if (gestion.registrarUsuario(n, p, r)) {
            // Si no hay un usuario registrado
            System.out.println("[INFO] - Registro con éxito del usuario " + n);
        } else {
            // Si hay ya un usuario registrado
            System.out.println("[ERROR] - El usuario "+ n +" ya existe");
        }
    }

    // Función para iniciar sesión comprobando que exista el usuario en la carpeta usuarios
    private static void login() {
        // Recogemos el usuario y contraseña
        System.out.print("==| Nombre: "); String n = sc.nextLine();
        System.out.print("==| Contraseña: "); String p = sc.nextLine();
        // Comprobamos el login
        String rol = gestion.login(n, p);

        if (rol != null) {
            System.out.println("[INFO] - ¡Te damos la bienvenida "+ n +"!");
            if (rol.equals("admin")) {
                // Mostrar menú de administrador
                menuAdmin();
            }
            else {
                // Mostrar menú de usuario
                menuUsuario();
            }
        } else {
            System.out.println("[ERROR] - Password incorrecto");
        }
    }

    // Menú de administrador para listar y borrar usuarios
    private static void menuAdmin() { // [cite: 25]
        boolean salir = false;
        while (!salir) {
            // Menú
            System.out.println("\n==== MENÚ ADMIN ====");
            System.out.println("1. Listar");
            System.out.println("2. Borrar");
            System.out.println("3. Salir");
            System.out.print("==| Selecciona una opcion: ");
            int opcion = leerEntero();
            if (opcion == 1) {
                // Listar usuarios de la carpeta usuarios
                gestion.listarUsuarios();
            } else if (opcion == 2) {
                // Eliminar usuario de la carpeta usuarios
                System.out.print("==| Nombre del usuario a borrar: ");
                gestion.borrarUsuario(sc.nextLine());
            } else {
                // Salir
                salir = true;
            }
        }
    }

    // Menú de usuario para cifrar con César
    private static void menuUsuario() { //
        boolean salir = false;
        while (!salir) {
            // Menú
            System.out.println("\n==== MENÚ USUARIO ====");
            System.out.println("1. Cifrar César");
            System.out.println("2. Descifrar César");
            System.out.println("3. Salir");
            System.out.print("==| Selecciona una opcion: ");
            int opcion = leerEntero();
            if (opcion == 1) {
                // Cifrado
                System.out.print("==| Introduce el texto a cifrar: ");
                String textoSinCifrar = sc.nextLine();
                String textoCifrado = CifradoCesar.cifrar(textoSinCifrar);
                System.out.println("==| Texto cifrado: " + textoCifrado);
            } else if (opcion == 2) {
                // Descifrado
                System.out.print("==| Introduce el texto cifrado: ");
                String textoCifrado = sc.nextLine();
                String textoSinCifrar = CifradoCesar.descifrar(textoCifrado);
                System.out.println("==| Texto sin cifrar: " +  textoSinCifrar);
            } else {
                // Salir
                salir = true;
            }
        }
    }

    // Función para leer enteros y manejar error de formato
    private static int leerEntero() {
        try {
            String entrada = sc.nextLine();
            return Integer.parseInt(entrada);
        } catch (Exception e) {
            return -1;
        }
    }
}