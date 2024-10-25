package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    public static final String EXTRA_QUERY = "query";
    public static final String EXTRA_PRINT_TYPE = "printType";
    private Context context; // Campo para el contexto
    private MainActivity mainActivity;

    // Constructor para pasar el contexto
    public BookLoaderCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        String queryString = args.getString(EXTRA_QUERY);
        String printType = args.getString(EXTRA_PRINT_TYPE);
        return new BookLoader(context, queryString, printType); // Usa el contexto aquí
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        mainActivity.updateBooksResultList(new ArrayList<>()); // Limpia la lista
    }


    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
        if (data != null && !data.isEmpty()) {
            mainActivity.updateBooksResultList(data);  // Envía la lista a MainActivity
        } else {
            Log.d("BookLoaderCallbacks", "No se encontraron resultados.");
        }
    }


}
