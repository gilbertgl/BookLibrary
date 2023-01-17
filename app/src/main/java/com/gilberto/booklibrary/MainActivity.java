package com.gilberto.booklibrary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rView;
    private FloatingActionButton add_button;
    private CustomAdapter adapter;

    private Repository repo;
    private ArrayList<String> book_id, book_title, book_author, book_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ComponentsInitializer();
        ContextInitializer();

        DisplayBooks();

        add_button.setOnClickListener(v -> GoToScreen(AddBookScreen.class));

        adapter = new CustomAdapter(this, this, book_id, book_title, book_author, book_pages);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    private void ComponentsInitializer() {
        rView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
    }

    private void ContextInitializer() {
        repo = new Repository(this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
    }

    private void GoToScreen(Class<?> screen) {
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }

    private void DisplayBooks() {
        Cursor cursor = repo.readAllData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}