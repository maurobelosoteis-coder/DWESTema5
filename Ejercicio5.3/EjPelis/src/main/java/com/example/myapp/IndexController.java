package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller // anotación controlador
public class IndexController {

    private VotoService votoService;

    public IndexController(VotoService votoService) {
        this.votoService = votoService;
    }

    @GetMapping({ "/", "/index" })
    public String showHome(Model model) {
        model.addAttribute("votos", votoService.getVotos());
        return "indexView";
    }

    @PostMapping("/voto")
    public String votar(@RequestParam String email, @RequestParam int peli, Model model) {

        boolean puedeVotar = votoService.votarUnaVez(email, peli);

        if(!puedeVotar){
            model.addAttribute("mensaje", "Este usuario ya ha votado");
            model.addAttribute("votos", votoService.getVotos());
            return "indexView";
        }

        return "redirect:/";
    }   
}
