package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Activity;

import java.util.Random;

public class DiceAdditionRollActivity extends Activity {

    private Spinner diceSpinner;
    private EditText numDiceInput;
    private ImageView diceImage;
    private Button rollButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_addition_rolls_activity);

        // Inicialización de las vistas
        diceSpinner = findViewById(R.id.diceSpinner);
        numDiceInput = findViewById(R.id.numDiceInput);
        diceImage = findViewById(R.id.diceImage);
        rollButton = findViewById(R.id.rollButton);
        resultText = findViewById(R.id.resultText);

        // Configuración del Spinner para el tipo de dado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dice_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceSpinner.setAdapter(adapter);

        // Seleccionar por defecto un dado de 6 caras
        diceSpinner.setSelection(2);  // Este es el índice para el dado de 6 caras

        // Cambiar la imagen del dado al seleccionar un tipo de dado
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

        // Acción del botón de lanzar dados
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
        int maxValue = getMaxValueForDice(selectedDice);

        String numDiceString = numDiceInput.getText().toString().trim();

        // Verifica si se introdujo algún valor
        if (numDiceString.isEmpty()) {
            resultText.setText("Por favor, introduce el número de dados.");
            return;
        }

        int numDice = Integer.parseInt(numDiceString);

        // Verifica si el número de dados excede el límite permitido
        if (numDice > 50) {
            resultText.setText("Por favor, selecciona 50 dados o menos.");
            return;
        }

        if (maxValue > 0 && numDice > 0) {
            Random random = new Random();
            int sum = 0;
            StringBuilder results = new StringBuilder("Resultados: ");

            // Lanzar los dados y calcular la suma
            for (int i = 0; i < numDice; i++) {
                int roll = random.nextInt(maxValue) + 1;
                results.append(roll).append(" ");
                sum += roll;
            }

            // Mostrar los resultados y el resultado final
            resultText.setText(results.toString() + "\nResultado final (suma): " + sum);
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
