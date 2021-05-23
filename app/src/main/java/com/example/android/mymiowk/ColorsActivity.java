package com.example.android.mymiowk;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        if(mMediaPlayer != null)
                            mMediaPlayer.pause();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        if(mMediaPlayer != null)
                            mMediaPlayer.pause();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, keep playing
                        if(mMediaPlayer != null)
                            mMediaPlayer.setVolume(0.2f,0.2f);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        if(mMediaPlayer != null) {
                            mMediaPlayer.setVolume(1f, 1f);
                            mMediaPlayer.start();
                        }

                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            stopPlaying();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
       final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("weṭeṭṭi","red",R.drawable.color_red,R.raw.color_red_islam));
        words.add(new Word("chokokki","green",R.drawable.color_green,R.raw.color_green_islam));
        words.add(new Word("ṭakaakki","brown",R.drawable.color_brown,R.raw.color_brown_islam));
        words.add(new Word("ṭopoppi","gray",R.drawable.color_gray,R.raw.color_gray_islam));
        words.add(new Word("kululli","black",R.drawable.color_black,R.raw.color_black_islam));
        words.add(new Word("kelelli","white",R.drawable.color_white,R.raw.color_white_islam));
        words.add(new Word("ṭopiisә","dusty yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow_islam));
        words.add(new Word("chiwiiṭә","mustard yellow",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow_islam));



        WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopPlaying();
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this,words.get(position).getAudio());
                mMediaPlayer.start();
                int result =  mAudioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer.start();
                }
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
    }

    protected void stopPlaying() {
        // If media player is not null then try to stop it
        if (mMediaPlayer != null) { if(mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
