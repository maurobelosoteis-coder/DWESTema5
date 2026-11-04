package com.example.app.model;

    //Each VALID guess is stored as an object (and added to the listaIntentos)
public class Intento {

    private String combinacion;  // user's guess
    private int bienColocados;   
    private int malColocados;

    public Intento(String combinacion, int bienColocados, int malColocados) {
        this.combinacion = combinacion;
        this.bienColocados = bienColocados;
        this.malColocados = malColocados;
    }

    public String getCombinacion() {
        return combinacion;
    }

    public int getBienColocados() {
        return bienColocados;
    }

    public int getMalColocados() {
        return malColocados;
    }

 /* Inherited Setters not used anywhere in the current flow (checked with Find All References).
    A scored guess never changes after it's created, so without setters
    the object is effectively IMMUTABLE: it can only be filled once, by the constructor. 

    public void setCombinacion(String combinacion) {
        this.combinacion = combinacion;
    }

    public void setBienColocados(int bienColocados) {
        this.bienColocados = bienColocados;
    }

    public void setMalColocados(int malColocados) {
        this.malColocados = malColocados;
    } */

}
