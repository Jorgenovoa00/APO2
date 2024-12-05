package org.example.Model;

public class Artista {
    // Atributos
    private String nombre;
    private String genero;
    private String pais;

    // Constructor
    public Artista(String nombre, String genero, String pais) {
        this.nombre = nombre;
        this.genero = genero;
        this.pais = pais;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getPais() {
        return pais;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
