package com.automatas;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Métodos visuales compartidos por los formularios. */
final class FormularioUI {

    private FormularioUI() {
    }

    static JPanel crearPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        return panel;
    }

    static void agregarFila(JPanel panel, int fila, String etiqueta, Component componente) {
        GridBagConstraints etiquetaGbc = new GridBagConstraints();
        etiquetaGbc.gridx = 0;
        etiquetaGbc.gridy = fila;
        etiquetaGbc.anchor = GridBagConstraints.NORTHWEST;
        etiquetaGbc.insets = new Insets(7, 0, 7, 12);
        panel.add(new JLabel(etiqueta), etiquetaGbc);

        GridBagConstraints componenteGbc = new GridBagConstraints();
        componenteGbc.gridx = 1;
        componenteGbc.gridy = fila;
        componenteGbc.weightx = 1;
        componenteGbc.fill = GridBagConstraints.HORIZONTAL;
        componenteGbc.anchor = GridBagConstraints.NORTHWEST;
        componenteGbc.insets = new Insets(4, 0, 4, 0);
        panel.add(componente, componenteGbc);
    }

    static JTextField crearCampo() {
        JTextField campo = new JTextField(28);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return campo;
    }

    static JTextArea crearArea(int filas) {
        JTextArea area = new JTextArea(filas, 28);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }
}
