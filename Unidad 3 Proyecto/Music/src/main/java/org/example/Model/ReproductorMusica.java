package org.example.Model;

import java.util.List;

public class ReproductorMusica {
    private ListaCanciones listaReproduccion;
    private int indiceCancionActual = -1;  // Almacenar el índice de la canción actual

    public ReproductorMusica() {
        this.listaReproduccion = new ListaCanciones();  // Inicializar la lista de canciones
    }

    public ListaCanciones getListaReproduccion() {
        return listaReproduccion;
    }

    // Método para obtener la canción actual
    public Cancion getCancionActual() {
        if (indiceCancionActual >= 0 && indiceCancionActual < listaReproduccion.getCanciones().size()) {
            return listaReproduccion.getCanciones().get(indiceCancionActual);
        }
        return null;  // Si no hay ninguna canción actual
    }

    // Métodos de reproducción (simulados)
    public void reproducir() {
        // Simulación de reproducción, actualiza el índice a la primera canción si está detenida
        if (indiceCancionActual == -1 && !listaReproduccion.getCanciones().isEmpty()) {
            indiceCancionActual = 0;
        }
        // Aquí puedes agregar la lógica real para reproducir música
    }

    public void pausar() {
        // Lógica de pausa, si es necesario
    }

    public void detener() {
        indiceCancionActual = -1;  // Detener la canción
    }

    public void siguienteCancion() {
        if (indiceCancionActual + 1 < listaReproduccion.getCanciones().size()) {
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
}
