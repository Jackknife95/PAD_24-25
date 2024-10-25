package es.ucm.fdi.googlebooksclient;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BookLoader extends AsyncTaskLoader<List<String>> {
    private final String queryString;
    private final String printType;
    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString= queryString;
        this.printType= printType;
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        return getBookInfoJson(queryString,printType);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    protected List<String> getBookInfoJson(String queryString, String printType) {
        //HttpURLConnection()
        List<String> result = new ArrayList<>();
        try {
            String apiKey = "AIzaSyADNpSpBqi7O7RSmYY0zSfX-O8lU50x2bs";
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + queryString + "&printType=" + printType + "&key=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line); //posible parseo de datos
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
