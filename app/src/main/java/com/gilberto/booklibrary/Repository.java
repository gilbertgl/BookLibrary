package com.gilberto.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Repository extends SQLiteOpenHelper {
    private final Context _context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "book";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_PAGES = "pages";

    public Repository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long AddBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues book = new ContentValues();

        book.put(COLUMN_TITLE, title);
        book.put(COLUMN_AUTHOR, author);
        book.put(COLUMN_PAGES, pages);
        return db.insert(TABLE_NAME,null, book);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, String updatedTitle, String updatedAuthor, int updatedPages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updatedBook = new ContentValues();
        updatedBook.put(COLUMN_TITLE, updatedTitle);
        updatedBook.put(COLUMN_AUTHOR, updatedAuthor);
        updatedBook.put(COLUMN_PAGES, updatedPages);

        long result = db.update(TABLE_NAME, updatedBook, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(_context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(_context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(_context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
