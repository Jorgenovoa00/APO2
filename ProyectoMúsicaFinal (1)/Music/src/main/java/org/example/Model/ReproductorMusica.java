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

    // Metodo para obtener la canción actual
    public Cancion getCancionActual() {
        if (indiceCancionActual >= 0 && indiceCancionActual < listaReproduccion.getCanciones().size()) {
            return listaReproduccion.getCanciones().get(indiceCancionActual);
        }
        return null;  // Si no hay ninguna canción actual
    }

    // Métodos de reproducción (simulados)
    public void reproducir() {
        if (indiceCancionActual == -1 && !listaReproduccion.getCanciones().isEmpty()) {
            indiceCancionActual = 0; // Empieza con la primera canción si no hay ninguna seleccionada
        }

        Cancion cancionActual = getCancionActual();
        if (cancionActual != null) {
            System.out.println("Reproduciendo: " + cancionActual.obtenerInformacion());
        } else {
            System.out.println("No hay canciones para reproducir.");
        }
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
        } else {
            indiceCancionActual = 0; // Vuelve al inicio si llega al final
        }
        reproducir(); // Muestra la nueva canción
    }

    public void anteriorCancion() {
        if (indiceCancionActual - 1 >= 0) {
            indiceCancionActual--;
        } else {
            indiceCancionActual = listaReproduccion.getCanciones().size() - 1; // Va al final si está al principio
        }
        reproducir(); // Muestra la nueva canción
    }

    public void ajustarVolumen(int volumen) {
        // Lógica para ajustar el volumen
    }

    public String obtenerEstadoActual() {
        Cancion cancionActual = getCancionActual();
        return (cancionActual != null) ? "Reproduciendo: " + cancionActual.obtenerInformacion() : "No hay canciones en reproducción.";
    }
}
