package com._2.fechas.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com._2.fechas.Services.FechaService;

import jakarta.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private FechaService fechaService;
    
    @GetMapping({"/", "/myForm"})
    public String getMethodName(Model model) {
        model.addAttribute("formInfo", new formInfo());
        return "formView";
    }

    @PostMapping("/myForm/submit")
    public String postMethodName(@Valid formInfo formInfo, BindingResult result, Model model, String submitButton) { //String submitButton para saber que botón presionó
       
        
        if (result.hasErrors()) {
            return "formView";
        }

        // Validación adicional: fecha2 después de fecha1
        if (formInfo.getFecha2().isBefore(formInfo.getFecha1())) {
            model.addAttribute("txtErr", "La segunda fecha debe ser posterior a la primera.");
            return "formView";
        }

        // resultado según botón pulsado
        String resultado = "";

        if ("diferencia".equals(submitButton)) {
            Long dias = fechaService.calcularDiferenciaDias(formInfo.getFecha1(), formInfo.getFecha2());
            resultado = "La diferencia de días es: " + dias;
        } else if ("bisiestos".equals(submitButton)) {
            List<Integer> bisiestos = fechaService.obtenerAniosBisiestos(formInfo.getFecha1(), formInfo.getFecha2());
            resultado = "Años bisiestos: " + bisiestos;
        } else if ("domingo".equals(submitButton)) {
            List<Integer> domingos = fechaService.contarDomingosEnero(formInfo.getFecha1(), formInfo.getFecha2());
            resultado = "Años con 1 de enero domingo: " + domingos;
        }


        model.addAttribute("formInfo", formInfo);
        model.addAttribute("resultado", resultado);
        return "formSubmitView";
    }

}
