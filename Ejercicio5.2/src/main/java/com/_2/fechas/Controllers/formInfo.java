package com._2.fechas.Controllers;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;


public class formInfo {
    
	@NotNull(message = "La primera fecha no puede estar vacía.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha1;
    
	@NotNull(message = "La segunda fecha no puede estar vacía.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha2;

    public LocalDate getFecha1() {
		return fecha1;
	}

	public void setFecha1(LocalDate fecha1) {
		this.fecha1 = fecha1;
	}
    public LocalDate getFecha2() {
		return fecha2;
	}

	public void setFecha2(LocalDate fecha2) {
		this.fecha2 = fecha2;
	}
}
