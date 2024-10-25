package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String queryString;
    private String printType;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookInfo> loadInBackground() {
        return getBookInfoJson(queryString, printType);
    }

    public List<BookInfo> getBookInfoJson(String queryString, String printType) {
        List<BookInfo> books = new ArrayList<>();
        try {
            String apiKey = "AIzaSyC5rify634w-w9N7kz8fhOImsM5t2foPDQ";
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + queryString + "&printType=" + printType + "&maxResults=10&key=" + apiKey;

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonResponse = stringBuilder.toString();
            books = BookInfo.fromJsonResponse(jsonResponse);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
