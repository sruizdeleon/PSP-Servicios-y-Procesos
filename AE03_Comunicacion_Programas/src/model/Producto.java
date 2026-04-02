package model;

import java.io.Serializable;

public class Producto implements Serializable {

    /* Atributos */
    int id;
    String nombre;
    String categoria;
    Double precio;
    int stock;

    /* Constructors */
    public Producto(String nombre, String categoria, Double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(int id, String nombre, String categoria, Double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /* Metodo toString */
    @Override
    public String toString() {
        return "(" + id + ") Producto: "+ nombre + " | Categoría: " + categoria + " | Precio: " + precio + " | Stock: " + stock;
    }

    /* Getters */
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public Double getPrecio() {
        return precio;
    }
    public int getStock() {
        return stock;
    }

    /* Setters */
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

}
