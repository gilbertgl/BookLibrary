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

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBookScreen extends AppCompatActivity {

    private EditText title_update_input, author_update_input, pages_update_input;
    private Button btn_updateBook;
    private ProgressBar progress_bar_updateScreen;
    String id;
    private View screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book_screen);
        ComponentsInitializer();
        GetAndSetIntentData();

        btn_updateBook.setOnClickListener(v -> {
            hideKeyBoard(v);
            progress_bar_updateScreen.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Repository repo = new Repository(this);
                repo.updateData(id, title_update_input.getText().toString(),
                        author_update_input.getText().toString(), Integer.parseInt(pages_update_input.getText().toString()));
            }, 1500);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }, 3000);
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
        progress_bar_updateScreen = findViewById(R.id.progress_bar_updateScreen);
        screen = findViewById(R.id.update_book_screen);
    }

    private void GetAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            id = getIntent().getStringExtra("id");
            title_update_input.setText(getIntent().getStringExtra("title"));
            author_update_input.setText(getIntent().getStringExtra("author"));
            pages_update_input.setText(getIntent().getStringExtra("pages"));
        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}