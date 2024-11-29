package es.ucm.fdi.proyecto.dicesanddragons;

import java.util.ArrayList;
import java.util.List;

// Clase para representar una tirada
public class Tirada {
    private String nombre;
    private int numDados;
    private String numCaras;
    private int resultado;

    public Tirada(String nombre,int numDados, String numCaras, int resultado) {
        this.nombre = nombre;
        this.numDados=numDados;
        this.numCaras=numCaras;
        this.resultado = resultado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumDados() {
        return numDados;
    }

    public int getResultado() {
        return resultado;
    }

    public String getNumCaras() {
        return numCaras;
    }

    @Override
    public String toString() {
        return "Dado: " + nombre + ", Resultado: " + resultado;
    }
}
