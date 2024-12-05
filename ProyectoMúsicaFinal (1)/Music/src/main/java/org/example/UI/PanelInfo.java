package org.example.UI;

import org.example.Model.ReproductorMusica;
import org.example.Model.ListaCanciones;
import org.example.Model.Cancion;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PanelInfo extends JPanel {
    private ReproductorMusica reproductor;
    private ListaCanciones listaCanciones;
    private JButton btnReproducir, btnPausar, btnDetener, btnSiguiente, btnAnterior;
    private JLabel lblEstado, lblVolumen;
    private JTextArea txtAreaCanciones;
    private JSlider volumenSlider;

    public PanelInfo(ReproductorMusica reproductor, ListaCanciones listaCanciones) {
        this.reproductor = reproductor;
        this.listaCanciones = listaCanciones;

        this.setLayout(new BorderLayout());

        // Controles de la interfaz
        JPanel panelControles = new JPanel(new GridLayout(1, 5));
        btnReproducir = new JButton("Reproducir");
        btnPausar = new JButton("Pausar");
        btnDetener = new JButton("Detener");
        btnSiguiente = new JButton("Siguiente");
        btnAnterior = new JButton("Anterior");

        btnReproducir.addActionListener(e -> reproducir());
        btnPausar.addActionListener(e -> pausar());
        btnDetener.addActionListener(e -> detener());
        btnSiguiente.addActionListener(e -> siguienteCancion());
        btnAnterior.addActionListener(e -> anteriorCancion());

        panelControles.add(btnReproducir);
        panelControles.add(btnPausar);
        panelControles.add(btnDetener);
        panelControles.add(btnSiguiente);
        panelControles.add(btnAnterior);

        lblEstado = new JLabel("Estado: Detenido", SwingConstants.CENTER);

        txtAreaCanciones = new JTextArea(10, 30);
        txtAreaCanciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCanciones);

        // Configuración de volumen
        JPanel panelVolumen = new JPanel(new BorderLayout());
        volumenSlider = new JSlider(0, 100, 50);
        volumenSlider.setMajorTickSpacing(10);
        volumenSlider.setPaintTicks(true);
        volumenSlider.setPaintLabels(true);

        volumenSlider.addChangeListener(e -> {
            int volumen = volumenSlider.getValue();
            reproductor.ajustarVolumen(volumen);
            lblVolumen.setText("Volumen: " + volumen);
        });

        lblVolumen = new JLabel("Volumen: 50", SwingConstants.CENTER);
        panelVolumen.add(volumenSlider, BorderLayout.CENTER);
        panelVolumen.add(lblVolumen, BorderLayout.SOUTH);

        this.add(panelControles, BorderLayout.NORTH);
        this.add(lblEstado, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.EAST);
        this.add(panelVolumen, BorderLayout.SOUTH);
    }

    private void reproducir() {
        reproductor.reproducir();
        lblEstado.setText(reproductor.obtenerEstadoActual());
    }

    private void pausar() {
        reproductor.pausar();
        lblEstado.setText("Estado: Pausado");
    }

    private void detener() {
        reproductor.detener();
        lblEstado.setText("Estado: Detenido");
    }

    private void siguienteCancion() {
        reproductor.siguienteCancion();
        lblEstado.setText(reproductor.obtenerEstadoActual());
    }

    private void anteriorCancion() {
        reproductor.anteriorCancion();
        lblEstado.setText(reproductor.obtenerEstadoActual());
    }

    public void cargarCancionesDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            listaCanciones.limpiarLista();
            txtAreaCanciones.setText("");

            String linea;
            br.readLine(); // Saltar la cabecera
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String titulo = datos[0].replace("\"", "").trim();
                    String artista = datos[1].replace("\"", "").trim();
                    int duracion = Integer.parseInt(datos[2].trim());
                    String album = datos[3].replace("\"", "").trim();
                    String genero = datos[4].replace("\"", "").trim();

                    Cancion cancion = new Cancion(titulo, artista, duracion, album, genero);
                    listaCanciones.agregarCancion(cancion);

                    txtAreaCanciones.append(cancion.obtenerInformacion() + "\n");
                }
            }

            JOptionPane.showMessageDialog(this, "Canciones cargadas correctamente desde: " + rutaArchivo,
                    "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo CSV.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
