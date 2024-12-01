package org.example.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelInfo extends JPanel implements ActionListener {

    MainWindow miVentanaMadre;
    JTextArea txtLista;
    JTextField txtElemento;
    JButton btnCambiar;

    public PanelInfo(MainWindow VentanaMadre) {
        this.miVentanaMadre = VentanaMadre;

        // Inicializar los componentes
        txtLista = new JTextArea();
        txtElemento = new JTextField();
        btnCambiar = new JButton("Cambiar");

        // Establecer tamaño de los componentes para que tengan el mismo tamaño
        txtLista.setPreferredSize(new Dimension(300, 100));
        txtElemento.setPreferredSize(new Dimension(300, 30));
        btnCambiar.setPreferredSize(new Dimension(300, 40));

        // Usar BoxLayout para organizar los componentes verticalmente
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Centrar los componentes
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(txtLista);
        this.add(txtElemento);
        this.add(btnCambiar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Aquí iría la lógica del botón "Cambiar"
    }
}