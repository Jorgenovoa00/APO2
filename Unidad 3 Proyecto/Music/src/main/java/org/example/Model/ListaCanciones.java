package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class ListaCanciones {
    private List<Cancion> canciones;
    private ModoReproduccion modoReproduccion;

    public ListaCanciones() {
        this.canciones = new ArrayList<>();
        this.modoReproduccion = ModoReproduccion.NORMAL;  // Usar el enum en lugar de un String
    }


    public void agregarCancion(Cancion c) {
        canciones.add(c);
    }

    public void eliminarCancion(Cancion c) {
        canciones.remove(c);
    }

    public void ordenarPorArtista() {
        canciones.sort((c1, c2) -> c1.getArtista().compareTo(c2.getArtista()));
    }

    public Cancion buscarPorTitulo(String titulo) {
        for (Cancion c : canciones) {
            if (c.getTitulo().equalsIgnoreCase(titulo)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Canción no encontrada: " + titulo); // Lanzar una excepción si no se encuentra
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    // Método adicional para obtener la información de la lista
    public String obtenerInformacionLista() {
        StringBuilder info = new StringBuilder();
        for (Cancion c : canciones) {
            info.append(c.obtenerInformacion()).append("\n");
        }
        return info.toString();
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
