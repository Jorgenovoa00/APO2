package org.example.UI;

import org.example.Model.ReproductorMusica;
import org.example.Model.ListaCanciones;
import org.example.Model.Cancion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class MainWindow extends JFrame implements ActionListener {
    private ReproductorMusica reproductor;
    private ListaCanciones listaCanciones;

    private JButton btnReproducir, btnPausar, btnDetener, btnSiguiente, btnAnterior, btnCargarArchivo;
    private JButton btnCrearLista, btnCrearCancion, btnCrearArtista;
    private JLabel lblEstado, lblVolumen;
    private JTextArea txtAreaCanciones;
    private JSlider volumenSlider;

    public MainWindow() {
        reproductor = new ReproductorMusica();
        listaCanciones = reproductor.getListaReproduccion();

        this.setSize(900, 600);
        this.setTitle("Music Collection");
        this.setLayout(new BorderLayout());

        JPanel panelControles = new JPanel(new GridLayout(1, 5));
        panelControles.setPreferredSize(new Dimension(900, 100));

        // Botones de control
        btnReproducir = new JButton("Reproducir");
        btnPausar = new JButton("Pausar");
        btnDetener = new JButton("Detener");
        btnSiguiente = new JButton("Siguiente");
        btnAnterior = new JButton("Anterior");
        btnCargarArchivo = new JButton("Cargar Archivo");

        btnReproducir.addActionListener(this);
        btnPausar.addActionListener(this);
        btnDetener.addActionListener(this);
        btnSiguiente.addActionListener(this);
        btnAnterior.addActionListener(this);
        btnCargarArchivo.addActionListener(this);

        panelControles.add(btnReproducir);
        panelControles.add(btnPausar);
        panelControles.add(btnDetener);
        panelControles.add(btnSiguiente);
        panelControles.add(btnAnterior);

        lblEstado = new JLabel("Estado: Detenido", SwingConstants.CENTER);

        txtAreaCanciones = new JTextArea(10, 30);
        txtAreaCanciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCanciones);

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

        // Botones nuevos
        btnCrearLista = new JButton("Crear Lista");
        btnCrearCancion = new JButton("Crear Canción");
        btnCrearArtista = new JButton("Crear Artista");

        btnCrearLista.addActionListener(this);
        btnCrearCancion.addActionListener(this);
        btnCrearArtista.addActionListener(this);

        JPanel panelCarga = new JPanel(new GridLayout(4, 1));
        panelCarga.add(btnCargarArchivo);
        panelCarga.add(btnCrearLista);
        panelCarga.add(btnCrearCancion);
        panelCarga.add(btnCrearArtista);

        this.add(panelControles, BorderLayout.NORTH);
        this.add(lblEstado, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.EAST);
        this.add(panelVolumen, BorderLayout.SOUTH);
        this.add(panelCarga, BorderLayout.WEST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Reproducir":
                reproductor.reproducir();
                lblEstado.setText("Estado: Reproduciendo");
                break;
            case "Pausar":
                reproductor.pausar();
                lblEstado.setText("Estado: Pausado");
                break;
            case "Detener":
                reproductor.detener();
                lblEstado.setText("Estado: Detenido");
                break;
            case "Siguiente":
                reproductor.siguienteCancion();
                lblEstado.setText("Estado: Reproduciendo Siguiente Canción");
                break;
            case "Anterior":
                reproductor.anteriorCancion();
                lblEstado.setText("Estado: Reproduciendo Canción Anterior");
                break;
            case "Cargar Archivo":
                cargarArchivoCSV();
                break;
            case "Crear Lista":
                mostrarDialogoLista();
                break;
            case "Crear Canción":
                mostrarDialogoCancion();
                break;
            case "Crear Artista":
                mostrarDialogoArtista();
                break;
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
                // Definir la ruta y el nombre del archivo con el nombre ingresado por el usuario
                String rutaArchivoListas = "src/main/resources/data/" + nombreListaTexto + ".csv";

                // Verificar si el archivo ya existe
                File archivo = new File(rutaArchivoListas);
                if (archivo.exists()) {
                    JOptionPane.showMessageDialog(this, "Ya existe una lista con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Guardar el nombre de la lista en el archivo CSV
                    guardarEnCSV(rutaArchivoListas, nombreListaTexto);
                    // Actualizar la interfaz para mostrar la lista creada
                    txtAreaCanciones.append("Lista creada: " + nombreListaTexto + "\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre válido para la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void mostrarDialogoCancion() {
        // Atributos para añadir la canción
        JTextField tituloCancion = new JTextField();
        JTextField artistaCancion = new JTextField();
        JTextField duracionCancion = new JTextField();
        JTextField albumCancion = new JTextField();
        JTextField generoCancion = new JTextField();

        // Panel para la interfaz
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

        // Mostrar el panel en un cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Canción", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados por el usuario
            String titulo = tituloCancion.getText().trim();
            String artista = artistaCancion.getText().trim();
            String duracionStr = duracionCancion.getText().trim();
            String album = albumCancion.getText().trim();
            String genero = generoCancion.getText().trim();

            // Validar que los campos no estén vacíos y que la duración sea un número válido
            if (!titulo.isEmpty() && !artista.isEmpty() && !duracionStr.isEmpty() && !album.isEmpty() && !genero.isEmpty()) {
                try {
                    int duracion = Integer.parseInt(duracionStr);

                    // Verificar si la canción ya existe (por título y artista)
                    boolean songExists = listaCanciones.getCanciones().stream()
                            .anyMatch(c -> c.getTitulo().equalsIgnoreCase(titulo) && c.getArtista().equalsIgnoreCase(artista));

                    if (songExists) {
                        JOptionPane.showMessageDialog(this, "Ya existe una canción con ese título y artista.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Crear la canción
                        Cancion cancion = new Cancion(titulo, artista, duracion, album, genero);

                        // Guardar la canción en el archivo CSV
                        String rutaArchivoCanciones = "src/main/resources/data/canciones.csv";
                        String contenidoCancion = titulo + "," + artista + "," + duracion + "," + album + "," + genero;

                        guardarEnCSV(rutaArchivoCanciones, contenidoCancion);

                        // Añadir la canción a la lista interna
                        listaCanciones.agregarCancion(cancion);

                        // Actualizar la interfaz mostrando la canción agregada
                        txtAreaCanciones.append("Canción Añadida: " + cancion.obtenerInformacion() + "\n");
                    }
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

    private void guardarEnCSV(String rutaArchivo, String contenido) {
        try (FileWriter writer = new FileWriter(rutaArchivo, true)) {
            writer.write(contenido + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Método original de carga de canciones
    private void cargarArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            cargarCancionesDesdeCSV(archivoSeleccionado.getAbsolutePath());
        }
    }

    private void cargarCancionesDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // Leer y descartar la línea de encabezados
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String titulo = datos[0].replace("\"", "");
                    String artista = datos[1].replace("\"", "");
                    int duracion = Integer.parseInt(datos[2].trim()); // Asegúrate de que no haya espacios extra
                    String album = datos[3].replace("\"", "");
                    String genero = datos[4].replace("\"", "");

                    // Imprimir los datos para depurar
                    System.out.println("Cargando canción: " + titulo + " - " + artista);

                    // Crear una canción a partir de los datos y agregarla a la lista
                    Cancion cancion = new Cancion(titulo, artista, duracion, album, genero);
                    listaCanciones.agregarCancion(cancion);

                    // Actualizar la interfaz gráfica con la nueva canción
                    txtAreaCanciones.append(cancion.obtenerInformacion() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
