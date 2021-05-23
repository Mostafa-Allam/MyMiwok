package com.example.android.mymiowk;

import android.net.Uri;

public class Word {

   private  String defaultWord;
   private  String miwokWord;
   private  int image = NO_IMAGE_PROVIDED;
   private  int audio ;
   private static final int NO_IMAGE_PROVIDED = -1;


    public Word() {
    }

    public Word(String miwokWord, String defaultWord, int image, int audio) {
        this.miwokWord = miwokWord;
        this.defaultWord = defaultWord;
        this.image = image;
        this.audio = audio;
    }
    public Word(String miwokWord, String defaultWord, int audio) {
        this.miwokWord = miwokWord;
        this.defaultWord = defaultWord;
        this.audio = audio;
    }

    public String getDefaultWord() {
        return defaultWord;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public int getImage() {
        return image;
    }
    public int getAudio() {
        return audio;
    }

    public boolean hasImage(){
        return image != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultWord='" + defaultWord + '\'' +
                ", miwokWord='" + miwokWord + '\'' +
                ", image=" + image +
                ", audio=" + audio +
                '}';
    }
}
