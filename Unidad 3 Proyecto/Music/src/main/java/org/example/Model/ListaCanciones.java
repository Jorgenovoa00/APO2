package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class ListaCanciones {
    private List<Cancion> canciones;
    private ModoReproduccion modoReproduccion;
    private String nombre;  // Nombre de la lista de canciones

    // Constructor con nombre
    public ListaCanciones(String nombre) {
        this.canciones = new ArrayList<>();
        this.modoReproduccion = ModoReproduccion.NORMAL;  // Usar el enum en lugar de un String
        this.nombre = nombre;  // Asignar el nombre de la lista
    }

    // Constructor sin parámetros
    public ListaCanciones() {
        this.canciones = new ArrayList<>();  // Inicializa la lista de canciones
        this.modoReproduccion = ModoReproduccion.NORMAL;  // Inicializa el modo de reproducción
    }

    // Agregar una canción a la lista
    public void agregarCancion(Cancion c) {
        canciones.add(c);
    }

    // Eliminar una canción de la lista
    public void eliminarCancion(Cancion c) {
        canciones.remove(c);
    }

    // Ordenar las canciones por artista (cantante)
    public void ordenarPorArtista() {
        canciones.sort((c1, c2) -> c1.getCantante().compareTo(c2.getCantante()));  // Cambié getArtista() por getCantante()
    }

    // Buscar una canción por título
    public Cancion buscarPorTitulo(String titulo) {
        for (Cancion c : canciones) {
            if (c.getTitulo().equalsIgnoreCase(titulo)) {
                return c;
            }
        }
        return null; // Devolver null si no se encuentra la canción
    }

    // Obtener la lista de canciones
    public List<Cancion> getCanciones() {
        return canciones;
    }

    // Obtener información completa de la lista de canciones
    public String obtenerInformacionLista() {
        StringBuilder info = new StringBuilder();
        for (Cancion c : canciones) {
            info.append(c.obtenerInformacion()).append("\n");
        }
        return info.toString();
    }

    // Obtener el nombre de la lista
    public String getNombre() {
        return nombre;  // Devolver el nombre de la lista
    }

    // Enum para los modos de reproducción
    public enum ModoReproduccion {
        NORMAL,
        ALEATORIO,
        REPETIR
    }

    // Métodos para cambiar el modo de reproducción
    public ModoReproduccion getModoReproduccion() {
        return modoReproduccion;
    }

    public void setModoReproduccion(ModoReproduccion modoReproduccion) {
        this.modoReproduccion = modoReproduccion;
    }
}
