package com.gilberto.booklibrary;

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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateBookScreen extends AppCompatActivity {

    private EditText title_update_input, author_update_input, pages_update_input;
    private Button btn_updateBook, btn_deleteBook;
    private ProgressBar progress_bar_updateScreen;
    String id, title, author, pages;
    private View screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book_screen);
        ComponentsInitializer();
        GetAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title_update_input.getText().toString());

        btn_updateBook.setOnClickListener(v -> {
            hideKeyBoard(v);
            progress_bar_updateScreen.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Repository repo = new Repository(this);
                title = title_update_input.getText().toString().trim();
                author = author_update_input.getText().toString().trim();
                pages = pages_update_input.getText().toString().trim();
                repo.updateData(id, title, author, Integer.parseInt(pages));
            }, 1500);
            new Handler().postDelayed(this::finish, 3000);
        });

        btn_deleteBook.setOnClickListener(v -> {
            hideKeyBoard(v);
            ConfirmDeleteDialog();
        });

        screen.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideKeyBoard(v);
            }
        });
    }

    private void ComponentsInitializer() {
        title_update_input = findViewById(R.id.title_update_input);
        author_update_input = findViewById(R.id.author_update_input);
        pages_update_input = findViewById(R.id.pages_update_input);
        btn_updateBook = findViewById(R.id.btn_updateBook);
        btn_deleteBook = findViewById(R.id.btn_deleteBook);
        progress_bar_updateScreen = findViewById(R.id.progress_bar_updateScreen);
        screen = findViewById(R.id.update_book_screen);
    }

    private void GetAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            title_update_input.setText(title);
            author_update_input.setText(author);
            pages_update_input.setText(pages);
        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void ConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("You are about to delete " + title + ". Are you sure?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            progress_bar_updateScreen.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Repository repo = new Repository(UpdateBookScreen.this);
                repo.deleteOneRow(id);
            }, 1500);
            new Handler().postDelayed(this::finish, 3000);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }
}