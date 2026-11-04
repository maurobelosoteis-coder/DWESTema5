package com.example.app.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class MasterMind {
    private String numeroSecreto; // the winning number as String
    private ArrayList<Intento> listaIntentos = new ArrayList<>();; // list with objects from Intento.java
    private EstadoJuego estadoJuego;

    // current game's settings
    private int maxIntentos = 10; // max number of tries with default value
    private int tamNumero = 4; // number of digits to play with default value

    private String mensajeError; // last error message stored

    // returns the remaining guesses available. It is a derived value instead of a
    // count down so it's safer.
    public int getIntentosRestantes() {
        return maxIntentos - listaIntentos.size();
    }
}
