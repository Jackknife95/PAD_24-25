package es.ucm.fdi.googlebooksclient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {

    private static final int BOOK_LOADER_ID = 1;
    private EditText editTextQuery;
    private RadioGroup radioGroupPrintType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextQuery = findViewById(R.id.Buscar);
        radioGroupPrintType = findViewById(R.id.RadioGroup);
        Button buttonSearch = findViewById(R.id.BuscarBut);

        buttonSearch.setOnClickListener(v -> {
            String query = editTextQuery.getText().toString();
            String printType = (radioGroupPrintType.getCheckedRadioButtonId() == R.id.RadioLibros) ? "books" : "magazines";
            Bundle bundle = new Bundle();
            bundle.putString("query", query);
            bundle.putString("printType", printType);
            LoaderManager.getInstance(MainActivity.this).initLoader(BOOK_LOADER_ID, bundle, MainActivity.this);
        });
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        assert args != null;
        String query = args.getString("query");
        String printType = args.getString("printType");
        return new BookLoader(this, query, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {
        // Manejar los resultados aquí
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        // Reiniciar el loader
    }
}
