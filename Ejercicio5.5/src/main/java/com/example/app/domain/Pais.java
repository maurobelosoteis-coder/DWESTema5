package com.example.app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pais  {
    private String nombre;
    private String capital;
    private Long poblacion;
   
    // public Pais(String nombre, String capital, Long poblacion) {
    //     this.nombre = nombre;
    //     this.capital = capital;
    //     this.poblacion = poblacion;
    // }

    // @Override
    // public String toString() {
    //     return "Pais [nombre=" + nombre + ", capital=" + capital + ", poblacion=" + poblacion + "]";
    // }


}
