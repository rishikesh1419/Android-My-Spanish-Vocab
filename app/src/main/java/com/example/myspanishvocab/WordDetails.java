package com.example.myspanishvocab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.google.gson.Gson;

public class WordDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);

        TextView allTv = findViewById(R.id.allTv);

        Gson details = new Gson();

        Word word = details.fromJson(getIntent().getStringExtra("details"), Word.class);

        allTv.setText(word.getMeaning());
    }
}
