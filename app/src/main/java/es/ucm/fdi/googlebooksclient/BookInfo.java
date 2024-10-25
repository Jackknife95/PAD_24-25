package es.ucm.fdi.googlebooksclient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    public String title;
    public String authors;
    public String infoLink;

    public static List<BookInfo> fromJsonResponse(String json) {
        List<BookInfo> books = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray items = jsonObject.optJSONArray("items");

            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject bookObject = items.getJSONObject(i).optJSONObject("volumeInfo");
                    BookInfo book = new BookInfo();
                    book.title = bookObject.optString("title");
                    book.authors = bookObject.optJSONArray("authors") != null ? bookObject.optJSONArray("authors").join(", ") : "Unknown";
                    book.infoLink = bookObject.optString("infoLink");
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
