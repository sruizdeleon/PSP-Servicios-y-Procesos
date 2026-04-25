package com.ecomerch;

// Clase para manejar el cifrado César
public class CifradoCesar {
    // Método para cifrar
    public static String cifrar(String texto) {
        return desplazar(texto, 3);
    }

    // Método para descifrar
    public static String descifrar(String texto) {
        return desplazar(texto, -3);
    }

    // Método para para desplazar
    private static String desplazar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        // Recorremos caracter a caracter
        for (char caracter : texto.toCharArray()) {
            // Comprobamos que sea una letra
            if (Character.isLetter(caracter)) {
                // Determinamos si mayúscula = 'A' o minúscula = 'a'
                char base = Character.isUpperCase(caracter) ? 'A' : 'a';
                // Convertimos la posición
                int posicionOriginal = caracter - base;
                // Desplazamiento y usamos % para reiniciar el alfabeto
                int nuevaPosicion = (posicionOriginal + desplazamiento) % 26;
                // Si el desplazamiento es negativo desciframos y si es menor que 0 sumamos 26
                if (nuevaPosicion < 0) nuevaPosicion += 26;
                // Volvemos a convertir el número en un carácter
                resultado.append((char) (base + nuevaPosicion));
            } else {
                // Añadimos todo lo que no sea una letra
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }
}