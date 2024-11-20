package es.ucm.fdi.proyecto.dicesanddragons.SavedGame;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.Counter;

public class SavedGame {
    private String nombrePartida;
    private String nombrePersonaje;
    private List<Counter> contadores;

    public SavedGame(String nombrePartida, String nombrePersonaje) {
        this.nombrePartida = nombrePartida;
        this.nombrePersonaje = nombrePersonaje;
        contadores = new ArrayList<>();
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public List<Counter> getContadores() {
        return contadores;
    }

    public void setContadores(List<Counter> contadores) {
        this.contadores = contadores;
    }
}
