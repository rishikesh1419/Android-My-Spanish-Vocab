package com.example.myspanishvocab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DBHandler extends SQLiteOpenHelper {

    private static  final int VERSION = 4;

    private static final String DB_NAME = "WordsDB";

    private static final String WORDS_TABLE = "words";
    private static final String ID = "id";
    private static final String WORD = "word";
    private static final String POS = "pos";
    private static final String GENDER = "gender";
    private static final String MEANING = "meaning";
    private static final String USAGE = "usage";
    private static final String NORM = "norm";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + WORDS_TABLE
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WORD + " TEXT, "
                + POS + " TEXT, "
                + GENDER + " TEXT, "
                + MEANING + " TEXT, "
                + USAGE + " TEXT, "
                + NORM + " TEXT)";

        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + WORDS_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WORD, word.getWord());
        values.put(POS, word.getPos());
        values.put(GENDER, word.getGender());
        values.put(MEANING, word.getMeaning());
        values.put(USAGE, word.getUsage());
        values.put(NORM, word.getUsage());

        db.insert(WORDS_TABLE, null, values);
        db.close();
    }

    public Word getWord(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                WORDS_TABLE,
                new String[] {ID, WORD, POS, GENDER, MEANING, USAGE, NORM},
                ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null
        );

        Word word;

        if(cursor != null) {
            cursor.moveToFirst();
            word = new Word(Integer.parseInt(String.valueOf(cursor.getInt(0))),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                    );
            return word;
        }
        else {
            return null;
        }
    }

    public List<Word> getAllWords() {
        SQLiteDatabase db = getReadableDatabase();
        List<Word> words = new ArrayList<>();
        String query = "SELECT * FROM " + WORDS_TABLE + " ORDER BY " + WORD;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                Word word = new Word();

                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setPos(cursor.getString(2));
                word.setGender(cursor.getString(3));
                word.setMeaning(cursor.getString(4));
                word.setUsage(cursor.getString(5));
                word.setNorm(cursor.getString(6));

                words.add(word);
            }
            while(cursor.moveToNext());
        }

        return words;
    }

    public List<Word> searchWords(String key) {
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String norm = pattern.matcher(Normalizer.normalize(key, Normalizer.Form.NFD)).replaceAll("");

        SQLiteDatabase db = getReadableDatabase();
        List<Word> words = new ArrayList<>();
        String query = "SELECT * FROM " + WORDS_TABLE
                + " WHERE " + NORM + " LIKE " + norm
                + " OR " + MEANING + " LIKE " + norm
                + " ORDER BY " + WORD;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                Word word = new Word();

                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setPos(cursor.getString(2));
                word.setGender(cursor.getString(3));
                word.setMeaning(cursor.getString(4));
                word.setUsage(cursor.getString(5));
                word.setNorm(cursor.getString(6));

                words.add(word);
            }
            while(cursor.moveToNext());
        }

        return words;
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(WORD, word.getWord());
        values.put(POS, word.getPos());
        values.put(GENDER, word.getGender());
        values.put(MEANING, word.getMeaning());
        values.put(USAGE, word.getUsage());
        values.put(NORM, word.getUsage());

        return db.update(
                WORDS_TABLE,
                values,
                ID + " =  ?",
                new String[] {String.valueOf(word.getId())}
                );
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                WORDS_TABLE,
                ID + " =  ?",
                new String[] {String.valueOf(word.getId())}
                );

        db.close();
    }

    public int getWordCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + WORDS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return  cursor.getCount();
    }

}
