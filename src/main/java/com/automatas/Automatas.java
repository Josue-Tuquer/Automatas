/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.automatas;

import javax.swing.SwingUtilities;

public class Automatas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatosProyecto datosProyecto = new DatosProyecto();
            VentanaPrincipal ventana = new VentanaPrincipal(datosProyecto);
            ventana.setVisible(true);
        });
    }
}
