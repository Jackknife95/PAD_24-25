package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    public static final String EXTRA_TITULO = "titulo";
    public static final String EXTRA_AUTOR = "autor";
    public static final String EXTRA_PRINT_TYPE = "printType";
    private MainActivity mainActivity;

    // Constructor para pasar el contexto
    public BookLoaderCallbacks(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        String tituloString = args.getString(EXTRA_TITULO);
        String authorString = args.getString(EXTRA_AUTOR);
        String printType = args.getString(EXTRA_PRINT_TYPE);
        return new BookLoader(mainActivity, tituloString, authorString, printType); // Usa el contexto aquí
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        mainActivity.updateBooksResultList(new ArrayList<>(), false); // Limpia la lista
    }


    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
        if (data != null && !data.isEmpty()) {
            Log.d("BookLoaderCallbacks", "Se han encontrado resultados");
        } else {
            Log.d("BookLoaderCallbacks", "No se encontraron resultados.");
        }
        mainActivity.updateBooksResultList(data, true);
    }
}
