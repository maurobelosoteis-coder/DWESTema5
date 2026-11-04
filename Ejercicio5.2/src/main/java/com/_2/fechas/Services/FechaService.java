package com._2.fechas.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FechaService {
    public Long calcularDiferenciaDias(LocalDate fecha1, LocalDate fecha2) {
        return java.time.temporal.ChronoUnit.DAYS.between(fecha1, fecha2);
    }

    public List<Integer> obtenerAniosBisiestos(LocalDate fecha1, LocalDate fecha2) {
        List<Integer> bisiestos = new ArrayList<>();
        int añoInicio = fecha1.getYear();
        int añoFin = fecha2.getYear();
        for (int año = añoInicio; año <= añoFin; año++) {
            if (esBisiesto(año)) {
                bisiestos.add(año);
            }
        }
        return bisiestos;
    }

    private boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    public List<Integer> contarDomingosEnero(LocalDate fecha1, LocalDate fecha2) {
        List<Integer> unoEneroDomingo = new ArrayList<>();
        int añoInicio = fecha1.getYear();
        int añoFin = fecha2.getYear();
        for (int año = añoInicio; año <= añoFin; año++) {
            LocalDate unoEnero = LocalDate.of(año, 1, 1);
            if (unoEnero.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
                unoEneroDomingo.add(año);
            }
        }
        return unoEneroDomingo;
    }

}
