package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Activity;

import java.util.Random;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class DiceSimpleRollActivity extends Activity {

    private Spinner diceSpinner;
    private ImageView diceImage;
    private Button rollButton;
    private TextView resultText;
    private SessionManager sm = SessionManager.getInstance();
    SavedGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_simple_rolls_activity);

        // Inicialización de las vistas
        diceSpinner = findViewById(R.id.diceSpinner);
        diceImage = findViewById(R.id.diceImage);
        rollButton = findViewById(R.id.rollButton);
        resultText = findViewById(R.id.resultText);

        // Configuración del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dice_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceSpinner.setAdapter(adapter);

        // Se establece el dado de 6 caras como selección por defecto
        diceSpinner.setSelection(2);

        // Cambiar la imagen del dado al seleccionar un dado del Spinner
        diceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDiceImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hace nada
            }
        });

        // Acción del botón de tirar
        rollButton.setOnClickListener(v -> rollDice());
    }

    private void updateDiceImage() {
        String selectedDice = diceSpinner.getSelectedItem().toString();

        switch (selectedDice) {
            case "Dado de 2 caras":
                diceImage.setImageResource(R.drawable.d2);
                break;
            case "Dado de 4 caras":
                diceImage.setImageResource(R.drawable.d4);
                break;
            case "Dado de 6 caras":
                diceImage.setImageResource(R.drawable.d6);
                break;
            case "Dado de 10 caras":
                diceImage.setImageResource(R.drawable.d12);
                break;
            case "Dado de 12 caras":
                diceImage.setImageResource(R.drawable.d12);
                break;
            case "Dado de 20 caras":
                diceImage.setImageResource(R.drawable.d20);
                break;
            case "Dado de 100 caras":
                diceImage.setImageResource(R.drawable.d100);
                break;
            default:
                diceImage.setImageResource(android.R.color.transparent); // Imagen por defecto
                break;
        }
    }


    private void rollDice() {
        String selectedDice = diceSpinner.getSelectedItem().toString();
        int maxValue;

        switch (selectedDice) {
            case "Dado de 2 caras":
                maxValue = 2;
                break;
            case "Dado de 4 caras":
                maxValue = 4;
                break;
            case "Dado de 6 caras":
                maxValue = 6;
                break;
            case "Dado de 10 caras":
                maxValue = 10;
                break;
            case "Dado de 12 caras":
                maxValue = 12;
                break;
            case "Dado de 20 caras":
                maxValue = 20;
                break;
            case "Dado de 100 caras":
                maxValue = 100;
                break;
            default:
                maxValue = 0; // Valor por defecto
        }

        if (maxValue > 0) {
            Random random = new Random();
            int result = random.nextInt(maxValue) + 1;
            currentGame = sm.getCurrentSession();
            currentGame.addTirada("Tirada Simple", selectedDice, result);
            sm.setCurrentSession(currentGame);
            resultText.setText("Resultado: " + result);
        } else {
            resultText.setText("Por favor, selecciona un dado válido.");
        }
    }

}
