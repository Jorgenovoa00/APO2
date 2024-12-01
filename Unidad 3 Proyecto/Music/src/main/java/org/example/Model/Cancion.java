package org.example.Model;

public class Cancion {
    private String titulo;
    private String cantante;
    private int duracion;  // Duración en segundos
    private String album;
    private String genero;

    // Constructor
    public Cancion(String titulo, String cantante, int duracion, String album, String genero) {
        this.titulo = titulo;
        this.cantante = cantante;
        this.duracion = duracion;
        this.album = album;
        this.genero = genero;
    }

    public Cancion(String nombreCancion, Artista artista, int duracion, String album, String genero) {
    }

    // Métodos getters
    public String getTitulo() {
        return titulo;
    }

    public String getCantante() {
        return cantante;  // Este método devuelve el cantante
    }

    public int getDuracion() {
        return duracion;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenero() {
        return genero;
    }

    // Método para obtener información completa de la canción
    public String obtenerInformacion() {
        return "Título: " + titulo + ", Cantante: " + cantante + ", Álbum: " + album + ", Género: " + genero + ", Duración: " + duracion + "s";
    }

    public boolean getNombre() {
        return false;
    }

    public Artista getArtista() {
        return null;
    }
}
