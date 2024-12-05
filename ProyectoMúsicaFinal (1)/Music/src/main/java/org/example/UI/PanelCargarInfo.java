package org.example.UI;

import org.example.Model.Cancion;
import org.example.Model.ReproductorMusica;
import org.example.Model.ListaCanciones;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PanelCargarInfo extends JPanel {
    private ReproductorMusica reproductor;
    private ListaCanciones listaCanciones;
    private JButton btnCargarArchivo, btnCrearLista, btnCrearCancion, btnCrearArtista, btnAgregarCancionALista;
    private JTextArea txtAreaCanciones;

    public PanelCargarInfo(ReproductorMusica reproductor, ListaCanciones listaCanciones) {
        this.reproductor = reproductor;
        this.listaCanciones = listaCanciones;

        this.setLayout(new GridLayout(6, 1));

        // Inicializar los componentes de la interfaz
        btnCargarArchivo = new JButton("Cargar Archivo");
        btnCrearLista = new JButton("Crear Lista");
        btnCrearCancion = new JButton("Crear Canción");
        btnCrearArtista = new JButton("Crear Artista");
        btnAgregarCancionALista = new JButton("Agregar a Lista");

        txtAreaCanciones = new JTextArea();
        txtAreaCanciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCanciones);

        // Agregar los botones y el área de texto al panel
        this.add(btnCargarArchivo);
        this.add(btnCrearLista);
        this.add(btnCrearCancion);
        this.add(btnCrearArtista);
        this.add(btnAgregarCancionALista);
        this.add(scrollPane);

        // Asignar los event listeners a los botones
        btnCargarArchivo.addActionListener(e -> cargarArchivoCSV());
        btnCrearLista.addActionListener(e -> mostrarDialogoLista());
        btnCrearCancion.addActionListener(e -> mostrarDialogoCancion());
        btnCrearArtista.addActionListener(e -> mostrarDialogoArtista());
        btnAgregarCancionALista.addActionListener(e -> mostrarDialogoSeleccionarLista());
    }

    private void cargarArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            MainWindow.getInstance().cargarCancionesDesdeCSV(archivoSeleccionado.getAbsolutePath());
        }
    }

    private void mostrarDialogoLista() {
        JTextField nombreLista = new JTextField();
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Nombre de la lista:"));
        panel.add(nombreLista);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Lista", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombreListaTexto = nombreLista.getText().trim();
            if (!nombreListaTexto.isEmpty()) {
                String rutaArchivoListas = "src/main/resources/data/" + nombreListaTexto + ".csv";
                File archivo = new File(rutaArchivoListas);
                if (archivo.exists()) {
                    JOptionPane.showMessageDialog(this, "Ya existe una lista con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    guardarEnCSV(rutaArchivoListas, nombreListaTexto);
                    txtAreaCanciones.append("Lista creada: " + nombreListaTexto + "\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre válido para la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarEnCSV(String rutaArchivo, String contenido) {
        try (FileWriter writer = new FileWriter(rutaArchivo, true)) {
            writer.write(contenido + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarDialogoCancion() {
        JTextField tituloCancion = new JTextField();
        JTextField artistaCancion = new JTextField();
        JTextField duracionCancion = new JTextField();
        JTextField albumCancion = new JTextField();
        JTextField generoCancion = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloCancion);
        panel.add(new JLabel("Artista:"));
        panel.add(artistaCancion);
        panel.add(new JLabel("Duración (segundos):"));
        panel.add(duracionCancion);
        panel.add(new JLabel("Álbum:"));
        panel.add(albumCancion);
        panel.add(new JLabel("Género:"));
        panel.add(generoCancion);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Canción", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String titulo = tituloCancion.getText().trim();
            String artista = artistaCancion.getText().trim();
            String duracionStr = duracionCancion.getText().trim();
            String album = albumCancion.getText().trim();
            String genero = generoCancion.getText().trim();

            if (!titulo.isEmpty() && !artista.isEmpty() && !duracionStr.isEmpty() && !album.isEmpty() && !genero.isEmpty()) {
                try {
                    int duracion = Integer.parseInt(duracionStr);
                    Cancion cancion = new Cancion(titulo, artista, duracion, album, genero);

                    String rutaArchivoCanciones = "src/main/resources/data/canciones.csv";
                    String contenidoCancion = titulo + "," + artista + "," + duracion + "," + album + "," + genero;
                    guardarEnCSV(rutaArchivoCanciones, contenidoCancion);

                    listaCanciones.agregarCancion(cancion);
                    txtAreaCanciones.append("Canción Añadida: " + cancion.obtenerInformacion() + "\n");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "La duración debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDialogoArtista() {
        JTextField nombreArtista = new JTextField();
        JTextField generoArtista = new JTextField();
        JTextField paisArtista = new JTextField();
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreArtista);
        panel.add(new JLabel("Género:"));
        panel.add(generoArtista);
        panel.add(new JLabel("País:"));
        panel.add(paisArtista);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Artista", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            guardarEnCSV("src/main/resources/data/artistas.csv", nombreArtista.getText() + "," + generoArtista.getText() + "," + paisArtista.getText());
        }
    }

    private void mostrarDialogoSeleccionarLista() {
        JTextField nombreLista = new JTextField();
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Nombre de la lista:"));
        panel.add(nombreLista);

        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccionar Lista", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombreListaTexto = nombreLista.getText().trim();
            if (!nombreListaTexto.isEmpty()) {
                agregarCancionALista(nombreListaTexto);
            } else {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre válido para la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarCancionALista(String nombreLista) {
        String rutaArchivoCanciones = "src/main/resources/data/canciones.csv";
        String rutaArchivoLista = "src/main/resources/data/" + nombreLista + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoCanciones))) {
            String linea;
            StringBuilder cancionesDisponibles = new StringBuilder();
            while ((linea = br.readLine()) != null) {
                cancionesDisponibles.append(linea).append("\n");
            }

            JTextArea textAreaCanciones = new JTextArea(cancionesDisponibles.toString());
            JScrollPane scrollPane = new JScrollPane(textAreaCanciones);
            textAreaCanciones.setEditable(false);

            String cancionSeleccionada = JOptionPane.showInputDialog(this, scrollPane,
                    "Seleccione una canción escribiendo el título exacto", JOptionPane.PLAIN_MESSAGE);

            if (cancionSeleccionada != null && !cancionSeleccionada.trim().isEmpty()) {
                try (BufferedReader brBusqueda = new BufferedReader(new FileReader(rutaArchivoCanciones))) {
                    String busqueda;
                    boolean encontrada = false;
                    while ((busqueda = brBusqueda.readLine()) != null) {
                        if (busqueda.contains(cancionSeleccionada.trim())) {
                            try (FileWriter writer = new FileWriter(rutaArchivoLista, true)) {
                                writer.write(busqueda + "\n");
                                txtAreaCanciones.append("Canción añadida a la lista: " + cancionSeleccionada + "\n");
                                encontrada = true;
                                break;
                            }
                        }
                    }
                    if (!encontrada) {
                        JOptionPane.showMessageDialog(this, "Canción no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se seleccionó una canción válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
