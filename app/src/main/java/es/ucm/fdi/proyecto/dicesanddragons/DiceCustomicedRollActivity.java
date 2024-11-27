package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import java.util.Random;

public class DiceCustomicedRollActivity extends Activity {

    private EditText numDiceInput;
    private EditText numFacesInput;
    private ImageView diceImage;
    private Button rollButton;
    private TextView resultText;

    private static final int MAX_DICE = 50;    // Limite de dados
    private static final int MAX_FACES = 500;  // Limite de caras del dado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_customiced_rolls_activity);

        // Inicialización de las vistas
        numDiceInput = findViewById(R.id.numDiceInput);
        numFacesInput = findViewById(R.id.numFacesInput);
        diceImage = findViewById(R.id.dicesImage);
        rollButton = findViewById(R.id.rollButton);
        resultText = findViewById(R.id.resultText);

        // Usar una imagen para el dado
        diceImage.setImageResource(R.drawable.dados);

        // Acción del botón de tirar
        rollButton.setOnClickListener(v -> rollCustomDice());
    }

    private void rollCustomDice() {
        String numDiceString = numDiceInput.getText().toString().trim();
        String numFacesString = numFacesInput.getText().toString().trim();

        if (numDiceString.isEmpty() || numFacesString.isEmpty()) {
            resultText.setText("Por favor, introduce el número de dados y caras.");
            return;
        }

        try {
            int numDice = Integer.parseInt(numDiceString);
            int numFaces = Integer.parseInt(numFacesString);

            // Validar el número de dados
            if (numDice > MAX_DICE) {
                resultText.setText("Por favor, selecciona 50 dados o menos.");
                return;
            }

            // Validar el número de caras (no debe ser mayor que MAX_FACES)
            if (numFaces < 2) {
                resultText.setText("El dado debe tener al menos 2 caras.");
                return;
            }

            if (numFaces > MAX_FACES) {
                resultText.setText("El número de caras no puede ser mayor a " + MAX_FACES +".");
                return;
            }

            // Lógica de lanzamiento de dados
            Random random = new Random();
            int sum = 0;
            StringBuilder results = new StringBuilder("Resultados: ");

            for (int i = 0; i < numDice; i++) {
                int roll = random.nextInt(numFaces) + 1;
                results.append(roll).append(" ");
                sum += roll;
            }

            resultText.setText(results.toString() + "\nResultado final (suma): " + sum);

        } catch (NumberFormatException e) {
            resultText.setText("Por favor, introduce valores numéricos válidos.");
        }
    }
}