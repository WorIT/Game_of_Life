package com.example.gameoflife.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbPatterns {
    private static final String DATABASE_NAME = "patterns";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "patterns";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_FIELD = "field";
    private static final String COLUMN_PATH = "path";

    private static final int NUM_COLUMN_TITLE = 0;
    private static final int NUM_COLUMN_FIELD = 1;
    private static final int NUM_COLUMN_PATH = 2;

    private SQLiteDatabase mDataBase;

    public DbPatterns (Context context) {
        DatabaseHandler mOpenHelper = new DatabaseHandler(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String title,String field,String path) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_FIELD, field);
        cv.put(COLUMN_PATH, path);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Pattern pattern) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE, pattern.getTitle());
        cv.put(COLUMN_FIELD, pattern.getField());
        cv.put(COLUMN_PATH, pattern.getPath());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_TITLE + " = ?",new String[] { String.valueOf(pattern.getTitle())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(String title) {
        mDataBase.delete(TABLE_NAME, COLUMN_TITLE + " = ?", new String[] { title });
    }

    public Pattern select(String title) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_TITLE + " = ?", new String[]{title}, null, null, null);
            mCursor.moveToFirst();
            String field = mCursor.getString(NUM_COLUMN_FIELD);
            String path = mCursor.getString(NUM_COLUMN_PATH);


        return new Pattern(title,field,path);
    }

    public ArrayList<Pattern> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Pattern> arr = new ArrayList<Pattern>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                String field = mCursor.getString(NUM_COLUMN_FIELD);
                String path = mCursor.getString(NUM_COLUMN_PATH);
                String title = mCursor.getString(NUM_COLUMN_TITLE);
                arr.add(new Pattern(title, field,path));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private static class DatabaseHandler extends SQLiteAssetHelper {


        private static DatabaseHandler ourInstance;

        void create_db(Context context){
            String DB_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
            InputStream myInput = null;
            OutputStream myOutput = null;
            try {
                File file = new File(DB_PATH);
                if (!file.exists()) {
                    this.getReadableDatabase();
                    //получаем локальную бд как поток
                    myInput = context.getAssets().open(DATABASE_NAME);
                    // Путь к новой бд
                    String outFileName = DB_PATH;

                    // Открываем пустую бд
                    myOutput = new FileOutputStream(outFileName);

                    // побайтово копируем данные
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = myInput.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }

                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                }
            } catch (IOException ex) {
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }

        public synchronized DatabaseHandler getInstance(Context context) {


            if (ourInstance == null){
                ///create_db(context);

            }

                ourInstance = new DatabaseHandler(context);

            return ourInstance;
        }

        private DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    }
}
