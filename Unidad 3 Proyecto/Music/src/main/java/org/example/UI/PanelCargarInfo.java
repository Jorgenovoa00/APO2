package org.example.UI;

import org.example.Model.ListaCanciones;
import org.example.Model.Cancion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PanelCargarInfo extends JPanel implements ActionListener {
    private JButton botonCargarArchivo;
    private ListaCanciones listaCanciones;
    private JTextArea txtAreaCanciones;

    public PanelCargarInfo(ListaCanciones listaCanciones) {
        this.listaCanciones = listaCanciones;
        this.setLayout(new BorderLayout());

        // Crear los botones
        botonCargarArchivo = new JButton("Cargar Archivo");
        botonCargarArchivo.addActionListener(this);
        botonCargarArchivo.setActionCommand("Cargar Archivo");

        // Crear el área de texto para mostrar las canciones cargadas
        txtAreaCanciones = new JTextArea(10, 30);
        txtAreaCanciones.setEditable(false);

        // Agregar los componentes al panel
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonCargarArchivo);
        this.add(panelBotones, BorderLayout.NORTH);
        this.add(new JScrollPane(txtAreaCanciones), BorderLayout.CENTER);  // Área de texto dentro de un JScrollPane
    }

    // Método para cargar canciones desde un archivo CSV
    private void cargarCancionesDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // Leer y descartar la línea de encabezados
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String titulo = datos[0].replace("\"", "");
                    String artista = datos[1].replace("\"", "");
                    int duracion = Integer.parseInt(datos[2]);
                    String album = datos[3].replace("\"", "");
                    String genero = datos[4].replace("\"", "");
                    Cancion cancion = new Cancion(titulo, artista, duracion, album, genero);
                    listaCanciones.agregarCancion(cancion);
                    // Mostrar la canción en la interfaz
                    txtAreaCanciones.append(cancion.obtenerInformacion() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        if (accion.equals("Cargar Archivo")) {
            cargarCancionesDesdeCSV("src/main/resources/data/canciones.csv");
        }
    }
}
