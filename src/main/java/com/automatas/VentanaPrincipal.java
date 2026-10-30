package com.automatas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Ventana principal y punto de navegación de la aplicación. */
public class VentanaPrincipal extends JFrame {

    private static final Color AZUL_OSCURO = new Color(20, 35, 63);
    private static final Color FONDO = new Color(243, 246, 250);
    private final DatosProyecto datosProyecto;

    public VentanaPrincipal(DatosProyecto datosProyecto) {
        this.datosProyecto = datosProyecto;
        configurarVentana();
        construirPantalla();
    }

    private void configurarVentana() {
        setTitle("Autómatas y Lenguajes Formales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 640);
        setMinimumSize(new Dimension(760, 560));
        setLocationRelativeTo(null);
    }

    private void construirPantalla() {
        JPanel principal = new JPanel(new BorderLayout(0, 22));
        principal.setBackground(FONDO);
        principal.setBorder(BorderFactory.createEmptyBorder(30, 38, 24, 38));

        JPanel encabezado = new JPanel(new BorderLayout(0, 8));
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("PROYECTO FINAL AUTÓMATAS");
        titulo.setForeground(AZUL_OSCURO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 27));
        //JLabel subtitulo = new JLabel("Crea gramáticas y AFD, y prueba cadenas de forma interactiva.");
        //subtitulo.setForeground(new Color(90, 103, 122));
        //subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        encabezado.add(titulo, BorderLayout.NORTH);
        //encabezado.add(subtitulo, BorderLayout.CENTER);

        JPanel opciones = new JPanel(new GridLayout(2, 3, 14, 14));
        opciones.setOpaque(false);
        opciones.add(crearBoton("01", "CREAR AFD", "Define estados, alfabeto y transiciones.",
                e -> new DialogoCrearAFD(this, datosProyecto).setVisible(true)));
        opciones.add(crearBoton("02", "CREAR GRAMÁTICA", "Registra no terminales y producciones.",
                e -> new DialogoCrearGramatica(this, datosProyecto).setVisible(true)));
        opciones.add(crearBoton("03", "EVALUAR CADENAS", "Comprueba una cadena en un AFD guardado.",
                e -> new DialogoEvaluarCadenas(this, datosProyecto).setVisible(true)));
        opciones.add(crearBoton("04", "REPORTES", "Consulta los elementos creados en esta sesión.",
                e -> new DialogoReportes(this, datosProyecto).setVisible(true)));
        opciones.add(crearBoton("05", "CARGAR ARCHIVOS", "Abre un archivo de gramática .gtk.",
                e -> cargarArchivoGtk()));
        opciones.add(crearBoton("06", "SALIR / CERRAR PROGRAMA", "Cierra la aplicación.",
                e -> confirmarSalida()));

        //JLabel pie = new JLabel("Seleccione una opción para continuar");
        //pie.setForeground(new Color(112, 124, 141));
        //pie.setFont(new Font("SansSerif", Font.PLAIN, 13));

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(opciones, BorderLayout.CENTER);
        //principal.add(pie, BorderLayout.SOUTH);
        setContentPane(principal);
    }

    private JButton crearBoton(String numero, String titulo, String descripcion, ActionListener accion) {
        String texto = "<html><div style='padding:8px 5px'>"
                + "<span style='color:#5379ba;font-size:12px;font-weight:bold'>" + numero + "</span><br>"
                + "<span style='font-size:15px;font-weight:bold;color:#14233f'>" + titulo + "</span><br>"
                + "<span style='font-size:12px;color:#5a677a'>" + descripcion + "</span></div></html>";
        JButton boton = new JButton(texto);
        boton.setHorizontalAlignment(JButton.LEFT);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 235)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        boton.addActionListener(accion);
        return boton;
    }

    private void cargarArchivoGtk() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de gramática (.gtk)");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de gramática (*.gtk)", "gtk"));
        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            String contenido = Files.readString(selector.getSelectedFile().toPath(), StandardCharsets.UTF_8);
            JTextArea area = new JTextArea(contenido, 22, 65);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            JOptionPane.showMessageDialog(this, scroll,
                    "Contenido de " + selector.getSelectedFile().getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo leer el archivo: " + ex.getMessage(),
                    "Error al cargar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar el programa?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
