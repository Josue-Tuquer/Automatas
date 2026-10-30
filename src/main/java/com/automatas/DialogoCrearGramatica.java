package com.automatas;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Formulario para capturar una gramática regular. */
public class DialogoCrearGramatica extends JDialog {

    private final DatosProyecto datosProyecto;
    private final JTextField nombre = FormularioUI.crearCampo();
    private final JTextField noTerminales = FormularioUI.crearCampo();
    private final JTextField terminales = FormularioUI.crearCampo();
    private final JTextField inicial = FormularioUI.crearCampo();
    private final JTextArea producciones = FormularioUI.crearArea(7);

    public DialogoCrearGramatica(Window propietario, DatosProyecto datosProyecto) {
        super(propietario, "Crear gramática regular", ModalityType.APPLICATION_MODAL);
        this.datosProyecto = datosProyecto;
        construirFormulario();
    }

    private void construirFormulario() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel campos = FormularioUI.crearPanel();
        FormularioUI.agregarFila(campos, 0, "Nombre:", nombre);
        FormularioUI.agregarFila(campos, 1, "No terminales:", noTerminales);
        FormularioUI.agregarFila(campos, 2, "Terminales:", terminales);
        FormularioUI.agregarFila(campos, 3, "Símbolo inicial:", inicial);
        FormularioUI.agregarFila(campos, 4, "Producciones:", new JScrollPane(producciones));

        JPanel pie = new JPanel();
        JButton guardar = new JButton("Guardar gramática");
        JButton cancelar = new JButton("Cancelar");
        guardar.addActionListener(e -> guardarGramatica());
        cancelar.addActionListener(e -> dispose());
        pie.add(guardar);
        pie.add(cancelar);

        contenido.add(new JScrollPane(campos), BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);
        setContentPane(contenido);
        setSize(630, 520);
        setLocationRelativeTo(getOwner());
    }

    private void guardarGramatica() {
        try {
            GramaticaRegular gramatica = new GramaticaRegular(nombre.getText(),
                    noTerminales.getText(), terminales.getText(), inicial.getText(), producciones.getText());
            datosProyecto.agregarGramatica(gramatica);
            JOptionPane.showMessageDialog(this, "La gramática se guardó en la sesión actual.",
                    "Gramática creada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Revisa los datos", JOptionPane.WARNING_MESSAGE);
        }
    }
}
