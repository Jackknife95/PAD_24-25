package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;

public class GameSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_activity);

        RecyclerView recyclerView = findViewById(R.id.listaPartidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la partida actual desde SessionManager
        SavedGame currentSession = SessionManager.getInstance().getCurrentSession();

        // Crear lista de partidas para el adaptador
        List<SavedGame> listaPartidas = new ArrayList<>();
        if (currentSession != null) {
            listaPartidas.add(currentSession);
        }

        // Configurar el adaptador
        SavedGamesAdapter adapter = new SavedGamesAdapter(this, listaPartidas);
        recyclerView.setAdapter(adapter);

        // Configurar el botón para abrir GameCreatorActivity
        Button gameCreator = findViewById(R.id.creator);
        gameCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSessionActivity.this, GameCreatorActivity.class);
                startActivity(intent);
            }
        });
    }
}
