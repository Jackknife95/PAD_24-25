package es.ucm.fdi.googlebooksclient;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etAutor;
    private RadioButton rbLibro, rbRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos del layout
        etAutor = findViewById(R.id.etAutor);
        rbLibro = findViewById(R.id.rbLibro);
        rbRevista = findViewById(R.id.rbRevista);

        // Listener para el RadioGroup
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbRevista) {
                    etAutor.setEnabled(false);  // Desactiva el campo autor
                    etAutor.setText("");  // Limpia el campo si hay texto
                } else if (checkedId == R.id.rbLibro) {
                    etAutor.setEnabled(true);  // Activa el campo autor
                }
            }
        });
    }
}
