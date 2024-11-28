package es.ucm.fdi.proyecto.dicesanddragons;

import java.util.ArrayList;
import java.util.List;

// Clase para representar una tirada
public class Tirada {
    private String nombreDado;
    private String numCaras;
    private int resultado;

    public Tirada(String nombreDado,String numCaras, int resultado) {
        this.nombreDado = nombreDado;
        this.numCaras=numCaras;
        this.resultado = resultado;
    }

    public String getNombreDado() {
        return nombreDado;
    }

    public int getResultado() {
        return resultado;
    }

    public String getNumCaras() {
        return numCaras;
    }

    @Override
    public String toString() {
        return "Dado: " + nombreDado + ", Resultado: " + resultado;
    }
}
