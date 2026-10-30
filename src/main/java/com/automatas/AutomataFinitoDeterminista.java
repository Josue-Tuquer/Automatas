package com.automatas;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/** Modelo de un AFD y su función de transición. */
public class AutomataFinitoDeterminista {

    private final String nombre;
    private final Set<String> estados;
    private final Set<Character> alfabeto;
    private final String estadoInicial;
    private final Set<String> estadosAceptacion;
    private final Map<String, Map<Character, String>> transiciones;

    public AutomataFinitoDeterminista(String nombre, String estadosTexto,
            String alfabetoTexto, String estadoInicial, String aceptacionTexto,
            String transicionesTexto) {
        this.nombre = validarTexto(nombre, "El nombre del AFD");
        this.estados = parsearElementos(estadosTexto, "los estados");
        this.alfabeto = parsearAlfabeto(alfabetoTexto);
        this.estadoInicial = validarTexto(estadoInicial, "El estado inicial");
        this.estadosAceptacion = parsearElementos(aceptacionTexto, "los estados de aceptación");
        this.transiciones = new LinkedHashMap<>();

        if (!estados.contains(this.estadoInicial)) {
            throw new IllegalArgumentException("El estado inicial debe aparecer en la lista de estados.");
        }
        if (!estados.containsAll(estadosAceptacion)) {
            throw new IllegalArgumentException("Todos los estados de aceptación deben estar en la lista de estados.");
        }
        cargarTransiciones(transicionesTexto);
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.trim();
    }

    private static Set<String> parsearElementos(String texto, String campo) {
        Set<String> resultado = new LinkedHashSet<>();
        if (texto != null) {
            for (String elemento : texto.split(",")) {
                if (!elemento.trim().isEmpty()) {
                    resultado.add(elemento.trim());
                }
            }
        }
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("Debes indicar " + campo + " separados por comas.");
        }
        return resultado;
    }

    private static Set<Character> parsearAlfabeto(String texto) {
        Set<Character> resultado = new LinkedHashSet<>();
        for (String simbolo : texto == null ? new String[0] : texto.split(",")) {
            String limpio = simbolo.trim();
            if (!limpio.isEmpty()) {
                if (limpio.length() != 1) {
                    throw new IllegalArgumentException("Cada símbolo del alfabeto debe ser un solo carácter.");
                }
                resultado.add(limpio.charAt(0));
            }
        }
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("Debes indicar el alfabeto separado por comas, por ejemplo: a, b.");
        }
        return resultado;
    }

    private void cargarTransiciones(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar al menos una transición.");
        }
        int numeroLinea = 0;
        for (String linea : texto.split("\\R")) {
            numeroLinea++;
            if (linea.trim().isEmpty()) {
                continue;
            }
            String[] partes = linea.split(",");
            if (partes.length != 3) {
                throw new IllegalArgumentException("Transición en línea " + numeroLinea
                        + ": usa el formato estado, símbolo, destino (ejemplo: q0, a, q1).");
            }
            String origen = partes[0].trim();
            String simboloTexto = partes[1].trim();
            String destino = partes[2].trim();
            if (simboloTexto.length() != 1 || !alfabeto.contains(simboloTexto.charAt(0))) {
                throw new IllegalArgumentException("El símbolo de la línea " + numeroLinea + " no pertenece al alfabeto.");
            }
            if (!estados.contains(origen) || !estados.contains(destino)) {
                throw new IllegalArgumentException("El origen y destino de la línea " + numeroLinea
                        + " deben existir en la lista de estados.");
            }
            char simbolo = simboloTexto.charAt(0);
            Map<Character, String> porSimbolo = transiciones.computeIfAbsent(origen, llave -> new LinkedHashMap<>());
            if (porSimbolo.putIfAbsent(simbolo, destino) != null) {
                throw new IllegalArgumentException("El AFD ya tiene una transición para " + origen + " y " + simbolo + ".");
            }
        }
        if (transiciones.isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar al menos una transición.");
        }
    }

    public boolean evaluar(String cadena) {
        String estadoActual = estadoInicial;
        for (int indice = 0; indice < cadena.length(); indice++) {
            char simbolo = cadena.charAt(indice);
            if (!alfabeto.contains(simbolo)) {
                return false;
            }
            Map<Character, String> porSimbolo = transiciones.get(estadoActual);
            if (porSimbolo == null || !porSimbolo.containsKey(simbolo)) {
                return false;
            }
            estadoActual = porSimbolo.get(simbolo);
        }
        return estadosAceptacion.contains(estadoActual);
    }

    public String getNombre() {
        return nombre;
    }

    public Set<String> getEstados() {
        return Collections.unmodifiableSet(estados);
    }

    public Set<Character> getAlfabeto() {
        return Collections.unmodifiableSet(alfabeto);
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Set<String> getEstadosAceptacion() {
        return Collections.unmodifiableSet(estadosAceptacion);
    }

    public Map<String, Map<Character, String>> getTransiciones() {
        Map<String, Map<Character, String>> copia = new LinkedHashMap<>();
        transiciones.forEach((estado, porSimbolo) ->
                copia.put(estado, Collections.unmodifiableMap(porSimbolo)));
        return Collections.unmodifiableMap(copia);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
