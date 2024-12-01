package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class ReproductorMusica {
    private List<ListaCanciones> listasReproduccion;  // Lista para manejar múltiples listas de reproducción
    private int indiceCancionActual = -1;  // Almacenar el índice de la canción actual

    public ReproductorMusica() {
        this.listasReproduccion = new ArrayList<>();  // Inicializar la lista de listas de reproducción
    }

    // Método para obtener todas las listas de reproducción
    public List<ListaCanciones> getListasReproduccion() {
        return listasReproduccion;
    }

    // Método para agregar una nueva lista de reproducción
    public void agregarListaReproduccion(ListaCanciones lista) {
        listasReproduccion.add(lista);
    }

    // Método para buscar una lista por nombre
    public ListaCanciones buscarListaPorNombre(String nombre) {
        for (ListaCanciones lista : listasReproduccion) {
            if (lista.getNombre().equals(nombre)) {
                return lista;
            }
        }
        return null;  // Retorna null si no encuentra la lista
    }

    // Método para obtener la canción actual
    public Cancion getCancionActual() {
        if (indiceCancionActual >= 0 && indiceCancionActual < getListaReproduccion().getCanciones().size()) {
            return getListaReproduccion().getCanciones().get(indiceCancionActual);
        }
        return null;  // Si no hay ninguna canción actual
    }

    // Métodos de reproducción (simulados)
    public void reproducir() {
        if (indiceCancionActual == -1 && !getListaReproduccion().getCanciones().isEmpty()) {
            indiceCancionActual = 0;  // Reproduce la primera canción si está detenido
        }
        if (indiceCancionActual >= 0) {
            // Lógica real para reproducir la canción
            System.out.println("Reproduciendo: " + getListaReproduccion().getCanciones().get(indiceCancionActual).obtenerInformacion());
        }
    }

    public void pausar() {
        // Lógica de pausa, si es necesario
    }

    public void detener() {
        indiceCancionActual = -1;  // Detener la canción
    }

    public void siguienteCancion() {
        if (indiceCancionActual + 1 < getListaReproduccion().getCanciones().size()) {
            indiceCancionActual++;
        }
    }

    public void anteriorCancion() {
        if (indiceCancionActual - 1 >= 0) {
            indiceCancionActual--;
        }
    }

    public void ajustarVolumen(int volumen) {
        // Lógica para ajustar el volumen
    }

    // Devuelve la lista actual que se está reproduciendo
    public ListaCanciones getListaReproduccion() {
        return listasReproduccion.isEmpty() ? null : listasReproduccion.get(0);  // Solo devuelve la primera lista, o null si no hay listas
    }

    public void agregarCancion(Cancion nuevaCancion) {
    }

    public void agregarArtista(Artista nuevoArtista) {
    }
}

