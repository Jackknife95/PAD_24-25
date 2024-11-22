package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;

public class GameCreatorActivity extends AppCompatActivity {

    private EditText gameNameEditText, characterNameEditText;
    private Button confirmGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_creator_activity);

        // Inicializamos las vistas
        gameNameEditText = findViewById(R.id.gameNameEditText);
        characterNameEditText = findViewById(R.id.characterNameEditText);
        confirmGameButton = findViewById(R.id.confirmGameButton);

        confirmGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName = gameNameEditText.getText().toString().trim();
                String characterName = characterNameEditText.getText().toString().trim();

                if (gameName.isEmpty() || characterName.isEmpty()) {
                    Toast.makeText(GameCreatorActivity.this, "Por favor, ingresa un nombre para la partida y el personaje", Toast.LENGTH_SHORT).show();
                } else {
                    // Crear nueva partida
                    SavedGame newGame = new SavedGame(gameName, characterName);

                    // Guardar la partida en el SessionManager
                    SessionManager.getInstance().setCurrentSession(newGame);

                    // Mostrar mensaje de éxito
                    Toast.makeText(GameCreatorActivity.this, "Partida creada: " + gameName, Toast.LENGTH_SHORT).show();

                    // Finalizar la actividad y regresar a la anterior
                    finish();
                }
            }
        });
    }
}
