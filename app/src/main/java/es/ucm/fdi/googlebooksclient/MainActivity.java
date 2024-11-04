package es.ucm.fdi.googlebooksclient;

import android.content.res.Configuration;
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
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Definición de los elementos de la interfaz
    private static final int BOOK_LOADER_ID = 1;
    private EditText etTitulo;
    private EditText etAutor;
    private RadioButton rbLibro, rbRevista;
    private Button btnBuscar, btnEspañol, btnIngles;
    private TextView tvResultados;
    private BookLoaderCallbacks bookLoaderCallbacks;
    private BooksResultListAdapter adapter;
    private static final String ETIQUETA = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(ETIQUETA, "onCreate: Inicializando la actividad"); // Log Verbose

        // Inicialización de los elementos de la interfaz
        etTitulo = findViewById(R.id.etTitulo);
        etAutor = findViewById(R.id.etAutor);
        rbLibro = findViewById(R.id.rbLibro);
        rbRevista = findViewById(R.id.rbRevista);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvResultados = findViewById(R.id.tvResultados);

        // Configuración para mostrar o no el campo de autor (depende si es libro o revista)
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLibro || checkedId == R.id.rbAmbos) {
                etAutor.setVisibility(View.VISIBLE);
                Log.i(ETIQUETA, "Campo de autor visible"); // Log Info
            } else {
                etAutor.setVisibility(View.GONE);
                Log.i(ETIQUETA, "Campo de autor oculto"); // Log Info
            }
        });

        // Configuración para mostrar los resultados de libros
        RecyclerView rvBooks = findViewById(R.id.rvBooks);
        adapter = new BooksResultListAdapter();
        rvBooks.setAdapter(adapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el loader y los callbacks
        bookLoaderCallbacks = new BookLoaderCallbacks(this);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

        btnBuscar.setOnClickListener(this::searchBooks);

        // Inicialización de botones para cambiar el idioma
        btnEspañol = findViewById(R.id.btnEspañol);
        btnIngles = findViewById(R.id.btnIngles);

        btnEspañol.setOnClickListener(v -> cambiarIdioma("es"));
        btnIngles.setOnClickListener(v -> cambiarIdioma("en"));
    }

    public void searchBooks(View view) {
        Log.v(ETIQUETA, "searchBooks: Método invocado"); // Log Verbose
        String tituloString = etTitulo.getText().toString().trim();
        String autorString = etAutor.getText().toString().trim();
        String printType;
        if (rbLibro.isChecked()) {
            printType = "books";
            Log.i(ETIQUETA, "Tipo de búsqueda: Libros"); // Log Info
        } else if (rbRevista.isChecked()) {
            printType = "magazines";
            autorString = ""; // Quitar el autor si se buscan solo revistas
            Log.i(ETIQUETA, "Tipo de búsqueda: Revistas"); // Log Info
        } else {
            printType = "all";
            Log.i(ETIQUETA, "Tipo de búsqueda: Ambos"); // Log Info
        }

        // Verificar si los campos de título y autor están vacíos
        if (tituloString.isEmpty() && autorString.isEmpty()) {
            Toast.makeText(this, "Introduce un título o autor para buscar", Toast.LENGTH_SHORT).show();
            Log.w(ETIQUETA, "Campos vacíos: Se necesita un título o autor para buscar"); // Log Warning
            return;
        }

        // Mostrar "Cargando..." al iniciar la búsqueda
        tvResultados.setText(getString(R.string.cargando));
        tvResultados.setVisibility(View.VISIBLE);

        Log.d(ETIQUETA, "Iniciando búsqueda para: " + tituloString + ", Autor: " + autorString); // Log Debug

        // Limpiamos la lista de libros
        Loader<List<BookInfo>> l = null;
        // Reseteamos la lista
        bookLoaderCallbacks.onLoaderReset(l);
        // Lanza el loader con los parámetros adecuados

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_TITULO, tituloString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_AUTOR, autorString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);

        Log.d(ETIQUETA, "Bundle de búsqueda creado: " + queryBundle); // Log Debug

        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    public void updateBooksResultList(List<BookInfo> books, boolean finalizado) {
        if (adapter != null) {
            adapter.setBooksData(books);
            adapter.notifyDataSetChanged();

            // Actualizar el texto del indicador según los resultados
            if (books.isEmpty()) {
                if (finalizado)
                    tvResultados.setText(getString(R.string.sin_resultados));
                    Log.i(ETIQUETA, "No se encontraron resultados"); // Log Info
            } else {
                if (finalizado)
                    tvResultados.setText(getString(R.string.resultados));
                Log.i(ETIQUETA, "Resultados encontrados y actualizados en la lista"); // Log Info
            }
        } else {
            Log.e(ETIQUETA, "Adapter no está inicializado"); // Log Error
        }
    }

    private void cambiarIdioma(String idioma) {
        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Reiniciar la actividad para aplicar los cambios de idioma
        recreate();
    }
}
