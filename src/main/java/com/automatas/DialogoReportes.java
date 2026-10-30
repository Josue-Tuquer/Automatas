package com.automatas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** Presenta un resumen de los datos creados durante la sesión. */
public class DialogoReportes extends JDialog {

    public DialogoReportes(Window propietario, DatosProyecto datosProyecto) {
        super(propietario, "Reportes del proyecto", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea reporte = new JTextArea(generarReporte(datosProyecto));
        reporte.setEditable(false);
        reporte.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        reporte.setCaretPosition(0);
        reporte.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.add(cerrar);

        setLayout(new BorderLayout(0, 8));
        add(new JScrollPane(reporte), BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
        setSize(650, 510);
        setLocationRelativeTo(propietario);
    }

    private String generarReporte(DatosProyecto datosProyecto) {
        StringBuilder texto = new StringBuilder("REPORTE DE LA SESIÓN\n");
        texto.append("====================\n\n");
        texto.append("AFD registrados: ").append(datosProyecto.getAutomatas().size()).append("\n");
        texto.append("Gramáticas registradas: ").append(datosProyecto.getGramaticas().size()).append("\n\n");

        if (datosProyecto.getAutomatas().isEmpty()) {
            texto.append("No se han creado AFD.\n\n");
        } else {
            texto.append("AUTÓMATAS FINITOS DETERMINISTAS\n");
            for (AutomataFinitoDeterminista automata : datosProyecto.getAutomatas()) {
                texto.append("\n- ").append(automata.getNombre()).append("\n")
                        .append("  Estados: ").append(automata.getEstados()).append("\n")
                        .append("  Alfabeto: ").append(automata.getAlfabeto()).append("\n")
                        .append("  Estado inicial: ").append(automata.getEstadoInicial()).append("\n")
                        .append("  Aceptación: ").append(automata.getEstadosAceptacion()).append("\n")
                        .append("  Transiciones:\n");
                for (Map.Entry<String, Map<Character, String>> estado : automata.getTransiciones().entrySet()) {
                    estado.getValue().forEach((simbolo, destino) -> texto.append("    ")
                            .append(estado.getKey()).append(" --").append(simbolo).append("--> ")
                            .append(destino).append("\n"));
                }
            }
            texto.append("\n");
        }

        if (datosProyecto.getGramaticas().isEmpty()) {
            texto.append("No se han creado gramáticas.\n");
        } else {
            texto.append("GRAMÁTICAS REGULARES\n");
            for (GramaticaRegular gramatica : datosProyecto.getGramaticas()) {
                texto.append("\n- ").append(gramatica.getNombre()).append("\n")
                        .append("  No terminales: ").append(gramatica.getNoTerminales()).append("\n")
                        .append("  Terminales: ").append(gramatica.getTerminales()).append("\n")
                        .append("  Símbolo inicial: ").append(gramatica.getSimboloInicial()).append("\n")
                        .append("  Producciones:\n");
                for (String produccion : gramatica.getProducciones()) {
                    texto.append("    ").append(produccion).append("\n");
                }
            }
        }
        texto.append("\nNota: los datos se conservan en memoria mientras el programa está abierto.");
        return texto.toString();
    }
}
