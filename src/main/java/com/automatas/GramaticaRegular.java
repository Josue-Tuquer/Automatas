package com.automatas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Modelo básico para registrar una gramática regular. */
public class GramaticaRegular {

    private final String nombre;
    private final String noTerminales;
    private final String terminales;
    private final String simboloInicial;
    private final List<String> producciones;

    public GramaticaRegular(String nombre, String noTerminales, String terminales,
            String simboloInicial, String produccionesTexto) {
        this.nombre = requerido(nombre, "El nombre de la gramática");
        this.noTerminales = requerido(noTerminales, "Los no terminales");
        this.terminales = requerido(terminales, "Los terminales");
        this.simboloInicial = requerido(simboloInicial, "El símbolo inicial");
        if (produccionesTexto == null || produccionesTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar al menos una producción.");
        }
        this.producciones = new ArrayList<>();
        for (String linea : produccionesTexto.split("\\R")) {
            if (!linea.trim().isEmpty()) {
                this.producciones.add(linea.trim());
            }
        }
    }

    private static String requerido(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public String getNoTerminales() {
        return noTerminales;
    }

    public String getTerminales() {
        return terminales;
    }

    public String getSimboloInicial() {
        return simboloInicial;
    }

    public List<String> getProducciones() {
        return Collections.unmodifiableList(producciones);
    }
}
