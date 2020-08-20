package nechaev.gameoflife.gameoflife.classes;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gameoflife.gameoflife.R;

import java.util.ArrayList;

public class DbPatterns {

    Context context;

    private static final String DATABASE_NAME = "i[lplj.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "patterns";
    private static final String TABLE_NOW = "nw";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_FIELD = "field";
    private static final String COLUMN_PATH = "path";

    private static final int NUM_COLUMN_TITLE = 0;
    private static final int NUM_COLUMN_FIELD = 1;
    private static final int NUM_COLUMN_PATH = 2;

    private String APP_PREFERENCES = "mysettings";
    private String  IS_FIRST = "first";
    private SharedPreferences mSettings;

    private SQLiteDatabase mDataBase;

    public DbPatterns(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
        mSettings = context.getSharedPreferences(APP_PREFERENCES,context.MODE_PRIVATE);
    }
    public void close(){mDataBase.close();}

    public void insert(String title, String field, String path) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_FIELD, field);
        cv.put(COLUMN_PATH, path);
        mDataBase.insert(TABLE_NAME, null, cv);
        return;
    }
    public long insqq(String title){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TITLE, title);
        return mDataBase.insert(TABLE_NOW, null, cv);
    }

    public void deleteqq() {
        mDataBase.delete(TABLE_NOW, null, null);
    }

    public String getqq (){
        Cursor mCursor = mDataBase.query(TABLE_NOW, null, null, null, null, null, null);
        String title = "";
        try {
            mCursor.moveToLast();
            title = mCursor.getString(NUM_COLUMN_TITLE);
        }catch (Exception e){
                title = "Empty Field";
        }finally {
            return title;
        }




    }


    public Pattern select(String title) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_TITLE + " = ?", new String[]{title}, null, null, null);
        mCursor.moveToFirst();
        String field = mCursor.getString(NUM_COLUMN_FIELD);
        String path = mCursor.getString(NUM_COLUMN_PATH);


        return new Pattern(title, field, path);
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
                    arr.add(new Pattern(title, field, path));
                } while (mCursor.moveToNext());
            }
            return arr;
        }

        public void crdb(){
            String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_TITLE+ " TEXT, " +
                    COLUMN_FIELD + " TEXT, " +
                    COLUMN_PATH+" INT);";
            String queryqq = "CREATE TABLE IF NOT EXISTS " + TABLE_NOW + " (" +
                    COLUMN_TITLE + " TEXT);";
            mDataBase.execSQL(query);
            mDataBase.execSQL(queryqq);
            insqq("Glider");
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean(IS_FIRST, false);
            editor.apply();

            String fieldempty = context.getString(R.string.empty_field);
            insert("Empty Field",fieldempty,"me");

            String fieldglider = context.getString(R.string.glider_field);
            insert("Glider",fieldglider,"glider");
            String fieldfight = context.getString(R.string.fight_field);
            insert("Fight",fieldfight,"fight");
            String fieldwash = context.getString(R.string.wash_field);
            insert("Washing machine",fieldwash,"wash");
            String fieldweekender = context.getString(R.string.weekender_field);
            insert("Weekendeer",fieldweekender,"weekender");

        }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context cont) {
            super(cont, DATABASE_NAME, null, DATABASE_VERSION);
            context = cont;

        }
        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}

