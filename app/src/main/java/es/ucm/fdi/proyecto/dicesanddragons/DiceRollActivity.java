package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class DiceRollActivity extends AppCompatActivity{

    private Button btnrolldice, btnviewlog;
    private SessionManager sm = SessionManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_roll_activity);
        // Aquí se añaden las funcionalidades de tiradas de dados

        btnrolldice = findViewById(R.id.rollDiceButton);
        btnviewlog = findViewById(R.id.viewLogButton);

        // Navegación a diferentes actividades
        btnrolldice.setOnClickListener(v -> openDiceRollActivity());
        btnviewlog.setOnClickListener(v -> openDiceRegister());

    }

    private void openDiceRollActivity() {
       // Intent intent = new Intent(this, .class);
       // startActivity(intent);
    }

    private void openDiceRegister() {
        if (sm.getCurrentSession()!=null){
            Intent intent = new Intent(this, DiceRegisterActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Por favor, seleccione una partida", Toast.LENGTH_SHORT).show();
        }

    }
}
