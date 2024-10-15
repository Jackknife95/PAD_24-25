package es.ucm.fdi.advancedcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        // Recuperar el resultado enviado desde MainActivity
        Intent intent = getIntent();
        double resultado = intent.getDoubleExtra("RESULTADO", 0); // El 0 es un valor por defecto en caso de que no se encuentre el extra

        // Buscar el TextView en el layout
        TextView resultTextView = findViewById(R.id.resultado);

        // Mostrar el resultado en el TextView
        resultTextView.setText("Resultado: " + resultado);
    }
}