package com.automatas;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Selecciona un AFD guardado y evalúa una cadena. */
public class DialogoEvaluarCadenas extends JDialog {

    private final JComboBox<AutomataFinitoDeterminista> selector = new JComboBox<>();
    private final JTextField cadena = FormularioUI.crearCampo();

    public DialogoEvaluarCadenas(Window propietario, DatosProyecto datosProyecto) {
        super(propietario, "Evaluar cadenas", ModalityType.APPLICATION_MODAL);
        for (AutomataFinitoDeterminista automata : datosProyecto.getAutomatas()) {
            selector.addItem(automata);
        }
        construirFormulario();
    }

    private void construirFormulario() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel contenido = new JPanel(new BorderLayout(10, 14));
        contenido.setBorder(BorderFactory.createEmptyBorder(22, 24, 18, 24));
        JPanel campos = FormularioUI.crearPanel();
        FormularioUI.agregarFila(campos, 0, "AFD:", selector);
        FormularioUI.agregarFila(campos, 1, "Cadena:", cadena);
        JLabel ayuda = new JLabel("Deja la cadena vacía para evaluar ε (cadena vacía).");
        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.add(campos, BorderLayout.CENTER);
        centro.add(ayuda, BorderLayout.SOUTH);

        JPanel botones = new JPanel();
        JButton evaluar = new JButton("Evaluar");
        JButton cerrar = new JButton("Cerrar");
        evaluar.addActionListener(e -> evaluarCadena());
        cerrar.addActionListener(e -> dispose());
        botones.add(evaluar);
        botones.add(cerrar);

        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(botones, BorderLayout.SOUTH);
        setContentPane(contenido);
        setSize(510, 250);
        setLocationRelativeTo(getOwner());
    }

    private void evaluarCadena() {
        AutomataFinitoDeterminista automata = (AutomataFinitoDeterminista) selector.getSelectedItem();
        if (automata == null) {
            JOptionPane.showMessageDialog(this, "Primero crea un AFD desde la pantalla principal.",
                    "No hay AFD disponibles", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String valor = cadena.getText();
        boolean aceptada = automata.evaluar(valor);
        String resultado = aceptada ? "VÁLIDA / ACEPTADA" : "INVÁLIDA / RECHAZADA";
        JOptionPane.showMessageDialog(this,
                "AFD: " + automata.getNombre() + "\nCadena: " + (valor.isEmpty() ? "ε (vacía)" : valor)
                + "\nResultado: " + resultado,
                "Resultado de evaluación", aceptada ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
}
