package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.app.Activity;

public class SpecialRollsActivity extends Activity {

    private Button advantageButton;
    private Button disadvantageButton;
    private Button additionButton;
    private Button customicedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_rolls_activity);

        // Inicializar los botones
        advantageButton = findViewById(R.id.advantageButton);
        disadvantageButton = findViewById(R.id.disadvantageButton);
        additionButton = findViewById(R.id.additionButton);
        customicedButton = findViewById(R.id.customicedButton);

        // Listeners para los botones
        advantageButton.setOnClickListener(v -> openAdvantageRoll());
        disadvantageButton.setOnClickListener(v -> openDisadvantageRoll());
        additionButton.setOnClickListener(v -> openAdditionRoll());
        customicedButton.setOnClickListener(v -> openCustomizedRoll());
    }

    // Métodos para abrir las actividades

    private void openAdvantageRoll() {
        Intent intent = new Intent(SpecialRollsActivity.this, DiceAdvantageRollActivity.class);
        startActivity(intent);
    }

    private void openDisadvantageRoll() {
        Intent intent = new Intent(SpecialRollsActivity.this, DiceDisadvantageRollActivity.class);
        startActivity(intent);
    }

    private void openAdditionRoll() {
        Intent intent = new Intent(SpecialRollsActivity.this, DiceAdditionRollActivity.class);
        startActivity(intent);
    }

    private void openCustomizedRoll() {
        Intent intent = new Intent(SpecialRollsActivity.this, DiceCustomicedRollActivity.class);
        startActivity(intent);
    }
}
