package com.gilberto.booklibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context _context;
    private ArrayList<String> book_id, book_title, book_author, book_pages;
    private Activity activity;

    CustomAdapter(Activity activity, Context context, ArrayList<String> book_id, ArrayList<String> book_title,
                  ArrayList<String> book_author, ArrayList<String> book_pages) {
        this.activity = activity;
        this._context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.book_title_text.setText(String.valueOf(book_id.get(position)));
        holder.book_title_text.setText(String.valueOf(book_title.get(position)));
        holder.book_author_text.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_text.setText(String.valueOf(book_pages.get(position)));
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(_context, UpdateBookScreen.class);
            intent.putExtra("id", String.valueOf(book_id.get(position)));
            intent.putExtra("title", String.valueOf(book_title.get(position)));
            intent.putExtra("author", String.valueOf(book_author.get(position)));
            intent.putExtra("pages", String.valueOf(book_pages.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return book_title.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_text, book_title_text, book_author_text, book_pages_text;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title_text = itemView.findViewById(R.id.book_title_text);
            book_author_text = itemView.findViewById(R.id.book_author_text);
            book_pages_text = itemView.findViewById(R.id.book_pages_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
        }
    }
}
