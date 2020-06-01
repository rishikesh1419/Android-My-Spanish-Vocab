package com.example.myspanishvocab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class WordDetails extends AppCompatActivity {

    Context context;
    DBHandler dbHandler;

    TextView wordTv, usageTv, meaningTv, typeTv;
    Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        dbHandler = new DBHandler(context);

        wordTv = findViewById(R.id.wordTv);
        typeTv = findViewById(R.id.typeTv);
        meaningTv = findViewById(R.id.meaningTv);
        usageTv = findViewById(R.id.usageTv);

        Intent intent = getIntent();
        String details = intent.getStringExtra("details");

        Gson gson = new Gson();
        word = gson.fromJson(details, Word.class);

        wordTv.setText(word.getWord());
        meaningTv.setText(word.getMeaning());
        usageTv.setText(word.getUsage());
        if(word.getGender().equals("None")) {
            typeTv.setText(word.getPos());
        }
        else {
            typeTv.setText(String.format("%s, %s", word.getPos(), word.getGender()));
        }

        FloatingActionButton fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteWord(word);
                finish();
            }
        });
    }

}
