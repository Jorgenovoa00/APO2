package org.example.UI;

import org.example.Model.ReproductorMusica;
import org.example.Model.ListaCanciones;
import org.example.Model.Cancion;
import org.example.Model.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame implements ActionListener {
    private ReproductorMusica reproductor;
    private ListaCanciones listaCanciones;


    private JButton btnReproducir, btnPausar, btnDetener, btnSiguiente, btnAnterior, btnCargarArchivo;
    private JLabel lblEstado, lblVolumen;
    private JTextArea txtAreaCanciones;
    private JSlider volumenSlider;
    private JButton btnNuevaLista, btnRegistrarArtista, btnRegistrarCancion, btnAgregarACancionALista;

    public MainWindow() {
        reproductor = new ReproductorMusica();
        listaCanciones = reproductor.getListaReproduccion();


        // Configuración de la ventana principal
        this.setSize(900, 600);
        this.setTitle("Music Collection");
        this.setLayout(new BorderLayout());

        // Panel de controles (botones de control)
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(2, 5)); // 9 columnas para incluir nuevos botones
        panelControles.setPreferredSize(new Dimension(900, 100));

        // Crear los botones
        btnReproducir = new JButton("Reproducir");
        btnPausar = new JButton("Pausar");
        btnDetener = new JButton("Detener");
        btnSiguiente = new JButton("Siguiente");
        btnAnterior = new JButton("Anterior");
        btnCargarArchivo = new JButton("Cargar Archivo");
        btnNuevaLista = new JButton("Nueva Lista");
        btnRegistrarArtista = new JButton("Registrar Artista");
        btnRegistrarCancion = new JButton("Registrar Canción");
        btnAgregarACancionALista = new JButton("Agregar Canción a Lista");

        // Agregar acción a los botones
        btnReproducir.addActionListener(this);
        btnPausar.addActionListener(this);
        btnDetener.addActionListener(this);
        btnSiguiente.addActionListener(this);
        btnAnterior.addActionListener(this);
        btnCargarArchivo.addActionListener(this);
        btnNuevaLista.addActionListener(this);
        btnRegistrarArtista.addActionListener(this);
        btnRegistrarCancion.addActionListener(this);
        btnAgregarACancionALista.addActionListener(this);

        // Agregar botones al panel
        panelControles.add(btnReproducir);
        panelControles.add(btnPausar);
        panelControles.add(btnDetener);
        panelControles.add(btnSiguiente);
        panelControles.add(btnAnterior);
        panelControles.add(btnNuevaLista);
        panelControles.add(btnRegistrarArtista);
        panelControles.add(btnRegistrarCancion);
        panelControles.add(btnAgregarACancionALista);

        // Etiqueta de estado
        lblEstado = new JLabel("Estado: Detenido");
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);

        // Área de texto para mostrar canciones
        txtAreaCanciones = new JTextArea(10, 30);
        txtAreaCanciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCanciones);

        // Panel de volumen
        JPanel panelVolumen = new JPanel();
        panelVolumen.setLayout(new BorderLayout());

        // Slider de volumen
        volumenSlider = new JSlider(0, 100, 50);
        volumenSlider.setMajorTickSpacing(10);
        volumenSlider.setMinorTickSpacing(1);
        volumenSlider.setPaintTicks(true);
        volumenSlider.setPaintLabels(true);
        volumenSlider.addChangeListener(e -> {
            int volumen = volumenSlider.getValue();
            reproductor.ajustarVolumen(volumen);
            lblVolumen.setText("Volumen: " + volumen);
        });

        lblVolumen = new JLabel("Volumen: 50");
        lblVolumen.setHorizontalAlignment(SwingConstants.CENTER);

        panelVolumen.add(volumenSlider, BorderLayout.CENTER);
        panelVolumen.add(lblVolumen, BorderLayout.SOUTH);

        // Agregar componentes a la ventana
        this.add(panelControles, BorderLayout.NORTH);
        this.add(lblEstado, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.EAST);
        this.add(panelVolumen, BorderLayout.SOUTH);

        JPanel panelCarga = new JPanel();
        panelCarga.add(btnCargarArchivo);
        this.add(panelCarga, BorderLayout.WEST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Reproducir":
                reproductor.reproducir();
                lblEstado.setText("Estado: Reproduciendo - " + reproductor.getCancionActual().obtenerInformacion());
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
            case "Nueva Lista":
                crearNuevaLista();
                break;
            case "Registrar Artista":
                mostrarVentanaRegistrarArtista();
                break;
            case "Registrar Canción":
                mostrarVentanaRegistrarCancion();
                break;
            case "Agregar Canción a Lista":
                agregarCancionALista();
                break;
        }
    }

    private void cargarArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                boolean esPrimeraLinea = true; // Para omitir la primera línea si es un encabezado
                while ((linea = reader.readLine()) != null) {
                    // Omitir la primera línea si es un encabezado
                    if (esPrimeraLinea) {
                        esPrimeraLinea = false;
                        continue; // No hacer nada con la primera línea
                    }

                    String[] datos = linea.split(",");
                    if (datos.length == 5) {  // Asegurando que hay 5 columnas
                        try {
                            // Intentamos convertir la duración a un número entero
                            int duracion = Integer.parseInt(datos[2].trim());
                            Cancion nuevaCancion = new Cancion(datos[0].trim(), datos[1].trim(), duracion, datos[3].trim(), datos[4].trim());

                            // Obtener la lista de reproducción por nombre o crear una nueva
                            String nombreLista = datos[3].trim();  // Asumiendo que el nombre de la lista está en la columna 4 (índice 3)
                            ListaCanciones lista = reproductor.buscarListaPorNombre(nombreLista);

                            if (lista == null) {
                                // Si la lista no existe, crearla y agregarla al reproductor
                                lista = new ListaCanciones(nombreLista);
                                reproductor.agregarListaReproduccion(lista);
                                System.out.println("Lista creada: " + nombreLista); // Depuración
                            }

                            // Verifica que la lista no sea null antes de agregar la canción
                            if (lista != null) {
                                lista.agregarCancion(nuevaCancion);
                                System.out.println("Canción agregada: " + nuevaCancion.getTitulo()); // Depuración
                            } else {
                                System.err.println("No se pudo agregar la canción a la lista: " + nuevaCancion.getTitulo());
                            }

                        } catch (NumberFormatException e) {
                            // Si no se puede convertir la duración, se muestra un mensaje de error
                            JOptionPane.showMessageDialog(this, "Error en el formato de la duración en la línea: " + linea);
                        }
                    } else {
                        // Si la línea no tiene 5 elementos, la omite
                        System.err.println("Formato incorrecto en la línea: " + linea);
                    }
                }

                // Mostrar todas las canciones en la ventana
                actualizarListaCanciones();  // Actualizar la lista de canciones mostrada

                // También mostrar todas las líneas leídas en la ventana de texto
                txtAreaCanciones.setText("");  // Limpiar el área de texto antes de mostrar las nuevas canciones
                try (BufferedReader readerMostrar = new BufferedReader(new FileReader(archivo))) {
                    while ((linea = readerMostrar.readLine()) != null) {
                        // Mostramos las líneas en la ventana de texto
                        txtAreaCanciones.append(linea + "\n");
                    }
                }

                JOptionPane.showMessageDialog(this, "Archivo cargado exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar el archivo.");
                e.printStackTrace(); // Imprime el error completo en la consola para depuración
            }
        }
    }




    private void agregarCancionALista() {
        String nombreLista = JOptionPane.showInputDialog(this, "Introduce el nombre de la lista a la que deseas agregar la canción:");

        if (nombreLista != null && !nombreLista.trim().isEmpty()) {
            ListaCanciones listaSeleccionada = buscarListaPorNombre(nombreLista);

            if (listaSeleccionada != null) {
                String nombreCancion = JOptionPane.showInputDialog(this, "Introduce el nombre de la canción a agregar:");
                if (nombreCancion != null && !nombreCancion.trim().isEmpty()) {
                    Cancion cancionSeleccionada = buscarCancionPorNombre(nombreCancion);
                    if (cancionSeleccionada != null) {
                        listaSeleccionada.agregarCancion(cancionSeleccionada);
                        JOptionPane.showMessageDialog(this, "Canción agregada a la lista.");
                        // Actualiza la lista mostrada
                        listaCanciones = listaSeleccionada;
                        actualizarListaCanciones();  // Actualizar lista de canciones después de agregar
                    } else {
                        JOptionPane.showMessageDialog(this, "Canción no encontrada.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lista no encontrada.");
            }
        }
    }


    private void actualizarListaCanciones() {
        if (listaCanciones != null && listaCanciones.getCanciones() != null) {
            txtAreaCanciones.setText("");  // Limpiar el área de texto antes de actualizar
            for (Cancion cancion : listaCanciones.getCanciones()) {
                txtAreaCanciones.append(cancion.obtenerInformacion() + "\n");
            }
        } else {
            System.err.println("La listaCanciones está vacía o no se ha inicializado.");
        }
    }



    private ListaCanciones buscarListaPorNombre(String nombre) {
        return reproductor.buscarListaPorNombre(nombre);
    }

    private Cancion buscarCancionPorNombre(String nombre) {
        for (Cancion cancion : listaCanciones.getCanciones()) {
            if (cancion.getTitulo().equalsIgnoreCase(nombre)) {
                return cancion;
            }
        }
        return null;
    }

    private void crearNuevaLista() {
        String nombreLista = JOptionPane.showInputDialog(this, "Introduce el nombre de la nueva lista de reproducción:");
        if (nombreLista != null && !nombreLista.trim().isEmpty()) {
            ListaCanciones nuevaLista = new ListaCanciones(nombreLista);

            // Aquí se coloca la lógica para guardar el archivo en la carpeta
            try {
                // Asume que la carpeta se encuentra en src/main/resources/data
                String ruta = "src/main/resources/data/" + nombreLista + ".txt";
                File archivo = new File(ruta);

                // Crear el archivo si no existe
                if (!archivo.exists()) {
                    archivo.createNewFile();
                }

                // Guardar la lista en el archivo (esto se puede personalizar según el formato)
                // Ejemplo: Escribir el nombre de la lista en el archivo
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("Lista de reproducción: " + nombreLista);
                }

                // Agregar la lista a la colección del reproductor
                reproductor.agregarListaReproduccion(nuevaLista);

                JOptionPane.showMessageDialog(this, "Nueva lista creada y guardada.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar la lista: " + e.getMessage());
            }
        }
    }


    private void mostrarVentanaRegistrarArtista() {
        // Crear la ventana para ingresar los datos del artista
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));  // 4 filas, 2 columnas (uno para la etiqueta y otro para el campo de entrada)

        // Crear los componentes de la ventana
        JLabel lblNombre = new JLabel("Nombre del artista:");
        JTextField txtNombre = new JTextField();

        JLabel lblGenero = new JLabel("Género musical:");
        JTextField txtGenero = new JTextField("Desconocido");  // Valor predeterminado

        JLabel lblPais = new JLabel("País:");
        JTextField txtPais = new JTextField("Desconocido");  // Valor predeterminado

        // Agregar los componentes al panel
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblGenero);
        panel.add(txtGenero);
        panel.add(lblPais);
        panel.add(txtPais);

        // Botón para confirmar el registro
        int option = JOptionPane.showConfirmDialog(this, panel, "Registrar Artista", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String genero = txtGenero.getText().trim();
            String pais = txtPais.getText().trim();

            // Validación para asegurarse de que el nombre no esté vacío
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del artista no puede estar vacío.");
                return;
            }

            // Crear un nuevo objeto Artista
            Artista nuevoArtista = new Artista();
            nuevoArtista.setNombre(nombre);
            nuevoArtista.setGeneroMusical(genero.isEmpty() ? "Desconocido" : genero);  // Si no se ingresa un género, se usa "Desconocido"
            nuevoArtista.setPais(pais.isEmpty() ? "Desconocido" : pais);  // Si no se ingresa un país, se usa "Desconocido"

            // Guardar los datos del artista en un archivo CSV
            try {
                // Definir la ruta del archivo CSV
                String ruta = "src/main/resources/data/artistas.csv";
                File archivo = new File(ruta);

                // Si el archivo no existe, crear el archivo y agregar los encabezados
                if (!archivo.exists()) {
                    archivo.createNewFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                        writer.write("Nombre,Género,País");  // Encabezado CSV
                        writer.newLine();
                    }
                }

                // Escribir los datos del nuevo artista en el archivo CSV
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                    writer.write(nuevoArtista.getNombre() + "," + nuevoArtista.getGeneroMusical() + "," + nuevoArtista.getPais());
                    writer.newLine();
                }

                // Agregar el artista al reproductor (si es necesario)
                reproductor.agregarArtista(nuevoArtista);
                JOptionPane.showMessageDialog(this, "Artista registrado y guardado exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos del artista: " + e.getMessage());
            }
        }
    }




    private void guardarArtistaEnCSV(Artista artista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("artistas.csv", true))) {
            writer.println(artista.getNombre()); // Guardar nombre del artista
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el artista.");
        }
    }



    private void mostrarVentanaRegistrarCancion() {
        // Crear la ventana para ingresar los datos de la canción
        JDialog dialogoCancion = new JDialog(this, "Registrar Canción", true);
        dialogoCancion.setLayout(new GridLayout(6, 2));
        dialogoCancion.setSize(350, 300);

        JLabel lblNombreCancion = new JLabel("Nombre de la Canción:");
        JTextField txtNombreCancion = new JTextField();
        JLabel lblArtista = new JLabel("Nombre del Artista:");
        JTextField txtArtista = new JTextField();
        JLabel lblDuracion = new JLabel("Duración (segundos):");
        JTextField txtDuracion = new JTextField();
        JLabel lblAlbum = new JLabel("Álbum:");
        JTextField txtAlbum = new JTextField();
        JLabel lblGenero = new JLabel("Género:");
        JTextField txtGenero = new JTextField();
        JButton btnRegistrar = new JButton("Registrar");

        dialogoCancion.add(lblNombreCancion);
        dialogoCancion.add(txtNombreCancion);
        dialogoCancion.add(lblArtista);
        dialogoCancion.add(txtArtista);
        dialogoCancion.add(lblDuracion);
        dialogoCancion.add(txtDuracion);
        dialogoCancion.add(lblAlbum);
        dialogoCancion.add(txtAlbum);
        dialogoCancion.add(lblGenero);
        dialogoCancion.add(txtGenero);
        dialogoCancion.add(new JLabel());
        dialogoCancion.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            String nombreCancion = txtNombreCancion.getText();
            String nombreArtista = txtArtista.getText();
            String duracionStr = txtDuracion.getText();
            String album = txtAlbum.getText();
            String genero = txtGenero.getText();

            if (!nombreCancion.trim().isEmpty() && !nombreArtista.trim().isEmpty() && !duracionStr.trim().isEmpty()) {
                try {
                    int duracion = Integer.parseInt(duracionStr);
                    Cancion nuevaCancion = new Cancion(nombreCancion, new Artista(nombreArtista), duracion, album, genero);
                    listaCanciones.agregarCancion(nuevaCancion);
                    // Guardar canción en CSV
                    guardarCancionEnCSV(nuevaCancion);
                    JOptionPane.showMessageDialog(this, "Canción registrada.");
                    dialogoCancion.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "La duración debe ser un número.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            }
        });

        dialogoCancion.setVisible(true);
    }

    private void guardarCancionEnCSV(Cancion cancion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("canciones.csv", true))) {
            writer.println(cancion.getNombre() + "," + cancion.getArtista().getNombre() + "," + cancion.getDuracion() + "," + cancion.getAlbum() + "," + cancion.getGenero());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la canción.");
        }
    }



    public static void main(String[] args) {
        new MainWindow();
    }
}
