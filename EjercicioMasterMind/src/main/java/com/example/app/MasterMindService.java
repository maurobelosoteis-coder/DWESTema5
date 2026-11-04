package com.example.app;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.app.model.EstadoJuego;
import com.example.app.model.Intento;
import com.example.app.model.MasterMind;

@Service
@Scope("session")
public class MasterMindService {

    private MasterMind masterMind;

    // ---- Read-only access for the controller ----
    // The controller asks the service; it never touches MasterMind directly,
    // so it can't bypass the game rules (e.g. by calling setEstadoJuego).

    public boolean hayPartida() {
        return masterMind != null; // is there a game in this session?
    }

    public List<Intento> getListaIntentos() {
        return Collections.unmodifiableList(masterMind.getListaIntentos()); // read-only view, like 5.3
    }

    // The original code had public fields, so the controller could reach into the
    // service’s game object and even change its state.
    // I made them private and gave the service these read-only methods, so the only
    // way to change the game is through @Service's rules.”
    public EstadoJuego getEstadoJuego() {
        return masterMind.getEstadoJuego();
    }

    public int getIntentosRestantes() {
        return masterMind.getIntentosRestantes();
    }

    public int getTamNumero() {
        return masterMind.getTamNumero();
    }

    public String getMensajeError() {
        return masterMind.getMensajeError();
    }

    public void nuevoJuego(int maxIntent, int numLength) {
        masterMind = new MasterMind();
        masterMind.setTamNumero(numLength);
        masterMind.setMaxIntentos(maxIntent);
        do {
            masterMind.setNumeroSecreto(cadenaAlAzar(masterMind.getTamNumero()));
        } while (cadenaConDuplicados(masterMind.getNumeroSecreto()));
        // masterMind.getListaIntentos().clear(); unnecesery line because line 19
        // already sets the list empty
        masterMind.setEstadoJuego(EstadoJuego.JUGANDO);
        System.out.println("=====> Num secreto: " + masterMind.getNumeroSecreto()); // solo para testing
    }

    public void comprobarIntento(String intento) {
        // Guard: no game in this session (e.g. session expired after inactivity, or the
        // server restarted while the old page was still open).
        // Or someone sending the POST by hand... Without this check, a guess sent would
        // crash below.
        if (masterMind == null)
            return; // means no game in this session: comprobarIntento -> newTry: return
                    // "redirect:/juego" -> showGame: has masterMind == null -> redirect:/ -> back
                    // to the start

        masterMind.setMensajeError(null);
        if (masterMind.getEstadoJuego() != EstadoJuego.JUGANDO) {
            masterMind.setMensajeError("La partida ha terminado. Pulsa Inicio para jugar otra.");
            return;
        }
        if (intento == null || !intento.matches("\\d+")) {
            masterMind.setMensajeError("Solo se permiten números");
            return;
        }
        if (intento.length() != masterMind.getTamNumero()) {
            masterMind.setMensajeError("Debe tener " + masterMind.getTamNumero() + " cifras");
            return;
        }
        if (cadenaConDuplicados(intento)) {
            masterMind.setMensajeError("No se permiten cifras repetidas");
            return;
        }
        for (Intento previo : masterMind.getListaIntentos()) {
            if (previo.getCombinacion().equals(intento)) {
                masterMind.setMensajeError("Ya has probado ese número");
                return;
            }
        }
        // ===== HAPPY PATH Begins =====
        int bienColocados = 0, malColocados = 0; // reset values
        String numeroSecreto = masterMind.getNumeroSecreto(); // reads the winning number
        for (int cont = 0; cont < intento.length(); cont++) { // iteration that lasts the lenght of the usesr num which
                                                              // has already been tested against this game’s length
            char letra = intento.charAt(cont); // reads in the n-essima letter(number in string format) from user num
            if (letra == numeroSecreto.charAt(cont)) // checks user's letter against the letter(number) in same same
                                                     // position in the solution
                bienColocados++; // if true -> increment corresponding variable
            else if (numeroSecreto.indexOf(letra) != -1) // returns the position (0 or more) if found, or -1 if not
                malColocados++; // so if the return int of indexOF is not negative, this user's number is
                                // present in the solution and increments corresponding variable
        }
        // the next line creates the object Intento then stores (appends) it in the
        // dedicated list (listaIntentos)
        // here we can see that lista de intentos is not only the user's number but the
        // 2 associated counter too
        masterMind.getListaIntentos().add(new Intento(intento, bienColocados, malColocados));
        if (bienColocados == masterMind.getTamNumero()) // checks if the bien colocados == length of the secret number
            masterMind.setEstadoJuego(EstadoJuego.GANADO); // changes the state to WIN
        // next line was IF in original. So if we had won on the last turn, it would be
        // overruled and return lost anywhay
        else if (masterMind.getListaIntentos().size() >= masterMind.getMaxIntentos()) // if had played the reps
                                                                                      // available
            masterMind.setEstadoJuego(EstadoJuego.PERDIDO); // sets the state to LOOSE
    }

    // to test if a number contains double digits (e.g 1212/6889/4724...)
    private boolean cadenaConDuplicados(String cad) {
        for (int i = 0; i < cad.length(); i++) { // runs once per character
            char c = cad.charAt(i); // stores caracter at given position
            if (cad.indexOf(c, i + 1) != -1) // checks if the letter is repeated anywhere from position i+1 to the end
                return true;
        }
        return false;
    }

    // to create a new winning number. tamanho stores the exact lenght the number
    // needs to have in current game
    private String cadenaAlAzar(int tamanho) {
        Random random = new Random(); // uses an instance of the random class
        String cad = "";
        int x;
        for (int i = 0; i < tamanho; i++) {
            x = random.nextInt(10); // generates a rundom number from 0 to 9
            cad += Integer.toString(x); // converts int to string
        }
        return cad;
    }

    public String getNumeroSecretoSiTerminada() {
        if (masterMind.getEstadoJuego() == EstadoJuego.JUGANDO) {
            return null; // never reveal it during the game
        }
        return masterMind.getNumeroSecreto();
    }

}
