package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;

public class GameSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_activity);

        RecyclerView recyclerView = findViewById(R.id.listaPartidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear lista de ejemplo de partidas
        List<SavedGame> listaPartidas = cargarPartidas();

        // Configurar el adaptador
        SavedGamesAdapter adapter = new SavedGamesAdapter(this, listaPartidas);
        recyclerView.setAdapter(adapter);
    }

    private List<SavedGame> cargarPartidas() {
        // Ejemplo de datos
        List<SavedGame> partidas = new ArrayList<>();
        partidas.add(new SavedGame("Partida 1", "Héroe A"));
        partidas.add(new SavedGame("Partida 2", "Héroe B"));
        return partidas;
    }

    public void sesionLista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

