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

public class DiceAdvantageRollActivity extends Activity {

    private Spinner diceSpinner;
    private Spinner numDiceSpinner;
    private ImageView diceImage;
    private Button rollButton;
    private TextView resultText;
    private SessionManager sm = SessionManager.getInstance();
    SavedGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_advantage_rolls_activity);

        // Inicialización de las vistas
        diceSpinner = findViewById(R.id.diceSpinner);
        numDiceSpinner = findViewById(R.id.numDiceSpinner);
        diceImage = findViewById(R.id.diceImage);
        rollButton = findViewById(R.id.rollButton);
        resultText = findViewById(R.id.resultText);

        // Configuración del Spinner para el número de caras
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dice_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceSpinner.setAdapter(adapter);

        // Configuración del Spinner para el número de dados
        ArrayAdapter<CharSequence> numDiceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.num_dice_options,
                android.R.layout.simple_spinner_item
        );
        numDiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numDiceSpinner.setAdapter(numDiceAdapter);

        // Se establece el dado de 6 caras como selección por defecto
        diceSpinner.setSelection(2);  // Este es el índice para el dado de 6 caras
        numDiceSpinner.setSelection(0);  // Establecemos el valor por defecto en 2 dados

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
        rollButton.setOnClickListener(v -> rollDiceWithAdvantage());
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

    private void rollDiceWithAdvantage() {
        String selectedDice = diceSpinner.getSelectedItem().toString();
        int maxValue = getMaxValueForDice(selectedDice);

        String numDiceString = numDiceSpinner.getSelectedItem().toString();
        int numDice = Integer.parseInt(numDiceString);

        if (maxValue > 0 && numDice > 0) {
            Random random = new Random();
            int highestRoll = 0;
            StringBuilder results = new StringBuilder("Resultados: ");

            // Lanzar los dados y obtener los resultados
            for (int i = 0; i < numDice; i++) {
                int roll = random.nextInt(maxValue) + 1;
                results.append(roll).append("  ");
                if (roll > highestRoll) {
                    highestRoll = roll;
                }
            }

            currentGame = sm.getCurrentSession();
            currentGame.addTirada("Tirada con Ventaja", selectedDice, highestRoll);
            sm.setCurrentSession(currentGame);

            // Mostrar los resultados y el resultado final
            resultText.setText(results.toString() + "\nResultado final (ventaja): " + highestRoll);
        } else {
            resultText.setText("Por favor, selecciona un dado y un número válido de dados.");
        }
    }

    private int getMaxValueForDice(String selectedDice) {
        switch (selectedDice) {
            case "Dado de 2 caras":
                return 2;
            case "Dado de 4 caras":
                return 4;
            case "Dado de 6 caras":
                return 6;
            case "Dado de 10 caras":
                return 10;
            case "Dado de 12 caras":
                return 12;
            case "Dado de 20 caras":
                return 20;
            case "Dado de 100 caras":
                return 100;
            default:
                return 0;
        }
    }
}

