package com.gilberto.booklibrary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rView;
    private FloatingActionButton add_button;
    private CustomAdapter adapter;
    private ImageView emptyScreen_imageView;
    private TextView emptyScreen_textView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            ConfirmDeleteAllDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ComponentsInitializer() {
        rView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        emptyScreen_imageView = findViewById(R.id.emptyScreen_imageView);
        emptyScreen_textView = findViewById(R.id.emptyScreen_textView);
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
            emptyScreen_imageView.setVisibility(View.GONE);
            emptyScreen_textView.setVisibility(View.GONE);
        } else {
            emptyScreen_imageView.setVisibility(View.VISIBLE);
            emptyScreen_textView.setVisibility(View.VISIBLE);
        }
    }

    private void ConfirmDeleteAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deleting all Books");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            new Handler().postDelayed(() -> {
                Repository repo = new Repository(this);
                repo.deleteAllData();
                Toast.makeText(this, "All books deleted!", Toast.LENGTH_SHORT).show();
            }, 1500);
            new Handler().postDelayed(this::recreate, 3000);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}