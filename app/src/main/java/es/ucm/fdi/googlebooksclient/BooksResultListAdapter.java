package es.ucm.fdi.googlebooksclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {

    private List<BookInfo> mBooksData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfo book = mBooksData.get(position);
        holder.titleTextView.setText(book.title);
        holder.authorsTextView.setText(book.authors);
        holder.infoLinkTextView.setText(book.infoLink);
    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    public void setBooksData(List<BookInfo> data) {
        mBooksData.clear();
        mBooksData.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorsTextView;
        TextView infoLinkTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            authorsTextView = itemView.findViewById(R.id.tvAuthors);
            infoLinkTextView = itemView.findViewById(R.id.tvInfoLink);
        }
    }
}
