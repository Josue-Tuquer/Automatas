package com.automatas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Mantiene en memoria los AFD y las gramáticas creados durante la ejecución. */
public class DatosProyecto {

    private final List<AutomataFinitoDeterminista> automatas = new ArrayList<>();
    private final List<GramaticaRegular> gramaticas = new ArrayList<>();

    public void agregarAutomata(AutomataFinitoDeterminista automata) {
        automatas.add(automata);
    }

    public void agregarGramatica(GramaticaRegular gramatica) {
        gramaticas.add(gramatica);
    }

    public List<AutomataFinitoDeterminista> getAutomatas() {
        return Collections.unmodifiableList(automatas);
    }

    public List<GramaticaRegular> getGramaticas() {
        return Collections.unmodifiableList(gramaticas);
    }
}
