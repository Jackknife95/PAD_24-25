package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DiceSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_selection_activity);

        // Inicialización de botones
        Button lanzamientoSimple = findViewById(R.id.lanzamientoSimple);
        Button tiradaCustomizada = findViewById(R.id.tiradaCustomizada);
        Button tiradaVentaja = findViewById(R.id.tiradaVentaja);
        Button tiradaDesventaja = findViewById(R.id.tiradaDesventaja);
        Button tiradaSumaDados = findViewById(R.id.tiradaSumaDados);

        // Configuración de intents para cada botón
        lanzamientoSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiceSelectionActivity.this, DiceSimpleRollActivity.class);
                startActivity(intent);
            }
        });

        tiradaCustomizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiceSelectionActivity.this, DiceCustomicedRollActivity.class);
                startActivity(intent);
            }
        });

        tiradaVentaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiceSelectionActivity.this, DiceAdvantageRollActivity.class);
                startActivity(intent);
            }
        });

        tiradaDesventaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiceSelectionActivity.this, DiceDisadvantageRollActivity.class);
                startActivity(intent);
            }
        });

        tiradaSumaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiceSelectionActivity.this, DiceAdditionRollActivity.class);
                startActivity(intent);
            }
        });
    }
}



