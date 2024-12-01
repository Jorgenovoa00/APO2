 package org.example.Model;

public class Artista {
    private String nombre;
    private String generoMusical = "Desconocido";  // Valor predeterminado
    private String pais = "Desconocido";  // Valor predeterminado

    // Constructor modificado para aceptar solo el nombre
    public Artista(String nombre) {
        this.nombre = nombre;
    }

    public Artista() {

    }

    // Método que devuelve la información del artista
    public String obtenerInformacion() {
        return "Artista: " + nombre + ", Género: " + generoMusical + ", País: " + pais;
    }

    // Métodos getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public String getPais() {
        return pais;
    }

    public String toCSV() {
        return nombre + "," + generoMusical + "," + pais;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public void setPais(String s) {
    }
}