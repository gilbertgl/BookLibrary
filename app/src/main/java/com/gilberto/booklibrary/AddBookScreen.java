package com.gilberto.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AddBookScreen extends AppCompatActivity {

    private EditText title_input, author_input, pages_input;
    private ProgressBar progress_bar;
    private Button btn_addBook;
    private View screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);
        ComponentsInitializer();

        btn_addBook.setOnClickListener(view -> {
            hideKeyBoard(view);
            String title = title_input.getText().toString().trim();
            String author = author_input.getText().toString().trim();
            int pages;
            if (pages_input.getText().toString().trim().isEmpty()) {
                pages = 0;
            } else {
                pages = Integer.parseInt(pages_input.getText().toString().trim());
            }

            if (title.isEmpty() || author.isEmpty() || pages == 0) {
                Toast.makeText(this, "Fill in all fields!", Toast.LENGTH_SHORT).show();

            } else if (!title.matches("^([a-zA-Z0-9\\s]+){3,}")) {
                Toast.makeText(this, "The title: %s isn't valid! It has to have at least 3 letters.", Toast.LENGTH_SHORT).show();

            } else if (!author.matches("^([a-zA-Z0-9\\s]+){3,}")) {
                Toast.makeText(this, "The author: %s isn't valid! It has to have at least 3 letters.", Toast.LENGTH_SHORT).show();

            } else {
                AddBook(view, title, author, pages);
            }
        });

        screen.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideKeyBoard(v);
            }
        });
    }

    private void ComponentsInitializer() {
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        btn_addBook = findViewById(R.id.btn_addBook);
        progress_bar = findViewById(R.id.progress_bar);
        screen = findViewById(R.id.add_book_screen);
    }

    private void AddBook(View view, String title, String author, int pages) {
        progress_bar.setVisibility(View.VISIBLE);

        Repository repo = new Repository(AddBookScreen.this);
        long result = repo.AddBook(title, author, pages);
        new Handler().postDelayed(() -> {
            if(result == -1){
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }, 1500);

        new Handler().postDelayed(this::GoToMainActivity, 3000);
    }

    private void GoToMainActivity() {
        Intent intent = new Intent(AddBookScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}