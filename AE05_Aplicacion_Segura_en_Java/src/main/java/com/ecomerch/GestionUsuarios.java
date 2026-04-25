package com.ecomerch;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Clase para gestionar usuarios
public class GestionUsuarios {
    // Ruta para guardar los txt de usuarios
    private static final String CARPETA = "AE_05_Aplicacion_Segura_en_Java/usuarios/";

    // Constructor
    public GestionUsuarios() {
        // Asegurar que la carpeta de usuarios exista al iniciar
        File directorio = new File(CARPETA);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }

    //  Generación de hash SHA-256
    private String cifrarSHA256(String texto) {
        try {
            // Creamos la clase para manejar el hashing
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Lo guardamos en un array de bytes
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            // Añadimos los bytes para crear el string final a guardar
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            // Devolvemos el string
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // Función para el registro de usuarios
    public boolean registrarUsuario(String nombre, String password, String rol) {
        // Creamos el archivo en la carpeta
        File archivo = new File(CARPETA + nombre + ".txt");
        // Comprobamos si existe el archivo
        if (archivo.exists()) {
            return false;
        }

        // Creamos el escritor para escribir en el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            // Guardamos el rol
            writer.println(rol);
            // Guardamos la contraseña hasheada
            writer.println(cifrarSHA256(password));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Método para el login del usuario
    public String login(String nombre, String password) {
        // Creamos el fichero
        File archivo = new File(CARPETA + nombre + ".txt");
        // Comprobamos que exista
        if (!archivo.exists()) {
            return null;
        }

        // Creamos el buffer para leer
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            // Obtenemos el rol
            String rolGuardado = reader.readLine();
            // Obtenemos la contraseña hasheada
            String hashGuardado = reader.readLine();

            // Comparamos la contraseña guardada con la aportada por el usuario haseada
            if (hashGuardado.equals(cifrarSHA256(password))) {
                // Devolvemos el rol del usuario
                return rolGuardado;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    // Método para listar los usuarios que hay guardados
    public void listarUsuarios() {
        // Creamos la ruta al repositorio
        File carpeta = new File(CARPETA);
        // Leemos los archivos del repositorio
        File[] ficheros = carpeta.listFiles();
        if (ficheros == null || ficheros.length == 0) {
            // Si no hay ficheros mostramos que no hay usuarios
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (File f : ficheros) {
            // Si hay ficheros los recorremos
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                // Leemos el rol
                String rol = reader.readLine();
                // Leemos el nombre
                String nombre = f.getName().replace(".txt", "");
                // Mostramos el usuario y el rol
                System.out.println("- Usuario: " + nombre + " | Rol: " + rol);
            } catch (IOException e) {
                System.out.println("[ERROR] - El archivo no existe");
            }
        }
    }

    // Método para borrar usuarios
    public boolean borrarUsuario(String nombre) {
        // Creamos el fichero
        File archivo = new File(CARPETA + nombre + ".txt");
        // Lo borramos y si ese borra correctamente devolverá true
        return archivo.delete();
    }
}