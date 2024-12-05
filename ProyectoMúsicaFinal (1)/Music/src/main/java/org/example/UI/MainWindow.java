package org.example.UI;

import org.example.Model.ReproductorMusica;
import org.example.Model.ListaCanciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JFrame implements ActionListener {
    private static MainWindow instance; // Singleton instance
    private ReproductorMusica reproductor;
    private ListaCanciones listaCanciones;
    private PanelInfo panelInfo;
    private PanelCargarInfo panelCargarInfo;

    // Constructor privado para restringir la creación de instancias
    private MainWindow() {
        reproductor = new ReproductorMusica();
        listaCanciones = reproductor.getListaReproduccion();

        this.setSize(900, 600);
        this.setTitle("Music Collection");
        this.setLayout(new BorderLayout());

        panelInfo = new PanelInfo(reproductor, listaCanciones);
        panelCargarInfo = new PanelCargarInfo(reproductor, listaCanciones);

        this.add(panelInfo, BorderLayout.CENTER);
        this.add(panelCargarInfo, BorderLayout.WEST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cargar canciones por defecto
        cargarCancionesDesdeCSV("src/main/resources/data/canciones.csv");
    }

    // Metodo estático para obtener la instancia única
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cargar Archivo":
                cargarArchivoCSV();
                break;
            // Otros casos de los botones de control se manejan en PanelInfo
        }
    }

    public void cargarArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            cargarCancionesDesdeCSV(archivoSeleccionado.getAbsolutePath());
        }
    }

    public void cargarCancionesDesdeCSV(String rutaArchivo) {
        panelInfo.cargarCancionesDesdeCSV(rutaArchivo);
    }

    public static void main(String[] args) {
        // Llama al Singleton para mostrar la ventana principal
        MainWindow ventana = MainWindow.getInstance();
        ventana.setVisible(true);
    }
}
