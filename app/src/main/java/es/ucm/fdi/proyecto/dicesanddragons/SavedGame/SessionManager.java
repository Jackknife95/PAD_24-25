package es.ucm.fdi.proyecto.dicesanddragons.SavedGame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static SessionManager instance;
    private SavedGame currentSession;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public SavedGame getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(SavedGame session) {
        this.currentSession = session;
    }

    public void guardarPartida(Context context){
        String json = currentSession.toJson();
        try (FileOutputStream fos = context.openFileOutput(currentSession.getNombrePartida()+"_"+currentSession.getNombrePersonaje()+".json", Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SavedGame cargarPartida(Context context, String filename) {
        StringBuilder json = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            this.currentSession = SavedGame.fromJson(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentSession;
    }

    public List<SavedGame> listarPartidas(Context context) {
        List<SavedGame> partidas = new ArrayList<>();
        String[] files = context.fileList();
        for (String filename : files) {
            if(filename.endsWith(".json")) {
                try (FileInputStream fis = context.openFileInput(filename);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json.append(line);
                    }
                    SavedGame partida = SavedGame.fromJson(json.toString());
                    partidas.add(partida);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return partidas;
    }
}
