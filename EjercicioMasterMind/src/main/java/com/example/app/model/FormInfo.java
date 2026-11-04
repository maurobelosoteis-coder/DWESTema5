package com.example.app.model;

public class FormInfo {

     // The user's guess. Kept as a String: like this we can compare digit by digit, 
     // it's not a number to calculate with. ! 0123 as number would become 123. !
    private String intento;

    public String getIntento() {
        return intento;
    }

    public void setIntento(String intento) {
        this.intento = intento;
    }

}
