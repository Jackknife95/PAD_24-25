package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String queryString;
    private String authorString;
    private String printType;

    public BookLoader(Context context, String queryString, String authorString, String printType) {
        super(context);
        this.queryString = queryString;
        this.authorString = authorString;
        this.printType = printType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookInfo> loadInBackground() {
        return getBookInfoJson(queryString, authorString, printType);
    }

    public List<BookInfo> getBookInfoJson(String titleString, String authorString, String printType) {
        List<BookInfo> books = new ArrayList<>();
        try {
            // Construcción de la URL de la consulta
            StringBuilder urlStringBuilder = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");
            if (!titleString.isEmpty()) {
                urlStringBuilder.append(URLEncoder.encode(titleString, "UTF-8"));
            }
            if (!authorString.isEmpty()) {
                urlStringBuilder.append("+inauthor:").append(URLEncoder.encode(authorString, "UTF-8"));
            }
            urlStringBuilder.append("&printType=").append(printType);
            urlStringBuilder.append("&maxResults=10&key=").append("AIzaSyC5rify634w-w9N7kz8fhOImsM5t2foPDQ");

            URL url = new URL(urlStringBuilder.toString());
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
