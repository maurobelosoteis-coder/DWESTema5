package com.example.app.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Pais;

@Service
public class PaisServiceImpl implements PaisService{
    
    private List<Pais> paises = new ArrayList<>();

    //Devuelve una lista sólo con los nombres de los países
    public List<String> getPaises() {
        List<String> nombrePaises = new ArrayList<>();
        for (Pais pais : paises) {
            nombrePaises.add(pais.getNombre());
        }
        return nombrePaises;
    }

    public Pais getPais(String nombre) {
        for (Pais pais : paises) {
            if (pais.getNombre().equals(nombre))
                return pais;
        }
        return null;
    }

    public boolean cargarPaisesDesdeFichero() {
        List<String> lineas;
        String[] partes = new String[3];
        try {
            /*Clase estática Paths sirve para obtener un objeto Path a partir de una cadena que representa una ruta de acceso.
            Sólo contiene un único método estático get().
            Objeto Path se usa para localizar un fichero en el sistema de ficheros.
            */
            Path path = Paths.get("paises2019.csv");
            //Clase estática Files que contiene métodos para leer, escribir y manipular ficheros y directorios.
            //readAllLines() lee todas las líneas de un fichero a través de un objeto Path.
            lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.printf("%nError:%s", ex.getMessage());
            return false;
        }
        for (String linea : lineas) {
            partes = linea.split(";");
            if (partes.length == 3)
                paises.add(new Pais(partes[0], partes[1], Long.parseLong(partes[2])));
            else
                return false;

        }
        return true;
    }

}
