package com.ecomerch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categoria")
public class CategoriaModel {

    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    /* Relación de Categoria 1:N con Productos */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnoreProperties("categoria")
    private List<ProductoModel> productoModels = new ArrayList<>();

    /* GETTERS */
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    /* SETTERS */
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
