package com.example.myapp;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service 
public class VotoService {
    
    private int[] votos = {0, 0, 0};

    private ArrayList<String> emails = new ArrayList<String>();

    public int[] getVotos() {
        return votos;
    }

    public boolean votarUnaVez(String email, int peli) {

        if (emails.contains(email)) {
            return false;
        }
        emails.add(email);
        votos[peli]++;
        return true;
    }

}