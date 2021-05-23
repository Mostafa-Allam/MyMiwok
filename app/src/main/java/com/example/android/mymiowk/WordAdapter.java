package com.example.android.mymiowk;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private  int  backGroundColor;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public WordAdapter(Activity context, ArrayList<Word> words, int backGroundColor) {
        super(context, 0, words);
        this. backGroundColor =  backGroundColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

          Word currentWord = getItem(position);
             TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
             defaultTextView.setText(currentWord.getDefaultWord());

            TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
            miwokTextView .setText(currentWord.getMiwokWord());

            ImageView miwokImageView = listItemView.findViewById(R.id.image_view);
            if(currentWord.hasImage())
            {
                miwokImageView.setImageResource(currentWord.getImage());
                miwokImageView.setVisibility(View.VISIBLE);
            }else {
                miwokImageView.setVisibility(View.GONE);
            }
            ImageView playImageView = listItemView.findViewById(R.id.play_image);
            playImageView.setImageResource(R.drawable.baseline_play_arrow_white_24dp);

           View textContainer = listItemView.findViewById(R.id.text_container);
           int color = ContextCompat.getColor(getContext(),backGroundColor);
           textContainer.setBackgroundColor(color);


        return listItemView;

    }
}