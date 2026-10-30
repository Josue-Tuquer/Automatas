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

/** Formulario para definir y guardar un AFD en la sesión actual. */
public class DialogoCrearAFD extends JDialog {

    private final DatosProyecto datosProyecto;
    private final JTextField nombre = FormularioUI.crearCampo();
    private final JTextField estados = FormularioUI.crearCampo();
    private final JTextField alfabeto = FormularioUI.crearCampo();
    private final JTextField inicial = FormularioUI.crearCampo();
    private final JTextField aceptacion = FormularioUI.crearCampo();
    private final JTextArea transiciones = FormularioUI.crearArea(6);

    public DialogoCrearAFD(Window propietario, DatosProyecto datosProyecto) {
        super(propietario, "Crear AFD", ModalityType.APPLICATION_MODAL);
        this.datosProyecto = datosProyecto;
        construirFormulario();
    }

    private void construirFormulario() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel campos = FormularioUI.crearPanel();
        FormularioUI.agregarFila(campos, 0, "Nombre:", nombre);
        FormularioUI.agregarFila(campos, 1, "Estados (coma):", estados);
        FormularioUI.agregarFila(campos, 2, "Alfabeto (coma):", alfabeto);
        FormularioUI.agregarFila(campos, 3, "Estado inicial:", inicial);
        FormularioUI.agregarFila(campos, 4, "Estados de aceptación:", aceptacion);
        FormularioUI.agregarFila(campos, 5, "Transiciones:", new JScrollPane(transiciones));

        JPanel pie = new JPanel();
        JButton guardar = new JButton("Guardar AFD");
        JButton cancelar = new JButton("Cancelar");
        guardar.addActionListener(e -> guardarAutomata());
        cancelar.addActionListener(e -> dispose());
        pie.add(guardar);
        pie.add(cancelar);

        contenido.add(new JScrollPane(campos), BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);
        contenido.setBorder(BorderFactory.createCompoundBorder(
                contenido.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        setContentPane(contenido);
        setSize(650, 570);
        setLocationRelativeTo(getOwner());
    }

    private void guardarAutomata() {
        try {
            AutomataFinitoDeterminista automata = new AutomataFinitoDeterminista(
                    nombre.getText(), estados.getText(), alfabeto.getText(), inicial.getText(),
                    aceptacion.getText(), transiciones.getText());
            datosProyecto.agregarAutomata(automata);
            JOptionPane.showMessageDialog(this, "El AFD se guardó en la sesión actual.",
                    "AFD creado", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Revisa los datos", JOptionPane.WARNING_MESSAGE);
        }
    }
}
