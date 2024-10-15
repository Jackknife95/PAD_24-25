package es.ucm.fdi.advancedcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String ETIQUETA = "MainActivity";
    private Calculator calculadora;
    private TextView numero1, numero2;
    private Button boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9;
    private Button suma, resultado, coma, borrar;

    private String input1 = "", input2 = "";
    private boolean input2Active = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Log de información (i)
        Log.i(ETIQUETA, "onCreate: Iniciando MainActivity");

        // Inicializar vistas
        calculadora=new Calculator();
        numero1 = findViewById(R.id.Numero1);
        numero2 = findViewById(R.id.Numero2);

        boton0 = findViewById(R.id.boton0);
        boton1 = findViewById(R.id.boton1);
        boton2 = findViewById(R.id.boton2);
        boton3 = findViewById(R.id.boton3);
        boton4 = findViewById(R.id.boton4);
        boton5 = findViewById(R.id.boton5);
        boton6 = findViewById(R.id.boton6);
        boton7 = findViewById(R.id.boton7);
        boton8 = findViewById(R.id.boton8);
        boton9 = findViewById(R.id.boton9);
        suma = findViewById(R.id.suma);
        resultado = findViewById(R.id.resultado);
        coma = findViewById(R.id.coma);
        borrar = findViewById(R.id.borrar);

        // Añadir listeners a los botones
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (input2Active) {
                    input2 += button.getText().toString();
                    numero2.setText(input2);

                    // Log verbose (v) para detalles del funcionamiento de la app
                    Log.v(ETIQUETA, "Nuevo valor para el segundo número: " + input2);
                } else {
                    input1 += button.getText().toString();
                    numero1.setText(input1);

                    // Log verbose (v) para detalles del funcionamiento de la app
                    Log.v(ETIQUETA, "Nuevo valor para el primer número: " + input1);
                }
            }
        };

        boton0.setOnClickListener(numberListener);
        boton1.setOnClickListener(numberListener);
        boton2.setOnClickListener(numberListener);
        boton3.setOnClickListener(numberListener);
        boton4.setOnClickListener(numberListener);
        boton5.setOnClickListener(numberListener);
        boton6.setOnClickListener(numberListener);
        boton7.setOnClickListener(numberListener);
        boton8.setOnClickListener(numberListener);
        boton9.setOnClickListener(numberListener);

        resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log de depuración (d)
                Log.d(ETIQUETA, "Botón de igual (=) presionado");

                addXandY();  // Llamar al método addXandY
            }
        });

        // Botón para suma
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input2Active = true;

                // Log de información (i)
                Log.i(ETIQUETA, "Modo suma activado");
            }
        });

        // Botón para coma
        coma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input2Active && !input2.contains(".")) {
                    input2 += ".";
                    numero2.setText(input2);

                    // Log de depuración (d)
                    Log.d(ETIQUETA, "Coma añadida al segundo número: " + input2);

                } else if (!input1.contains(".")) {
                    input1 += ".";
                    numero1.setText(input1);

                    // Log de depuración (d)
                    Log.d(ETIQUETA, "Coma añadida al primer número: " + input1);
                }
            }
        });

        // Botón para borrar
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1 = "";
                input2 = "";
                input2Active = false;
                numero1.setText("Introduzca X");
                numero2.setText("Introduzca Y");

                // Log de información (i)
                Log.i(ETIQUETA, "Campos de los valores borrados");
            }
        });
    }

    // Método addXandY
    private void addXandY() {

        // Asegurarse de que no estén vacíos
        if (!input1.isEmpty() && !input2.isEmpty()) {
            double x = Double.parseDouble(input1);
            double y = Double.parseDouble(input2);

            double result = calculadora.sum(x, y);

            // Log depuración (d) para el resultado
            Log.d(ETIQUETA, "Resultado obtenido: " + result);

            Intent intent = new Intent(MainActivity.this, CalculatorResultActivity.class);

            intent.putExtra("RESULTADO", result);
            startActivity(intent);

            // Log de información (i)
            Log.i(ETIQUETA, "Iniciando CalculatorResultActivity con resultado");
        } else {

            // Log de advertencia (w)
            Log.w(ETIQUETA, "Uno o ambos campos están vacíos");

            // Manejar el caso en que uno de los campos esté vacío (por ejemplo, mostrar un mensaje)
            Toast.makeText(MainActivity.this, "Por favor, ingrese ambos números", Toast.LENGTH_SHORT).show();
        }
    }
}