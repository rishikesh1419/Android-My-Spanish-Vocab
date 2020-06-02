package com.example.myspanishvocab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class EditWord extends AppCompatActivity {

    Context context;
    DBHandler dbHandler;

    EditText wordEt, meaningEt, usageEt;
    RadioGroup rg1, rg2, rg3, genderRg;
    RadioButton typeBtn, masculineBtn, feminineBtn, genderBtn;
    Word edWord;

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null);
                rg2.clearCheck();
                rg2.setOnCheckedChangeListener(listener2);
                rg3.setOnCheckedChangeListener(null);
                rg3.clearCheck();
                rg3.setOnCheckedChangeListener(listener3);
                genderRg.setOnCheckedChangeListener(null);
                genderRg.clearCheck();
                genderRg.setOnCheckedChangeListener(listener4);
                masculineBtn.setTextColor(Color.parseColor("#B6B6B6"));
                feminineBtn.setTextColor(Color.parseColor("#B6B6B6"));
                masculineBtn.setEnabled(false);
                feminineBtn.setEnabled(false);
//                genderRg.setOnCheckedChangeListener(listener4);
                int selectedId = rg1.getCheckedRadioButtonId();
                typeBtn = findViewById(selectedId);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                rg3.setOnCheckedChangeListener(null);
                rg3.clearCheck();
                rg3.setOnCheckedChangeListener(listener3);
                genderRg.setOnCheckedChangeListener(null);
                genderRg.clearCheck();
                genderRg.setOnCheckedChangeListener(listener4);
                masculineBtn.setTextColor(Color.parseColor("#B6B6B6"));
                feminineBtn.setTextColor(Color.parseColor("#B6B6B6"));
                masculineBtn.setEnabled(false);
                feminineBtn.setEnabled(false);
//                genderRg.setOnCheckedChangeListener(listener4);
                int selectedId = rg2.getCheckedRadioButtonId();
                typeBtn = findViewById(selectedId);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener3 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null);
                rg2.clearCheck();
                rg2.setOnCheckedChangeListener(listener2);
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                genderRg.setOnCheckedChangeListener(null);
                genderRg.clearCheck();
                genderRg.setOnCheckedChangeListener(listener4);
                int selectedId = rg3.getCheckedRadioButtonId();
                typeBtn = findViewById(selectedId);
//                Toast.makeText(AddWord.this, typeBtn.getText().toString(), Toast.LENGTH_SHORT).show();
                masculineBtn.setEnabled(true);
                feminineBtn.setEnabled(true);
                masculineBtn.setTextColor(Color.parseColor("#8A000000"));
                feminineBtn.setTextColor(Color.parseColor("#8A000000"));
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener4 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null);
                rg2.clearCheck();
                rg2.setOnCheckedChangeListener(listener2);
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
//                int selectedId = rg3.getCheckedRadioButtonId();
//                typeBtn = findViewById(selectedId);
//                masculineBtn.setEnabled(true);
//                feminineBtn.setEnabled(true);
//                masculineBtn.setTextColor(Color.parseColor("#8A000000"));
//                feminineBtn.setTextColor(Color.parseColor("#8A000000"));
//                Toast.makeText(AddWord.this, "L4", Toast.LENGTH_SHORT).show();
                int selectedGender = genderRg.getCheckedRadioButtonId();
                genderBtn = findViewById(selectedGender);
//                Toast.makeText(AddWord.this, genderBtn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        dbHandler = new DBHandler(context);

        Intent intent = getIntent();
        edWord = dbHandler.getWord(intent.getIntExtra("id", 0));

        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);
        genderRg = findViewById(R.id.genderRg);
        rg1.clearCheck();
        rg2.clearCheck();
        rg3.clearCheck();
        genderRg.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);
        rg3.setOnCheckedChangeListener(listener3);
        genderRg.setOnCheckedChangeListener(listener4);

        masculineBtn = findViewById(R.id.masculineRb);
        feminineBtn = findViewById(R.id.feminineRb);

        wordEt = findViewById(R.id.wordEt);
        meaningEt = findViewById(R.id.meaningEt);
        usageEt = findViewById(R.id.usageEt);

        FloatingActionButton fabSave = findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String word = wordEt.getText().toString();
                String meaning = meaningEt.getText().toString();
                String usage = usageEt.getText().toString();
                String gender, norm, pos;

                if(TextUtils.isEmpty(word) || TextUtils.isEmpty(meaning) || TextUtils.isEmpty(usage)) {
                    Toast.makeText(EditWord.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    norm = pattern.matcher(Normalizer.normalize(word, Normalizer.Form.NFD)).replaceAll("");
                    if (typeBtn == null) {
                        Toast.makeText(EditWord.this, "Select a type", Toast.LENGTH_SHORT).show();
                    } else {
                        if (typeBtn.getText().toString().equals("Noun") && genderBtn == null) {
                            Toast.makeText(EditWord.this, "Select a gender", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(AddWord.this, norm, Toast.LENGTH_SHORT).show();
                            pos = typeBtn.getText().toString();
                            if (pos.equals("Noun")) {
                                gender = genderBtn.getText().toString();
                            }
                            else {
                                gender = "None";
                            }
//                            Word newWord = new Word(
//                                    word,
//                                    pos,
//                                    gender,
//                                    meaning,
//                                    usage,
//                                    norm
//                            );
                            edWord.setWord(word);
                            edWord.setGender(gender);
                            edWord.setPos(pos);
                            edWord.setMeaning(meaning);
                            edWord.setUsage(usage);
                            edWord.setNorm(norm);

                            dbHandler.updateWord(edWord);

                            Toast.makeText(EditWord.this, "Word edited", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                    }
                }


            }
        });
    }

}
