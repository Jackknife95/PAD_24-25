package es.ucm.fdi.googlebooksclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Definición de los elementos de la interfaz
    private static final int BOOK_LOADER_ID = 1;
    private EditText etTitulo;
    private EditText etAutor;
    private RadioButton rbLibro, rbRevista;
    private Button btnBuscar;
    private BookLoaderCallbacks bookLoaderCallbacks;
    private BooksResultListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de los elementos de la interfaz
        etTitulo = findViewById(R.id.etTitulo);
        etAutor = findViewById(R.id.etAutor);
        rbLibro = findViewById(R.id.rbLibro);
        rbRevista = findViewById(R.id.rbRevista);
        btnBuscar = findViewById(R.id.btnBuscar);

        // Configuración para mostrar o no el campo de autor (depende si es libro o revista)
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLibro) {
                etAutor.setVisibility(View.VISIBLE);
            } else {
                etAutor.setVisibility(View.GONE);
            }
        });

        //Configuración para mostrar los resultados de libros
        RecyclerView rvBooks = findViewById(R.id.rvBooks);
        adapter = new BooksResultListAdapter();
        rvBooks.setAdapter(adapter);

        // Inicializar el loader y los callbacks
        bookLoaderCallbacks = new BookLoaderCallbacks(this);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

        btnBuscar.setOnClickListener(this::searchBooks);
    }

    public void searchBooks(View view) {
        String tituloString = etTitulo.getText().toString().trim();
        String autorString = etAutor.getText().toString().trim();
        String printType = (rbLibro.isChecked()) ? "books" : "magazines";

        // Se verifica si los campos de título y autor están vacíos
        if (tituloString.isEmpty() && autorString.isEmpty()) {
            Toast.makeText(this, "Introduce un título o autor para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lanza el loader con los parámetros adecuados
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_TITULO, tituloString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_AUTOR, autorString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    public void updateBooksResultList(List<BookInfo> books) {
        if (adapter != null) {
            adapter.setBooksData(books);
            adapter.notifyDataSetChanged();
        } else {
            Log.d("MainActivity", "Adapter no está inicializado");
        }
    }


}
