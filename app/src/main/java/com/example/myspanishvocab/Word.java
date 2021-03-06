package com.example.myspanishvocab;

public class Word {
    private int id;
    private String word;
    private String pos;
    private String gender;
    private String meaning;
    private String usage;
    private String norm;

    public Word() {

    }

    public Word(int id, String word, String pos, String gender, String meaning, String usage, String norm) {
        this.id = id;
        this.word = word;
        this.pos = pos;
        this.gender = gender;
        this.meaning = meaning;
        this.usage = usage;
        this.norm = norm;
    }

    public Word(String word, String pos, String gender, String meaning, String usage, String norm) {
        this.word = word;
        this.pos = pos;
        this.gender = gender;
        this.meaning = meaning;
        this.usage = usage;
        this.norm = norm;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", pos='" + pos + '\'' +
                ", gender='" + gender + '\'' +
                ", meaning='" + meaning + '\'' +
                ", usage='" + usage + '\'' +
                ", norm='" + norm + '\'' +
                '}';
    }
}
