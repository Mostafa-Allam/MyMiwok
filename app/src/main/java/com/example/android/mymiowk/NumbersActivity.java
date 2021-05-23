package com.example.android.mymiowk;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;



public class NumbersActivity extends AppCompatActivity{

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
       final ArrayList<Word>  words = new ArrayList<Word>();
        words.add(new Word("lutti","One",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("otiiko","Tow",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("tolookosu","Three",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("oyyisa","four",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("massokka","five",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("temmokka","six",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("kenekaku","seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("kawinta","eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("wo’e","nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("na’aacha","ten",R.drawable.number_ten,R.raw.number_ten));

      WordAdapter adapter = new WordAdapter(this,words,R.color.category_numbers);
         ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopPlaying();

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(position).getAudio());
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
        if (mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
