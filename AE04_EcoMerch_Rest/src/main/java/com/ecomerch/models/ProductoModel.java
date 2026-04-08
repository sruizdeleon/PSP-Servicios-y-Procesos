package com.ecomerch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="producto")
public class ProductoModel {

    /* Atributos */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="precio")
    private double precio;

    @Column(name="stock")
    private int stock;

    /* Relación Producto N:1 con Categoría */
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties("productoModels")
    private CategoriaModel categoria;

    /* GETTERS */

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    /* SETTERS */

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(CategoriaModel categoriaModel) {
        this.categoria = categoriaModel;
    }
}
