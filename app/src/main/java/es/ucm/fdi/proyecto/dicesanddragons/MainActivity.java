package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnDiceRolls, btnGameSession, btnWeaponCreator, btnCounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Inicializando MainActivity");

        // Inicialización de botones
        btnDiceRolls = findViewById(R.id.btnDiceRolls);
        btnGameSession = findViewById(R.id.btnGameSession);
        btnWeaponCreator = findViewById(R.id.btnWeaponCreator);
        btnCounters = findViewById(R.id.btnCounters);
        // Navegación a diferentes actividades
        btnDiceRolls.setOnClickListener(v -> openDiceRollActivity());
        btnGameSession.setOnClickListener(v -> openGameSessionActivity());
        btnWeaponCreator.setOnClickListener(v -> openWeaponCreatorActivity());
        btnCounters.setOnClickListener(v -> openCountersActivity());
    }

    private void openDiceRollActivity() {
        Log.i(TAG, "Navegando a DiceRollActivity");
        Intent intent = new Intent(this, DiceRollActivity.class);
        startActivity(intent);
    }

    private void openGameSessionActivity() {
        Log.i(TAG, "Navegando a GameSessionActivity");
        Intent intent = new Intent(this, GameSessionActivity.class);
        startActivity(intent);
    }

    private void openWeaponCreatorActivity() {
        Log.i(TAG, "Navegando a WeaponCreatorActivity");
        Intent intent = new Intent(this, WeaponCreatorActivity.class);
        startActivity(intent);
    }
    private void openCountersActivity() {
        if(SessionManager.getInstance().getCurrentSession()==null){
            Log.w(TAG, "No hay sesión válida iniciada");
            Toast.makeText(this,"Necesitas estar en una partida válida para acceder a sus contadores",Toast.LENGTH_LONG).show();
        }
        else {
            Log.i(TAG, "Navegando a CounterActivity");
            Intent intent = new Intent(this, CounterActivity.class);
            startActivity(intent);
        }
    }
}