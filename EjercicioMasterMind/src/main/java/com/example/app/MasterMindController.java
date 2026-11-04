package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.model.FormConfig;
import com.example.app.model.FormInfo;

import jakarta.validation.Valid;

@Controller
@Scope("session")
public class MasterMindController {

    @Autowired
    private MasterMindService masterMindService;

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("formConfig", new FormConfig());

        return "indexView";
    }

    @GetMapping("/juego")
    public String showGame(Model model) {
        if (!masterMindService.hayPartida()) {
            return "redirect:/";    // no game in this session: back to the start page
        }
        model.addAttribute("formInfo", new FormInfo());
        model.addAttribute("listaIntentos", masterMindService.getListaIntentos());
        model.addAttribute("estadoJuego", masterMindService.getEstadoJuego());
        model.addAttribute("intentosRestantes", masterMindService.getIntentosRestantes());
        model.addAttribute("mensajeError", masterMindService.getMensajeError());
        model.addAttribute("tamNumero", masterMindService.getTamNumero());
        model.addAttribute("numeroSecreto", masterMindService.getNumeroSecretoSiTerminada());
        return "juegoView";
    }

    @PostMapping("/juego")
    public String newTry(@ModelAttribute FormInfo formInfo) {
        if (!masterMindService.hayPartida()) {
            return "redirect:/";    // session expired or server restarted
        }
        masterMindService.comprobarIntento(formInfo.getIntento());
        return "redirect:/juego";
    }

    @GetMapping("/nuevoJuego")
    public String newGame() {
        return "redirect:/";
    }

    @PostMapping("/nuevoJuego")
    public String newGameConfig(@Valid @ModelAttribute FormConfig formConfig, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "indexView";
        } else{ 
            masterMindService.nuevoJuego(formConfig.getMaxIntent(), formConfig.getNumLength());
            return "redirect:/juego";
        }        
    }
    
}
