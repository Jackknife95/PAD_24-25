package es.ucm.fdi.googlebooksclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {
    private static final int BOOK_LOADER_ID = 1;

    private EditText editTextTitle;
    private EditText editTextAuthors;
    private RadioGroup radioGroup;
    private TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthors = findViewById(R.id.editTextAuthors);
        radioGroup = findViewById(R.id.radioGroup);
        textViewResults = findViewById(R.id.textViewResults);
        Button buttonSearch = findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(this::searchBooks);
    }

    public void searchBooks(View view) {
        String title = editTextTitle.getText().toString();
        String authors = editTextAuthors.getText().toString();
        String printType = (radioGroup.getCheckedRadioButtonId() == R.id.radioBooks) ? "books" : "magazines";

        String queryString = title;
        if (!authors.isEmpty()) {
            queryString = authors + " " + title;
        }

        Bundle queryBundle = new Bundle();
        queryBundle.putString("QUERY", queryString);
        queryBundle.putString("PRINT_TYPE", printType);

        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = args.getString("QUERY");
        String printType = args.getString("PRINT_TYPE");
        return new BookLoader(this, queryString, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {
        // Actualizar la UI con los resultados
        if (data != null && !data.isEmpty()) {
            textViewResults.setVisibility(View.VISIBLE);
            // Aquí deberías actualizar el RecyclerView con los datos
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        // Resetear el loader si es necesario
    }
}
