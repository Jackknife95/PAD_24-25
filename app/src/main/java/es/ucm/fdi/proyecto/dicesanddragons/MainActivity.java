package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnDiceRolls, btnGameSession, btnWeaponCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de botones
        btnDiceRolls = findViewById(R.id.btnDiceRolls);
        btnGameSession = findViewById(R.id.btnGameSession);
        btnWeaponCreator = findViewById(R.id.btnWeaponCreator);

        // Navegación a diferentes actividades
        btnDiceRolls.setOnClickListener(v -> openDiceRollActivity());
        btnGameSession.setOnClickListener(v -> openGameSessionActivity());
        btnWeaponCreator.setOnClickListener(v -> openWeaponCreatorActivity());
    }

    private void openDiceRollActivity() {
        Intent intent = new Intent(this, DiceRollActivity.class);
        startActivity(intent);
    }

    private void openGameSessionActivity() {
        Intent intent = new Intent(this, GameSessionActivity.class);
        startActivity(intent);
    }

    private void openWeaponCreatorActivity() {
        Intent intent = new Intent(this, WeaponCreatorActivity.class);
        startActivity(intent);
    }
}