package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Pais;
import com.example.app.dto.FormInfo;
import com.example.app.services.PaisService;

@Controller
public class JuegoController {

    @Autowired
    private PaisService paisService;

    @GetMapping("/")
    public String showList(Model model) {
        // Envío al formulario un objeto FormInfo vacío
        model.addAttribute("formInfo", new FormInfo());
        // Envío al formulario la lista de países
        model.addAttribute("listaPaises", paisService.getPaises());
        return "/paisView";
    }

    @PostMapping("/")
    public String showNewSubmit(FormInfo formInfo, Model model) {

        // Si no se ha seleccionado un país, redirigimos a la página principal
        if (formInfo.getNombre().equals(""))
            return "redirect:/";
        // model.addAttribute("formInfo", new FormInfo());
        model.addAttribute("listaPaises", paisService.getPaises());
        Pais pais = paisService.getPais(formInfo.getNombre());

        model.addAttribute("pais", pais.getNombre());
        model.addAttribute("capital", pais.getCapital());
        model.addAttribute("poblacion", pais.getPoblacion());
        return "paisView";

    }
}
