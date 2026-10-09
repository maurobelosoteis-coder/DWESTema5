package com.example.app.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.app.domain.Pais;

@Service
public interface PaisService {
    public List<String> getPaises();
    public Pais getPais(String nombre);
    public boolean cargarPaisesDesdeFichero();
}
