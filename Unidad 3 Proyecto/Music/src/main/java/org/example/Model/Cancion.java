package org.example.Model;

public class Cancion {
    private String titulo;
    private String artista;
    private int duracion; // segundos
    private String album;
    private String genero;

    public Cancion(String titulo, String artista, int duracion, String album, String genero)
    {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.album = album;
        this.genero = genero;
    }



    public int obtenerDuracion() {
        return duracion;
    }

    public String obtenerInformacion() {
        return "Título: " + titulo + ", Artista: " + artista + ", Duración: " + duracion + "s, Álbum: " + album + ", Género: " + genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }


}
