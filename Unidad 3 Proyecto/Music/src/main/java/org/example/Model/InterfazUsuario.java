package org.example.Model;

public class InterfazUsuario {
    private ReproductorMusica reproductor;

    public InterfazUsuario(ReproductorMusica reproductor) {
        this.reproductor = reproductor;
    }

    public void mostrarCancionActual() {
        Cancion actual = reproductor.getListaReproduccion().buscarPorTitulo("Cancion Actual");
        if (actual != null) {
            System.out.println(actual.obtenerInformacion());
        }
    }

    public void mostrarVolumen() {
        System.out.println("Mostrar:  Volumen actual");
    }

    public void mostrarListaReproduccion() {
        for (Cancion c : reproductor.getListaReproduccion().getCanciones()) {
            System.out.println(c.obtenerInformacion());
        }
    }
}
