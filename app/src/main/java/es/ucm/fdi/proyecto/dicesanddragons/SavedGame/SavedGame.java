package es.ucm.fdi.proyecto.dicesanddragons.SavedGame;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.Counter;
import es.ucm.fdi.proyecto.dicesanddragons.Tirada;

public class SavedGame {
    private String nombrePartida;
    private String nombrePersonaje;
    private List<Counter> contadores;
    private List<Tirada> tiradas;

    public SavedGame(String nombrePartida, String nombrePersonaje) {
        this.nombrePartida = nombrePartida;
        this.nombrePersonaje = nombrePersonaje;
        contadores = new ArrayList<>();
        tiradas = new ArrayList<>();
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public List<Tirada> getTiradas() {
        return tiradas;
    }

    public void addTirada(String nombreDado,String caraDado, int resultado) {
        this.tiradas.add(new Tirada(nombreDado, caraDado, resultado));
    }

    public List<Counter> getContadores() {
        return contadores;
    }

    public void setContadores(List<Counter> contadores) {
        this.contadores = contadores;
    }


}
